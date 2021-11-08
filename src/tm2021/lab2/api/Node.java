package tm2021.lab2.api;


public class Node {

	private String nodeId;
	private int amount;
	
	public Node(){
	}
	
	public Node(String nodeId, int amount) {
		super();
		this.nodeId = nodeId;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Node{" +
				"nodeId='" + nodeId + '\'' +
				", amount=" + amount +
				'}';
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		return nodeId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return nodeId.equals(other.nodeId);
	}

}
