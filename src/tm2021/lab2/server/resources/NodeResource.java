package tm2021.lab2.server.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.inject.Singleton;
import tm2021.lab2.api.Node;
import tm2021.lab2.api.service.RestNode;

@Singleton
public class NodeResource implements RestNode {

   static public Node n = new Node("10.101.221.191",0);

	private static Logger Log = Logger.getLogger(NodeResource.class.getName());
	
	public NodeResource() {
	}


	@Override
	public int updateAmount(String userId, int amount, Node node) {
		Log.info("updateAmount : user = " + userId + "; amount = " + amount + " ; user = " + node);
		n.setAmount(amount);
		return amount;
	}
}
