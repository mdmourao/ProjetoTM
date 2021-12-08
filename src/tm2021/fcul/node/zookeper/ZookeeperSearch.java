package tm2021.fcul.node.zookeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import tm2021.fcul.node.NodeProjeto;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class ZookeeperSearch{
    String PATH = "/node";

    public void searchNodes() {
        final ZookeeperProcessor zk;
        try {
            zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
            List<String> lst = zk.getChildren( PATH);
            for(String i : lst){
                System.out.println(i);
            }
        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }

    }

    public List<String> getListIPs() {
        final ZookeeperProcessor zk;
        try {
            zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
            List<String> lst = zk.getChildren( PATH);
            List<String> listIPs = new ArrayList<>();
            for (String i : lst){
                listIPs.add(findIpFromId(i));
            }
            return listIPs;
        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }
        List<String> st = new ArrayList<>();
        return st;
    }

    public int nodesCount(){
        final ZookeeperProcessor zk;
        try {
            zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
            List<String> lst = zk.getChildren( PATH);
            return lst.size();
        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }
        return 0;
    }

    public String findIpFromId(String id){
        final ZookeeperProcessor zk;
        try {
            zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
            List<String> lst = zk.getChildren( PATH);
            for(String i: lst){
                if(i.equals(id)){
                    return zk.getValue(PATH + "/" + id);
                }
            }
        } catch (Exception e) {
            NodeProjeto.lg.writetoLogFile( e.getLocalizedMessage());
        }
        return "";
    }
}
