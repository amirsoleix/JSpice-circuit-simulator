package Kernel;

import java.util.ArrayList;

public class Union {
    public ArrayList<Node> nodeList = new ArrayList<Node>();
    public Node unionStarter;
    public String name;

    public double V;
    public double I_total1;
    public double I_total2;
    public double delta;
    public int index;
    public ArrayList<Double> voltage = new ArrayList<Double>();
    public boolean visited = false;
    public ArrayList<Element> elementList = new ArrayList<Element>();

    // Constructors
    public Union(Node parentU, int index) {
        this.name = parentU.name;
        this.unionStarter = parentU;
        this.index = index;
    }

    public Union(Node parentU, ArrayList<Node> nodeList) {
        addNode(parentU);
        addNode(nodeList);
        for (Node node : this.nodeList) {
            node.belongUnion = this;
        }
        this.name = parentU.name;
        this.unionStarter = parentU;
    }

    // Adds a give node or array of nodes to union list
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    public void addNode(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            if (!this.nodeList.contains(node)) {
                this.nodeList.add(node);
            }
        }
    }


    /***************** getter ***************/
    public ArrayList<Node> getNodeList() {
        return nodeList;
    }
    //public Node getParentUnion() {
    //    return parentUnion;
    //}

    /***************** setter ***************/
    //public void setParentUnion(Node pUnion) {
    //    parentUnion = pUnion;
    //}
    public static void setElementListForAllUnions() {
        for (Union union : Circuit.unionList) {
            for (Node node : union.nodeList) {
                for (Element element : node.elementList) {
                    union.elementList.add(element);
                    element.unionIndex = union.index;
                }
            }
        }
    }

    public void doStep(double time) {
        this.setTempV_ForAllNodes_FromList();
        double Dv = CirSim.Dv;
        double Di = CirSim.Di;
        I_total1 = this.getTotalCurrent(time);
        this.addToTempV_ForAllNodes(Dv);
        I_total2 = this.getTotalCurrent(time);
        double change = ((Math.abs(I_total1) - Math.abs(I_total2)) * Dv) / Di;
        this.addToTempV_ForAllNodes(change);
        delta = Dv + change;
    }

    public double getTotalCurrent(double time) {
        double i = 0;
        for (Node node : nodeList) {
            for (Element element : node.negativeElementList) {
                //if (element.unionIndex != index
                    i += element.getCurrentFromNegativeNode(time);
            }
            for (Element element : node.positiveElementList) {
                //if (element.unionIndex != index)
                    i -= element.getCurrentFromPositiveNode(time);
            }
        }
        return i;
    }

    private void setTempV_ForAllNodes_FromList() {
        for (Node node : Circuit.nodeList) {
            node.tempV = node.voltageList.get(node.voltageList.size() - 1);
        }
    }

    private void addToTempV_ForAllNodes(Double Dv) {
        for (Node node : nodeList) {
            node.tempV += Dv;
        }
    }

    public void addNodeList(ArrayList<Node> nList) {
        for (Node nNode : nList) {
            if (!nodeList.contains(nNode)) {
                nodeList.add(nNode);
            }
        }
    }


}
