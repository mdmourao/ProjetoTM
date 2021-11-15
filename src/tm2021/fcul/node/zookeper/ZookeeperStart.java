package tm2021.fcul.node.zookeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import tm2021.fcul.node.NodeProjeto;

import java.util.List;

public class ZookeeperStart implements Runnable {
    String PATH = "/node";
    @Override
    public void run() {
        try {

            ZookeeperProcessor zk = new ZookeeperProcessor( "localhost:2181");
            zk.write(PATH, CreateMode.PERSISTENT);

            int pathHash = Math.abs(NodeProjeto.ip.hashCode());
            System.out.println(pathHash);
            PATH = PATH + "/" +pathHash;

            String value = NodeProjeto.ip;
            zk.write(PATH, value, CreateMode.EPHEMERAL);

            System.out.println("ZOOKEPER INFO: " +  PATH + " PATH: "+ value);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
