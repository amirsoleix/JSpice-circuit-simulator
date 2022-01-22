package Kernel;

public class VoltageSrc extends Source {
    double Vpk;
    double Vdc;
    double phase;
    double frequency;
    boolean isDirect = false;
    boolean isDependent = false;

    public VoltageSrc() {
        super();
    }

    public VoltageSrc(String name, Node pN, Node nN) {
        super(name, pN, nN);
    }

    public VoltageSrc(String name, double Vdc, double Vpk, double frequency, double phase, Node pN, Node nN) {
        super(name, pN, nN);
        this.Vpk = Vpk;
        this.Vdc = Vdc;
        this.frequency = frequency;
        this.phase = phase;
        type = "VOLSRC";
        if (Vpk == 0.0) {
            isDirect = true;
        }
        isDependent = false;
    }

    @Override
    public double getVoltage(double time) {
        if (Vpk != 0.0)
            return (Vdc + (Vpk * Math.sin((time * frequency * (2 * Math.PI)) + phase)));
        else
            return Vdc;
    }
/*
    @Override
    public double getCurrent(double time) {
        double totalCurrent = 0;
        boolean isItOk = true;
        try {
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
        } catch (StackOverflowError e){
            e.printStackTrace();
        }
        return -1 * totalCurrent;
    }

    @Override
    public double getCurrentFromNegativeNode(double time) {
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
                totalCurrent -= element.getCurrentFromPositiveNode(time);
            }
            for (Element element : negativeNode.negativeElementList) {
                totalCurrent += element.getCurrentFromNegativeNode(time);
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

    @Override
    public double getCurrentFromPositiveNode(double time) {
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
                totalCurrent -= element.getCurrentFromPositiveNode(time);
            }
            for (Element element : positiveNode.negativeElementList) {
                totalCurrent += element.getCurrentFromNegativeNode(time);
            }
        }
        return -1 * totalCurrent;
    }
 */
}
