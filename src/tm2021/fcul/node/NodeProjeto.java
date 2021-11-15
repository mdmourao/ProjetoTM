package tm2021.fcul.node;

import tm2021.fcul.node.resources.NodeResource;
import tm2021.fcul.node.services.Client;
import tm2021.fcul.node.services.Server;
import tm2021.fcul.node.zookeper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NodeProjeto {

    //Se mantiverem vazio ele vai usar o ip default para a rede
    // Isto é importante para as imagens de docker terem ips diferentes
    public static String ip = "" ;

    public static void main(String[] args) throws IOException {
        if(ip == ""){
            Socket sToIp = new Socket();
            sToIp.connect(new InetSocketAddress("google.com",80));
            ip = sToIp.getLocalAddress().toString().substring(1);
        }
        Server s = new Server();
        s.run();
        ZookeeperStart zp = new ZookeeperStart();
        zp.run();
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
                    Client c = new Client(uidClient,Integer.parseInt(amountTransfer));
                    c.run();
                case "2":
                    System.out.println( NodeResource.n.getAmount());
                case "3":
                    ZookeeperSearch zookeeperSearch = new ZookeeperSearch();
                    zookeeperSearch.run();
            }
        }

    }
}
