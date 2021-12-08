package tm2021.fcul.node.services;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.Retransmition;
import tm2021.fcul.node.NodeProjeto;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class GossipClient implements Runnable {

    String id;
    int amount;
    int numRetrans;

    public GossipClient(String id, int amount, int numRetrans) {
        this.id = id;
        this.amount = amount;
        this.numRetrans = numRetrans;
    }

    ClientConfig config = new ClientConfig();
    jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

    @Override
    public void run() {
        System.out.println("DEBUG: comecei o gossip");
        List<String> listIPS = NodeProjeto.zookeeperSearch.getListIPs();
        System.out.println("SIZE IPS " + listIPS.size());
        if(listIPS.size() > 1){
            Random random = new Random();
            int randomIP = random.nextInt(listIPS.size());
            System.out.println("DEBUG: 1 " + listIPS.get(randomIP));
            sendRequest(listIPS.get(randomIP));
            randomIP = random.nextInt(listIPS.size());
            System.out.println("DEBUG: 2 " + listIPS.get(randomIP));
            sendRequest(listIPS.get(randomIP));
            randomIP = random.nextInt(listIPS.size());
            System.out.println("DEBUG: 3 " + listIPS.get(randomIP));
            sendRequest(listIPS.get(randomIP));
            randomIP = random.nextInt(listIPS.size());
            System.out.println("DEBUG: 4 " + listIPS.get(randomIP));
            sendRequest(listIPS.get(randomIP));
            randomIP = random.nextInt(listIPS.size());
            System.out.println("DEBUG: 5 " + listIPS.get(randomIP));
            sendRequest(listIPS.get(randomIP));
        }



    }

    public void sendRequest(String ipClient) {
        System.out.println("DEBUG: comecei a enviar um pedido ");
        String url2 = "http://" + ipClient + ":8081" + "/rest/retrans/";
        WebTarget target2 = client.target(url2);
        Date date = new Date();
        long timeMilli = date.getTime();
        String idRetrans = id + timeMilli;
        Retransmition retrans = new Retransmition(idRetrans, id, amount, numRetrans);
        Response r2 = target2.request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(retrans, MediaType.APPLICATION_JSON));


        if (r2.getStatus() == Response.Status.OK.getStatusCode() && r2.hasEntity()) {
            System.out.println("RETRANS " + id + "Numero retrans " + numRetrans);
        } else {
            System.out.println("Error RETRANS, HTTP error status: " + r2.getStatus());
        }

    }
}
