package Kernel;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    private final double resCurrent;
    //private final String name;
    public List<Double> currentList = new ArrayList<Double>();
    public List<Double> voltageList = new ArrayList<Double>();
    public Node positiveNode;
    public Node negativeNode;
    // Declare variable current to hold the latest element of currentList
    double current;
    public String name;
    public String type;

    protected int unionIndex;
    int positiveNodeIndex = 0;
    int negativeNodeIndex = 0;

    Element(){
        resCurrent = 0;
        name = null;
    }
    // Element Constructor
    public Element(String elemName, Node pN, Node nN) {
        name = elemName;
        positiveNode = pN;
        negativeNode = nN;
        this.current = 0;
        this.resCurrent = 0;

        // Initialize currentList
        currentList = new ArrayList<Double>();
        currentList.add(0.00);
    }


    public String getName(){
        return this.name;
    }

    public void setUnionIndex(int index) {
        unionIndex = index;
    }
    
    // Voltage related methods
    public double getVoltage(double time) {
        return positiveNode.getLastVoltage() - negativeNode.getLastVoltage();
    }

    // Current related methods
    public double getCurrent(Node node){
        if(positiveNode.name.equals(node.name)){
            return current;
        }
        else if(negativeNode.name.equals(negativeNode.name)){
            return current * -1;
        }
        else{
            System.out.println("This condition should not happen!");
            return  0; // Control current for errors
        }
    }

    public double getCurrent(double time) {
        return 0;
    }

    public double getLastVoltage(){ return (voltageList.get(voltageList.size()-1)); }

    public ArrayList<Double> getCurrentList(){
        return (ArrayList<Double>) currentList;
    }

    public double getCurrentFromNegativeNode(double time) {
        return this.getCurrent(time);
    }

    public double getCurrentFromPositiveNode(double time) {
        return this.getCurrent(time);
    }
}
