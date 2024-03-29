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
import tm2021.fcul.node.NodeProjeto;

import java.util.Date;
import java.util.HashMap;

public class ClientGetAmount implements Runnable {

    String idClient;


    public ClientGetAmount(String clientId) {
        this.idClient = clientId;
    }

    ClientConfig config = new ClientConfig();
    jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

    @Override
    public void run() {
        String ip = NodeProjeto.zookeeperSearch.findIpFromId(idClient);
        String url = "http://" + ip + ":8081" + "/rest/nodes/";
        WebTarget target = client.target(url);


        try{
            Response r = target.path(idClient).request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            if (r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity()){
                String actual = r.readEntity(String.class);
                System.out.println("O saldo desse cliente é: "+actual);

            }else{

            }
        }catch(ProcessingException i1){
            System.out.println("Neste momento não é possivel ler o saldo deste UID");
        }



    }


}
