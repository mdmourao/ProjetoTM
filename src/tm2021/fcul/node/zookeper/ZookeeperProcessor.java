package tm2021.fcul.node.zookeper;

import java.util.Base64;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperProcessor implements Watcher {
    private ZooKeeper zk;
    // IP onde o zookeeper principal corre
    // funciona com ips da aws, para não passamos o limite do free tier encontra-se desligado
    public static String ip = "15.236.208.18:2181";


    public ZookeeperProcessor( String hostPort) throws Exception {
        zk = new ZooKeeper(hostPort, 3000, this);

    }

    public String write( String path, CreateMode mode) {
        try {
            return zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        } catch (KeeperException | InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public String write( String path, String value, CreateMode mode) {
        try {
            return zk.create(path, Base64.getEncoder().encode(value.getBytes()), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        } catch (KeeperException | InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public List<String> getChildren( String path, Watcher watch) {
        try {
            return zk.getChildren(path, watch);
        } catch (KeeperException | InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public List<String> getChildren( String path) {
        try {
            return zk.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public String getValue(String path){
        try {
            return new String (Base64.getDecoder().decode(zk.getData(path, false, null)));
        } catch (KeeperException | InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public void process(WatchedEvent event) {
        //System.out.println( event);
    }

}

