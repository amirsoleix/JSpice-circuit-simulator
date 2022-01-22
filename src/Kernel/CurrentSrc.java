package Kernel;

public class CurrentSrc extends Source {
    //private final String name;
    double Ipk;
    double Idc;
    double phase;
    double frequency;
    boolean isDirect = false;
    boolean isDependent = false;

    public CurrentSrc() {
        super();
    }

    public CurrentSrc(String name, Node pN, Node nN) {
        super(name, pN, nN);
    }

    public CurrentSrc(String name, double Idc, double Ipk, double frequency, double phase, Node pN, Node nN) {
        super(name, pN, nN);
        this.Ipk = Ipk;
        this.Idc = Idc;
        this.frequency = frequency;
        this.phase = phase;
        type = "CURSRC";
        if (Ipk == 0.0) {
            isDirect = true;
        }
        isDependent = false;
    }

    @Override
    public double getCurrent(double time) {
        if (Ipk != 0.0)
            return (Idc + (Ipk * Math.sin((time * frequency * (2 * Math.PI)) + phase)));
        else
            return Idc;
    }
}
