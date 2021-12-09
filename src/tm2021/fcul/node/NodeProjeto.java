package tm2021.fcul.node;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import tm2021.fcul.api.Node;
import tm2021.fcul.node.resources.NodeResource;
import tm2021.fcul.node.services.*;
import tm2021.fcul.node.zookeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
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
        Logger.getRootLogger().setLevel(Level.OFF);
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

        try {
            Random r = new Random();
            int i = r.nextInt(3) + 7 ;
            System.out.println("Aguarde a iniciar o sistema em:");
            for(int a = 0; a < i;a++){
                System.out.print(a);
                TimeUnit.SECONDS.sleep(1);       
            }
            System.out.println("");
            new Thread(new ZookeeperStart()).start();
        } catch (InterruptedException e) {       
            //e.printStackTrace();
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String ipRandom = zookeeperSearch.getRandomIdFromZK();
                if(!Objects.equals(ipRandom, "")){
                    new Thread(new ClientPullInfo(ipRandom)).start();
                }
                ipRandom = zookeeperSearch.getRandomIdFromZK();
                if(!Objects.equals(ipRandom, "")){
                    new Thread(new ClientPullInfo(ipRandom)).start();
                }
                ipRandom = zookeeperSearch.getRandomIdFromZK();
                if(!Objects.equals(ipRandom, "")){
                    new Thread(new ClientPullInfo(ipRandom)).start();
                }
            }
        });
         t1.start();



        new Thread(new GossipClient(id,"",amount, numTTL)).start();
        System.out.println("Bem-Vindo");
        System.out.println("O teu id é: " + id);
        System.out.println("_____________________");
        System.out.println("Opção 1 - transfer (from, to, amount)");
        System.out.println("Opção 2 - Read (from)");
        System.out.println("Opção 3 - Descobrir Nodes");
        System.out.println("Opção 4 - Consultar o meu saldo");
        System.out.println("DEBUG:");
        System.out.println("Opção 5 - Saldos que tenho conhecimento ");
        System.out.println("Opção 6 - total de nodes eu conheço");
        while(true){
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
                    System.out.println("UID do cliente:");
                    uidClient = reader.readLine();
                    if(nodeResource.getAmount(uidClient) != -1){
                        System.out.println(nodeResource.getAmount(uidClient));
                    }else{
                        new Thread(new ClientGetAmount(uidClient)).start();
                    }
                    break;
                case "3":
                    zookeeperSearch.searchNodes();
                    break;
                case "4":
                    System.out.println(nodeResource.getAmount(id));
                    break;
                case "5":
                    nodeResource.estadolista();
                    break;
                case "6":
                    System.out.println(nodeResource.getAllTransacoes().size());
                    break;
            }
        }

    }
}
