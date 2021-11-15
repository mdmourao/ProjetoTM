package tm2021.fcul.node.zookeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

public class ZookeeperSearch implements  Runnable{
    String PATH = "/node";
    @Override
    public void run() {
        final ZookeeperProcessor zk;
        try {
            zk = new ZookeeperProcessor( "localhost:2181");
            List<String> lst = zk.getChildren( PATH);
            for(String i : lst){
                System.out.println(i);
            }
            System.out.println(lst.size());
        } catch (Exception e) {

        }

    }
}
