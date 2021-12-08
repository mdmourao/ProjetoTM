package tm2021.fcul.node.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.inject.Singleton;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.Retransmition;
import tm2021.fcul.api.service.RestNode;
import tm2021.fcul.node.services.GossipClient;

@Singleton
public class NodeResource implements RestNode {

   static public Map<String,Node> listNodes = new HashMap<>();


	static public Map<String,Retransmition> listRetrans = new HashMap<>();

	private static Logger Log = Logger.getLogger(NodeResource.class.getName());
	
	public NodeResource() {
	}


	@Override
	public int updateAmount(String nodeId, int amount, Node node) {
		Log.info("updateAmount : nodeID: = " + nodeId + "; amount = " + amount + " ; node = " + node);
		nodeId = Integer.toString(Math.abs(nodeId.hashCode()));
		Node n = listNodes.get(nodeId);
		if(n==null){
			n = new Node(nodeId, 0);
			listNodes.put(nodeId, n);
		}
		n.setAmount(n.getAmount() + amount);
		listNodes.put(n.getNodeId(),n);
		return amount;
	}

	@Override
	public int sendRetrans( Retransmition retr) {

		Retransmition r = listRetrans.get(retr.getIdRetrans());
		if(r == null){
			Node n = new Node(retr.getNodeId(),retr.getAmount());
			listNodes.put(n.getNodeId(),n);
			int numR = retr.getNumberRetrans() - 1;
			if(numR >= 0){
				GossipClient gc = new GossipClient(retr.getNodeId(),retr.getIdRetrans(),retr.getAmount(),numR);
				gc.run();
			}
			listRetrans.put(retr.getIdRetrans(),r);
		}
		return 0;
	}


	public Node getNode(String id){
		return listNodes.get(id);
	}



	public int getAmount(String id){
		return listNodes.get(id).getAmount();
	}

	public void addNode(Node n){
		listNodes.put(n.getNodeId(),n);
	}





}
