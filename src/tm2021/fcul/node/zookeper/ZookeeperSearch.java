package tm2021.fcul.node.zookeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZookeeperSearch{
    String PATH = "/node";

    public static ZookeeperProcessor zk;

    static {
        try {
            zk = new ZookeeperProcessor( ZookeeperProcessor.ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchNodes() {

        try {

            List<String> lst = zk.getChildren( PATH);
            for(String i : lst){
                System.out.println(i);
            }
            System.out.println(lst.size());
        } catch (Exception e) {

        }

    }

    public String getRandomIdFromZK() {

        try {
            List<String> lst = zk.getChildren( PATH);
            Random r = new Random();
            if(lst.size() > 1){
                int posR = r.nextInt(lst.size());
                return lst.get(posR);
            }else{
                return "";
            }
        } catch (Exception e) {

        }
        return "";
    }

    public List<String> getListIPs() {

        try {

            List<String> lst = zk.getChildren( PATH);
            List<String> listIPs = new ArrayList<>();
            for (String i : lst){
                listIPs.add(findIpFromId(i));
            }
            return listIPs;
        } catch (Exception e) {

        }
        List<String> st = new ArrayList<>();
        return st;
    }

    public int nodesCount(){

        try {

            List<String> lst = zk.getChildren( PATH);
            return lst.size();
        } catch (Exception e) {

        }
        return 0;
    }

    public String findIpFromId(String id){

        try {

            List<String> lst = zk.getChildren( PATH);
            for(String i: lst){
                if(i.equals(id)){
                    return zk.getValue(PATH + "/" + id);
                }
            }
        } catch (Exception e) {
        }
        return "";
    }
}
