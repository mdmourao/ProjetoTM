package tm2021.fcul.api;

public class Retransmition {

    private String idRetrans;
    private String nodeId;
    private int amount;
    private int numberTTL;

    public Retransmition() {

    }

    public Retransmition(String idRetrans, String nodeId, int amount, int numberTTL) {
        this.idRetrans = idRetrans;
        this.nodeId = nodeId;
        this.amount = amount;
        this.numberTTL = numberTTL;
    }

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

    public int getNumberTTL() {
        return numberTTL;
    }

    public void setNumberTTL(int numberTTL) {
        this.numberTTL = numberTTL;
    }


    @Override
    public String toString() {
        return "Retransmition{" +
                "idRetrans='" + idRetrans + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", amount=" + amount +
                ", numberRetrans=" + numberTTL +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Retransmition other = (Retransmition) obj;
        return idRetrans.equals(other.idRetrans);
    }
}
