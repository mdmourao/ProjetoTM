package tm2021.fcul.node;

import tm2021.fcul.api.Node;
import tm2021.fcul.node.resources.NodeResource;
import tm2021.fcul.node.services.Client;
import tm2021.fcul.node.services.GossipClient;
import tm2021.fcul.node.services.Server;
import tm2021.fcul.node.zookeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NodeProjeto {

    //Se mantiverem vazio ele vai usar o ip default para a rede
    // Isto é importante para as imagens de docker terem ips diferentes
    public static String ip = "" ;
    public static String id;
    public static int amount = 0;
    public static int numTTL = 4;
    public static NodeResource nodeResource = new NodeResource();
    public static ZookeeperSearch zookeeperSearch = new ZookeeperSearch();


    public static void initNode(){
        Random random = new Random();
        int randomAmount = random.nextInt(100) + 1;
        amount = randomAmount;

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


        new Thread(new Server()).start();
        initNode();
        new Thread(new ZookeeperStart()).start();
        try {
            System.out.println("Aguarde a iniciar o sistema em:");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(1);
            TimeUnit.SECONDS.sleep(1);
            System.out.print(2);
            TimeUnit.SECONDS.sleep(1);
            System.out.print(3);
            TimeUnit.SECONDS.sleep(1);
            System.out.print(4);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new GossipClient(id,"",amount, numTTL)).start();
        System.out.println("Bem-Vindo");
        while(true){
            System.out.println("Opção 1 - Transferir Dinheiro");
            System.out.println("Opção 2 - Consultar Saldo");
            System.out.println("Opção 3 - Descobrir Nodes");
            System.out.println("Opção 4 - Saldos que tenho conhecimento");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String opcao = reader.readLine();
            switch (opcao){
                case "1":
                    //TODO
                    System.out.println("UID do cliente:");
                    String uidClient = reader.readLine();
                    System.out.println("Dinhero a transferir:");
                    String amountTransfer = reader.readLine();
                    int amount = Integer.parseInt(amountTransfer);
                    int realamount = nodeResource.getNode(id).getAmount();
                    if(amount > realamount){
                        System.out.println("Ups! Não tens saldo para esta transferencia descentralizada");
                        System.out.println("O teu saldo disponivel é: " + realamount);
                    }else{
                        uidClient = zookeeperSearch.findIpFromId(uidClient);
                        new Thread(new Client(uidClient,amount)).start();
                    }
                    break;
                case "2":
                    System.out.println(nodeResource.getAmount(id));
                    break;
                case "3":
                    zookeeperSearch.searchNodes();
                    break;
                case "4":
                    nodeResource.estadolista();
                    break;
            }
        }

    }
}
