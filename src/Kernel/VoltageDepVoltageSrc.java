package Kernel;

public class VoltageDepVoltageSrc extends VoltageSrc {
    Node refNode1;
    Node refNode2;
    Node node1;
    Node node2;
    double gain;

    public VoltageDepVoltageSrc() {
        super();
    }

    public VoltageDepVoltageSrc(String name, double gain, Node pN, Node nN, Node pDepNode, Node nDepNode) {
        super(name, pN, nN);
        this.gain = gain;
        refNode1 = pDepNode;
        refNode2 = nDepNode;
        node1 = pN;
        node2 = nN;
        isDependent = true;
        type = "CVS";
    }

    @Override
    public double getVoltage(double time) {
        double refVoltage = refNode1.getVoltage() - refNode2.getVoltage();
        return (gain * refVoltage);
    }
/*
    @Override
    public double getCurrent(double time) {
        double totalCurrent = 0;
        boolean isItOk = true;
        for (Element element : negativeNode.elementList) {
            if (element.getClass().equals(VoltageSrc.class))
                isItOk = false;
            else if (element.getClass().equals(VoltageDepVoltageSrc.class))
                isItOk = false;
            else if (element.getClass().equals(CurrentDepVoltageSrc.class))
                isItOk = false;
        }
        if (isItOk) {
            for (Element element : negativeNode.positiveElementList) {
                totalCurrent -= element.getCurrent(time);
            }
            for (Element element : negativeNode.negativeElementList) {
                totalCurrent += element.getCurrent(time);
            }
        } else {
            for (Element element : positiveNode.positiveElementList) {
                totalCurrent -= element.getCurrent(time);
            }
            for (Element element : positiveNode.negativeElementList) {
                totalCurrent += element.getCurrent(time);
            }
        }
        return -1 * totalCurrent;
    }
 */
}
