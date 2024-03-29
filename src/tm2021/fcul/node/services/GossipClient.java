package tm2021.fcul.node.services;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.Retransmition;
import tm2021.fcul.node.NodeProjeto;
import tm2021.fcul.node.resources.NodeResource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GossipClient implements Runnable {

    String id;
    String idRetrans;
    int amount;
    int numRetrans;

    public GossipClient(String id, String idRetrans ,int amount, int numRetrans) {
        this.id = id;
        this.amount = amount;
        this.numRetrans = numRetrans;
        this.idRetrans = idRetrans;
    }

    ClientConfig config = new ClientConfig();
    jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

    @Override
    public void run() {
        List<String> listIPS = NodeProjeto.zookeeperSearch.getListIPs();
        if(listIPS.size() > 1){
            for(int i = 0; i < NodeProjeto.numTTL; i ++){
                Random random = new Random();
                int randomIP = random.nextInt(listIPS.size());
                sendRequest(listIPS.get(randomIP));
            }
        }
    }

    public void sendRequest(String ipClient) {
        String url2 = "http://" + ipClient + ":8081" + "/rest/retrans/";
        if(idRetrans == ""){
            Date date = new Date();
            long timeMilli = date.getTime();
            idRetrans = id + "00" +timeMilli;
        }
        try{
            Retransmition retrans = new Retransmition(idRetrans, id, amount, numRetrans);
            NodeResource.listRetrans.put(retrans.getIdRetrans(),retrans);
            Response r2 = client.target(url2)
                    .request().header("null",null)
                    .accept(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(retrans, MediaType.APPLICATION_JSON));
        }catch(ProcessingException i1){

        }
    }
}
