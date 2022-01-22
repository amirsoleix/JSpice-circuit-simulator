package Kernel;

public class CurrentDepVoltageSrc extends VoltageSrc {
    Element refElm;
    double gain;

    public CurrentDepVoltageSrc() {
        super();
    }

    public CurrentDepVoltageSrc(String name, double gain, Node pN, Node nN, Element refElm) {
        super(name, pN, nN);
        this.gain = gain;
        this.refElm = refElm;
        isDependent = true;
        type = "CVS";
    }


    @Override
    public double getVoltage(double time) {
        return (gain * refElm.getCurrent(time));
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
