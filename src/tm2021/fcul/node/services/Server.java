package tm2021.fcul.node.services;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import tm2021.fcul.node.NodeProjeto;
import tm2021.fcul.node.resources.NodeResource;

import java.net.URI;

public class Server implements Runnable {

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s\n");
    }

    public static final int PORT = 8081;
    public static final String SERVICE = "NodeClientService";

    @Override
    public void run() {
        try {
            String ip = NodeProjeto.ip;

            ResourceConfig config = new ResourceConfig();
            config.register(NodeResource.class);

            String serverURI = String.format("http://%s:%s/rest", ip, PORT);
            JdkHttpServerFactory.createHttpServer(URI.create(serverURI), config);

            NodeProjeto.lg.writetoLogFile(String.format("%s Server ready @ %s\n", SERVICE, serverURI) );

        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }
    }
}
