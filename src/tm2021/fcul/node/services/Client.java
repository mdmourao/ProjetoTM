package tm2021.fcul.node.services;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.Retransmition;
import tm2021.fcul.api.service.RestNode;
import tm2021.fcul.node.NodeProjeto;

import java.util.Date;
import java.util.Objects;

public class Client implements Runnable {

    String idClient;
    int amount;

    public Client(String clientId, int amount) {
        this.idClient = clientId;
        this.amount = amount;
    }

    ClientConfig config = new ClientConfig();
    jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

    @Override
    public void run() {
        String url = "http://" + idClient + ":8081" + "/rest/nodes/";
        WebTarget target = client.target(url);

        Node n = NodeProjeto.nodeResource.getNode(idClient);

        if(n==null){
            n = new Node(idClient, 0);
        }


        System.out.println(url);
        System.out.println(target.getUri());


        try{
            Response r = target.path(idClient).queryParam("amount", amount).queryParam("nodeFrom",NodeProjeto.id).request()
                    .accept(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(n, MediaType.APPLICATION_JSON));
            if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity() ){
                int amount = r.readEntity(int.class);
                if(amount != -1){
                    System.out.println("Transferiu o seguinte valor da sua conta: " + amount);
                    Node neu = NodeProjeto.nodeResource.getNode(NodeProjeto.id);
                    neu.setAmount(neu.getAmount() - this.amount);
                    NodeProjeto.nodeResource.addNode(neu);
                    Date date = new Date();
                    long timeMilli = date.getTime();
                    String idRetrans = NodeProjeto.id + "00" +timeMilli;
                    new Thread(new GossipClient(neu.getNodeId(),idRetrans,neu.getAmount(),NodeProjeto.numTTL)).start();
                }else{
                    //consideramos que este node possa estar a tentar ser malicioso.
                    System.out.println("Ups! Esta transferencia não pode ser realizada. Tenta de novo mais tarde.");
                }

            }else{
                //System.out.println("Error, HTTP error status: " + r.getStatus());
            }

        }catch(ProcessingException i1){

        }




    }


}
