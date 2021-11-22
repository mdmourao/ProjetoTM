package tm2021.fcul.node;

import tm2021.fcul.api.Node;
import tm2021.fcul.node.resources.NodeResource;
import tm2021.fcul.node.services.Client;
import tm2021.fcul.node.services.Server;
import tm2021.fcul.node.zookeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

public class NodeProjeto {

    //Se mantiverem vazio ele vai usar o ip default para a rede
    // Isto é importante para as imagens de docker terem ips diferentes
    public static String ip = "" ;
    public static String id;
    public static NodeResource nodeResource = new NodeResource();
    public static ZookeeperSearch zookeeperSearch = new ZookeeperSearch();


    public static void initNode(){
        int nodesCount = zookeeperSearch.nodesCount();
        Random random = new Random();
        int randomAmount = random.nextInt(100) + 1;

        Node n = new Node(id, randomAmount);

        nodeResource.addNode(n);
    }



    public static void main(String[] args) throws IOException {
        if(ip == ""){
            /*
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = in.readLine();
            System.out.println("aqui");
            System.out.println(ip);
            */
            if(ip == ""){
                Socket sToIp = new Socket();
                sToIp.connect(new InetSocketAddress("google.com",80));
                ip = sToIp.getLocalAddress().toString().substring(1);
            }
        }
        id = Integer.toString(Math.abs(NodeProjeto.ip.hashCode()));
        Server s = new Server();
        s.run();
        ZookeeperStart zp = new ZookeeperStart();
        zp.run();
        initNode();
        System.out.println("Bem-Vindo");
        while(true){
            System.out.println("Opção 1 - Transferir Dinheiro");
            System.out.println("Opção 2 - Consultar Saldo");
            System.out.println("Opção 3 - Descobrir Nodes");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String opcao = reader.readLine();
            switch (opcao){
                case "1":
                    //TODO
                    System.out.println("UID do cliente:");
                    String uidClient = reader.readLine();
                    System.out.println("Dinhero a transferir:");
                    String amountTransfer = reader.readLine();
                    uidClient = zookeeperSearch.findIpFromId(uidClient);
                    Client c = new Client(uidClient,Integer.parseInt(amountTransfer));
                    c.run();
                case "2":
                    System.out.println(nodeResource.getAmount(id));
                case "3":
                    zookeeperSearch.searchNodes();
                case "4":
                    System.out.println(zookeeperSearch.findIpFromId(id));
            }
        }

    }
}
