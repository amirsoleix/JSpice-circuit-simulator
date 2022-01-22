package Kernel;

public class
Capacitor extends Element {
    double capacity;
    double IC = 0;


    public Capacitor(String name, double val, Node pNode, Node nNode) {
        super(name,pNode,nNode);
        capacity = val;
        type = "CAP";
    }

    @Override
    public double getCurrent(double time) {
        int lastIndex = voltageList.size() - 1;
        return (capacity * (voltageList.get(lastIndex) - voltageList.get(lastIndex - 1)) / CirSim.Dt);
    }

    @Override
    public double getCurrentFromNegativeNode(double time) {
        int lastIndex = voltageList.size() - 1;
        return (capacity * ((positiveNode.getLastVoltage() - negativeNode.tempV) - voltageList.get(lastIndex)) / CirSim.Dt);
    }

    @Override
    public double getCurrentFromPositiveNode(double time) {
        int lastIndex = voltageList.size() - 1;
        return (capacity * ((positiveNode.tempV - negativeNode.getLastVoltage()) - voltageList.get(lastIndex)) / CirSim.Dt);
    }
}
