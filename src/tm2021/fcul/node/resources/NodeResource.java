package tm2021.fcul.node.resources;

import java.util.*;
import java.util.logging.Logger;

import jakarta.inject.Singleton;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.Retransmition;
import tm2021.fcul.api.service.RestNode;
import tm2021.fcul.node.NodeProjeto;
import tm2021.fcul.node.services.GossipClient;

@Singleton
public class NodeResource implements RestNode {

   static public Map<String,Node> listNodes = new HashMap<>();


	static public Map<String,Retransmition> listRetrans = new HashMap<>();

	private static Logger Log = Logger.getLogger(NodeResource.class.getName());
	
	public NodeResource() {
	}
	

	@Override
	public int updateAmount(String nodeId, int amount, String nodeFrom, Node node) {
		Log.info("updateAmount : nodeID: = " + nodeId + "; amount = " + amount + " ; node = " + node);
		nodeId = Integer.toString(Math.abs(nodeId.hashCode()));
		Node n = listNodes.get(nodeId);
		Node nFrom = listNodes.get(nodeFrom);
		if(nFrom==null){
			return -1;
		}
		n.setAmount(n.getAmount() + amount);
		listNodes.put(n.getNodeId(),n);
		Date date = new Date();
		long timeMilli = date.getTime();
		String idRetrans = nodeId + timeMilli;
		new Thread(new GossipClient(nodeId,idRetrans,n.getAmount(), NodeProjeto.numTTL)).start();
		return amount;
	}

	@Override
	public int getAmountFrom() {
		return listNodes.get(NodeProjeto.id).getAmount();
	}

	@Override
	public int sendRetrans( Retransmition retr) {

		Retransmition r = listRetrans.get(retr.getIdRetrans());
		if(r == null){
			Node n = new Node(retr.getNodeId(),retr.getAmount());
			listNodes.put(n.getNodeId(),n);
			int numR = retr.getNumberTTL() - 1;
			if(numR >= 0){
				new Thread(new GossipClient(retr.getNodeId(),retr.getIdRetrans(),retr.getAmount(),numR)).start();
			}
			listRetrans.put(retr.getIdRetrans(),r);
		}
		return 0;
	}

	@Override
	public Map<String,Node> getAllTransacoes() {
		return listNodes;
	}


	public Node getNode(String id){
		return listNodes.get(id);
	}





	public int getAmount(String id){
		if(listNodes.get(id) != null){
			return listNodes.get(id).getAmount();
		}else{
			return -1;
		}

	}

	public void addNode(Node n){
		listNodes.put(n.getNodeId(),n);
	}


	public void estadolista(){
		for(String i : listNodes.keySet()){
			System.out.println(listNodes.get(i).getNodeId());
			System.out.println(listNodes.get(i).getAmount());
		}
	}


}
