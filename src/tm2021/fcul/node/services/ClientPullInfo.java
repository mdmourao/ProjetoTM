package tm2021.fcul.node.services;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import tm2021.fcul.api.Node;
import tm2021.fcul.node.NodeProjeto;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

public class ClientPullInfo implements Runnable {

    String idClient;


    public ClientPullInfo(String clientId) {
        this.idClient = clientId;
    }

    ClientConfig config = new ClientConfig();
    jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

    @Override
    public void run() {
        String ip = NodeProjeto.zookeeperSearch.findIpFromId(idClient);
        String url = "http://" + ip + ":8081" + "/rest/transacoes";
        WebTarget target = client.target(url);

        try{
            Response r = target.request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity()){
                HashMap<String, Node> actual = r.readEntity(new GenericType<HashMap<String, Node>>() { });
                for(String i : actual.keySet()){
                    NodeProjeto.nodeResource.addNode(actual.get(i));
                }
            }
        }catch(ProcessingException i1){

            }
    }


    }
