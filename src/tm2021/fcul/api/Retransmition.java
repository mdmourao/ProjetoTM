package tm2021.fcul.api;

public class Retransmition {

    private String idRetrans;
    private String nodeId;
    private int amount;
    private int numberRetrans;

    public String getIdRetrans() {
        return idRetrans;
    }

    public void setIdRetrans(String idRetrans) {
        this.idRetrans = idRetrans;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getNumberRetrans() {
        return numberRetrans;
    }

    public void setNumberRetrans(int numberRetrans) {
        this.numberRetrans = numberRetrans;
    }

    public Retransmition(String idRetrans, String nodeId, int amount, int numberRetrans) {
        this.idRetrans = idRetrans;
        this.nodeId = nodeId;
        this.amount = amount;
        this.numberRetrans = numberRetrans;
    }

    @Override
    public String toString() {
        return "Retransmition{" +
                "idRetrans='" + idRetrans + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", amount=" + amount +
                ", numberRetrans=" + numberRetrans +
                '}';
    }
}
