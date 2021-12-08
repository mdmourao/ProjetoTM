package tm2021.fcul.node.zookeper;

import org.apache.zookeeper.CreateMode;
import tm2021.fcul.node.NodeProjeto;

public class ZookeeperStart implements Runnable {
    String PATH = "/node";
    @Override
    public void run() {
        try {

            ZookeeperProcessor zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
            //zk.write(PATH, CreateMode.PERSISTENT);

            int pathHash = Math.abs(NodeProjeto.ip.hashCode());
            System.out.println(pathHash);
            PATH = PATH + "/" +pathHash;

            String value = NodeProjeto.ip;
            zk.write(PATH, value, CreateMode.EPHEMERAL);

            NodeProjeto.lg.writetoLogFile("ZOOKEPER START INFO: " +  PATH + " PATH: "+ value);


        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }

    }
}
