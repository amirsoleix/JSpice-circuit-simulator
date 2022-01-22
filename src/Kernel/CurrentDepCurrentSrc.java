package Kernel;

import Kernel.Circuit;

public class CurrentDepCurrentSrc extends CurrentSrc {
    Element refElm;
    double gain;

    public CurrentDepCurrentSrc() {
        super();
    }

    public CurrentDepCurrentSrc(String name, double gain, Node pN, Node nN, Element refElm) {
        super(name, pN, nN);
        this.gain = gain;
        this.refElm = refElm;
        isDependent = true;
        type = "CCS";
    }

    @Override
    public double getCurrent(double time) {
        return (refElm.getCurrent(time) * gain);
    }
}
