package tm2021.fcul.node.services;

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
        String ip = NodeProjeto.zookeeperSearch.findIpFromId(idClient);
        String url = "http://" + ip + ":8081" + "/rest/nodes/";
        WebTarget target = client.target(url);

        Node n = NodeProjeto.nodeResource.getNode(idClient);

        if(n==null){
            n = new Node(idClient, 0);
        }


        Response r = target.path(idClient).queryParam("amount", amount).request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(n, MediaType.APPLICATION_JSON));
        if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity()){
            //TODO
            NodeProjeto.lg.writetoLogFile( "SUCESSO, enviei dinheiro para: " + r.readEntity(String.class));
            System.out.println("SUCESSO, enviei dinheiro para: " + r.readEntity(String.class));
        }else{
            NodeProjeto.lg.writetoLogFile( "Error, HTTP error status: " + r.getStatus());
        }

    }


}
