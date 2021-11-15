package tm2021.fcul.node.resources;

import java.util.logging.Logger;

import jakarta.inject.Singleton;
import tm2021.fcul.api.Node;
import tm2021.fcul.api.service.RestNode;

@Singleton
public class NodeResource implements RestNode {

	//TODO Definir com IP proprio
   static public Node n = new Node("10.101.220.126",0);

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
