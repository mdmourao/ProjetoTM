package tm2021.fcul.node.resources;

import java.util.logging.Logger;

import jakarta.inject.Singleton;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.service.RestNode;
import tm2021.fcul.node.NodeProjeto;

@Singleton
public class NodeResource implements RestNode {

   static public Node n = new Node(NodeProjeto.ip,0);

	private static Logger Log = Logger.getLogger(NodeResource.class.getName());
	
	public NodeResource() {
	}


	@Override
	public int updateAmount(String nodeId, int amount, Node node) {
		Log.info("updateAmount : nodeID: = " + nodeId + "; amount = " + amount + " ; node = " + node);
		n.setAmount(amount);
		return amount;
	}
}
