package Kernel;

import Kernel.Element;
import Kernel.Launcher;

public class Resistor extends Element {
    double resistance;

    public Resistor(String name, double val, Node pNode, Node nNode) {
        super(name,pNode,nNode);
        resistance = val;
        type = "RES";
    }

    @Override
    public double getCurrent(double time) {
        return (this.getVoltage(time) / resistance);
    }

    @Override
    public double getCurrentFromNegativeNode(double time) {
        return ((positiveNode.getLastVoltage() - negativeNode.tempV) / resistance);
    }

    @Override
    public double getCurrentFromPositiveNode(double time) {
        return ((positiveNode.tempV - negativeNode.getLastVoltage()) / resistance);
    }
}
