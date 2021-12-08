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

public class Client implements Runnable {

    String idClient;
    int amount;

    public Client(String clientId, int amount) {
        this.idClient = clientId;
        this.amount = amount;
    }

    @Override
    public void run() {
        ClientConfig config = new ClientConfig();
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);
        String url = "http://" + idClient + ":8081" + "/rest/nodes/";
        WebTarget target = client.target(url);

        Node n = NodeProjeto.nodeResource.getNode(idClient);

        if(n==null){
            n = new Node(idClient, 0);
        }


        System.out.println(url);
        System.out.println(target.getUri());

        Response r = target.path(idClient).queryParam("amount", amount).request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(n, MediaType.APPLICATION_JSON));
        if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity()){
            System.out.println("Success, updated amount with id: " + r.readEntity(String.class));
           // sendInfo("273848bhfynjy", n.getAmount(),n.getNodeId());

            String url2 = "http://" + idClient + ":8081" + "/rest/retrans/";
            WebTarget target2 = client.target(url2);

            //Retransmition retrans = new Retransmition("273848bhfynjy",  n.getNodeId(),5,5);
            Node ntest = new Node("1222",12);
            Response r2 = target2.request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(ntest, MediaType.APPLICATION_JSON));



            if( r2.getStatus() == Response.Status.OK.getStatusCode() && r2.hasEntity()){
                System.out.println("OK!!");
            }else{
                System.out.println("ErrorR2, HTTP error status: " + r2.getStatus());
            }

        }else{
            System.out.println("Error, HTTP error status: " + r.getStatus());
        }

    }

    public void sendInfo(String idTrans, int amount, String idNode){
        ClientConfig config = new ClientConfig();
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);
        String url = "http://" + idClient + ":8081" + "/rest/nodes";
        WebTarget target = client.target(url);

        System.out.println(url);
        System.out.println(target.getUri());

        Retransmition retrans = new Retransmition(idTrans, idNode, amount,5);
        System.out.println("INFOS::");
        System.out.println(retrans);
        System.out.println(url);

        Response r = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(retrans, MediaType.APPLICATION_JSON));
        if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity())
            System.out.println("Success, updated amount with id: " + r.readEntity(String.class));
        else
            System.out.println();
            System.out.println("Error, HTTP error status: " + r.getStatus());
    }
}
