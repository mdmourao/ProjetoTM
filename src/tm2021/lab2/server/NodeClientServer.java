package tm2021.lab2.server;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import tm2021.lab2.api.Node;
import tm2021.lab2.api.service.RestNode;
import tm2021.lab2.server.resources.NodeResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;

class Server implements Runnable{

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s\n");
    }
    public static final int PORT = 8080;
    public static final String SERVICE = "NodeClientService";
    @Override
    public void run() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();

            ResourceConfig config = new ResourceConfig();
            config.register(NodeResource.class);

            String serverURI = String.format("http://%s:%s/rest", ip, PORT);
            JdkHttpServerFactory.createHttpServer( URI.create(serverURI), config);

            System.out.println(String.format("%s Server ready @ %s\n",  SERVICE, serverURI));

        } catch( Exception e) {
            System.out.println("Erro Cliente");
        }
    }
}

class Client implements Runnable{

    String idClient;
    int amount;

    Client(String clientId, int amount){
        this.idClient = clientId;
        this.amount = amount;
    }

    @Override
    public void run() {
        ClientConfig config = new ClientConfig();
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient(config);

        String url = "http://" + idClient + ":8080"+"/rest";
        WebTarget target = client.target( url ).path( RestNode.PATH );

        Node n = new Node( idClient,0);


        Response r = target.path(idClient).queryParam("amount",  amount).request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(n, MediaType.APPLICATION_JSON));
        if( r.getStatus() == Response.Status.OK.getStatusCode() && r.hasEntity() )
            System.out.println("Success, updated amount with id: " + r.readEntity(String.class) );
        else
            System.out.println("Error, HTTP error status: " + r.getStatus() );
    }
}

public class NodeClientServer {
    public static void main(String[] args) throws IOException {

        Server s = new Server();
        s.run();
        System.out.println("Bem-Vindo");
        while(true){
            System.out.println("Opção 1 - Transferir Dinheiro");
            System.out.println("Opção 2 - Consultar Saldo");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String opcao = reader.readLine();
            switch (opcao){
                case "1":
                    //TODO
                    System.out.println("UID do cliente:");
                    String uidClient = reader.readLine();
                    System.out.println("Dinhero a transferir:");
                    String amountTransfer = reader.readLine();
                    Client c = new Client(uidClient,Integer.parseInt(amountTransfer));
                    c.run();
                case "2":
                    System.out.println( NodeResource.n.getAmount());
            }
        }

    }
}
