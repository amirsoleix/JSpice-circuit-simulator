package Kernel;

public class VoltageDepCurrentSrc extends CurrentSrc {
    Node refNode1;
    Node refNode2;
    Node node1;
    Node node2;
    double gain;

    public VoltageDepCurrentSrc() {
        super();
    }

    public VoltageDepCurrentSrc(String name, double gain, Node pN, Node nN, Node pDepNode, Node nDepNode) {
        super(name, pN, nN);
        this.gain = gain;
        refNode1 = pDepNode;
        refNode2 = nDepNode;

        node1 = pN;
        node2 = nN;
        isDependent = true;
        type = "CCS";
    }

    @Override
    public double getCurrent(double time) {
        double refVoltage = refNode1.getLastVoltage() - refNode2.getLastVoltage();
        return (gain * refVoltage);
    }
}
