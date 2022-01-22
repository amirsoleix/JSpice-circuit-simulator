package Kernel;

public class Inductor extends Element {
    double inductance;
    double IC = 0;

    public Inductor(String name, double val, Node pNode, Node nNode) {
        super(name,pNode,nNode);
        inductance = val;
        type = "IND";
    }

    public Inductor(String[] inLine) {
    }

    @Override
    public double getCurrent(double time) {
        double sum = this.voltageIntegratorFrom0();
        return (IC + (1 / inductance) * sum);
    }

    @Override
    public double getCurrentFromNegativeNode(double time) {
        double sum = this.voltageIntegratorFrom0() + ((positiveNode.getLastVoltage() - negativeNode.tempV) * CirSim.Dt);
        return (IC + (1 / inductance) * sum);
    }

    @Override
    public double getCurrentFromPositiveNode(double time) {
        double sum = this.voltageIntegratorFrom0() + ((positiveNode.tempV - negativeNode.getLastVoltage()) * CirSim.Dt);
        return (IC + (1 / inductance) * sum);
    }

    public double voltageIntegratorFrom0() {
        double sum = 0;
        for (Double volt : voltageList) {
            sum += (volt * CirSim.Dt);
        }
        return sum;
    }
}
