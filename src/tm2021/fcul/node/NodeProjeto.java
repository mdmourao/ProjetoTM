package tm2021.fcul.node;

import tm2021.fcul.api.Node;
import tm2021.fcul.node.resources.NodeResource;
import tm2021.fcul.node.services.Client;
import tm2021.fcul.node.services.GossipClient;
import tm2021.fcul.node.services.LogFile;
import tm2021.fcul.node.services.Server;
import tm2021.fcul.node.zookeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

public class NodeProjeto {

    //Se mantiverem vazio ele vai usar o ip default para a rede
    // Isto é importante para as imagens de docker terem ips diferentes
    public static String ip = "" ;
    public static String id;
    public static int amount = 0;
    public static int numberRetrans = 2;
    public static NodeResource nodeResource = new NodeResource();
    public static ZookeeperSearch zookeeperSearch = new ZookeeperSearch();
    public static LogFile lg;


    public static void initNode(){
        int nodesCount = zookeeperSearch.nodesCount();
        Random random = new Random();
        int randomAmount = random.nextInt(100) + 1;
        amount = randomAmount;

        Node n = new Node(id, randomAmount);

        nodeResource.addNode(n);
    }



    public static void main(String[] args) throws IOException {
        lg = new LogFile();
        lg.writetoLogFile("START Programa");
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

        initNode();

        Server s = new Server();
        s.run();

        ZookeeperStart zp = new ZookeeperStart();
        zp.run();

        GossipClient gc = new GossipClient(id,amount,numberRetrans);
        gc.run();



        System.out.println("Bem-Vindo");
        while(true){
            System.out.println("Opção 1 - Transferir Dinheiro");
            System.out.println("Opção 2 - Consultar Saldo");
            System.out.println("Opção 3 - Descobrir Nodes");
            System.out.println("Opção 4 - ");
            System.out.println("Opção 5 - Aceders aos Logs");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String opcao = null;
            try {
                opcao = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (opcao){
                case "1":
                    System.out.println("UID do cliente:");
                    String uidClient = null;
                    try {
                        uidClient = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Dinhero a transferir:");
                    String amountTransfer = null;
                    try {
                        amountTransfer = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Client c = new Client(uidClient,Integer.parseInt(amountTransfer));
                    c.run();
                    break;
                case "2":
                    System.out.println(nodeResource.getAmount(id));
                    break;
                case "3":
                    zookeeperSearch.searchNodes();
                    break;
                case "4":
                    System.out.println(zookeeperSearch.findIpFromId(id));
                    break;
                case "5":
                    lg.readFile();
                    break;
            }
        }



    }

    public static void menu(){

    }
}
