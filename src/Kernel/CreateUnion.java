package Kernel;

import UI.DrawEnvironment;

import java.util.ArrayList;

public class CreateUnion {
    Circuit circuit;

    public CreateUnion(Circuit circuit) {
        this.circuit = circuit;
    }

    public void create() {
        Node groundNode = null;
        for (Node node : Circuit.nodeList) {
            if (Integer.parseInt(node.name) == 0) {
                groundNode = node;
            }
        }
        if (groundNode == null) {
            System.out.println("No ground found in circuit!!");
            System.out.println("Terminating the simulation...");
            DrawEnvironment.showAlert("No ground found in circuit!!","Error!");
            //System.exit(0);
        }
        for (VoltageSrc vSrc : circuit.volSrcList) {
            if ((vSrc.positiveNode.name.equals(vSrc.negativeNode.name)) &&
                    (vSrc.Vdc != 0 || vSrc.Vpk != 0)) {
                System.out.println("Error -4: Voltage source is not operational!! (Two terminals in the same end)");
                System.out.println("Terminating the simulation...");
                DrawEnvironment.showAlert("Error -4: Voltage source is not operational!! (Two terminals in the same end)","Error -4:");
                //System.exit(0);
            }
        }

        setAllNodeNotAdded();
        dependantNodeInquiry(groundNode);
        int i = 0;
        for (Node node : Circuit.nodeList) {
            boolean found = false;
            for (Union union : Circuit.unionList) {
                if (union.unionStarter.name.equals(node.parentNode.name)) {
                    union.addNode(node);
                    node.belongUnion = union;
                    found = true;
                }
            }
            if (found == false) {
                Union union = new Union(node.parentNode, i);
                i++;
                node.belongUnion = union;
                union.addNode(node);
                Circuit.unionList.add(union);
            }
        }
        addElement();
    }

    void setAllNodeNotAdded() {
        for (Node node : Circuit.nodeList) {
            node.isAdded = false;
            node.parentNode = null;
        }
    }

    void addElement() {
        for (Element elem : Circuit.elementList) {
            if (elem.positiveNode.belongUnion.index == elem.negativeNode.belongUnion.index) {
                elem.positiveNode.belongUnion.elementList.add(elem);
            }
        }
    }

    void dependantNodeInquiry(Node inNode) {
        if (inNode.parentNode == null) {
            inNode.parentNode = inNode;
        }
        ArrayList<Node> notAddedAdjacentNodeList = new ArrayList<Node>();
        for (Node adjacentNode : inNode.adjacentNodes) {
            for (VoltageSrc vSrc : circuit.volSrcList) {
                if ((vSrc.positiveNode.name.equals(inNode.name) && vSrc.negativeNode.name.equals(adjacentNode.name)) ||
                        (vSrc.negativeNode.name.equals(inNode.name) && vSrc.positiveNode.name.equals(adjacentNode.name))) {
                    if (adjacentNode.parentNode == null) {
                        adjacentNode.parentNode = inNode;
                    } else {
                        inNode.parentNode = adjacentNode;
                    }
                }
            }
        }
        inNode.isAdded = true;
        for (Node node : inNode.adjacentNodes) {
            if (!node.isAdded) {
                dependantNodeInquiry(node);
            }
        }
    }

}