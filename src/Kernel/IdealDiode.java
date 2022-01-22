package Kernel;

public class IdealDiode extends VoltageSrc {
    boolean isON;
    public IdealDiode(String name, double val, Node pNode, Node nNode) {
        super(name, 0.0, 0.0, 0.0,0.0, pNode, nNode);
        isON = false;
        isDirect = true;
        isDependent = false;
        type = "DIODE";
        }
}
