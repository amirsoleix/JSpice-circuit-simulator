package Kernel;/*
 *   In the name of God
 *   JSpice Electrical circuit simulator
 *   <<<    Summer 1399/2020   >>>
 *   Meysam Amirsardari + Amirmahdi Soleimanifar
 */

import UI.DrawEnvironment;
import UI.Preview;

import java.io.IOException;
import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Preview.show(args);

        //just for test:
        //String filePath = "D:\\Diode_circuit.txt";
        //launch(filePath);
    }

    static Circuit circuit = null;

    public static void launch(String filePath) throws IOException {
        Circuit.nodeList.clear();
        Circuit.elementList.clear();
        Circuit.nodeList.clear();
        Circuit.diodeList.clear();
        circuit = new Circuit(filePath);
        checkNodes();
        checkSources();
        circuit.printCircuit();
        CirSim.simulate(circuit);
        circuit.printUnion();
        CirSim.arrange();
        //Circuit.saveResults();
        CirSim.printResults();
    }

    public static void checkSources() {
        ArrayList<CurrentSrc> list = circuit.curSrcList;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if ((list.get(i).positiveNode.name.equals(list.get(j).negativeNode.name) && list.get(i).positiveNode.adjacentNodes.size() == 2)
                        || (list.get(i).negativeNode.name.equals(list.get(j).positiveNode.name) && list.get(i).negativeNode.adjacentNodes.size() == 2)) {
                    CurrentSrc c1 = list.get(i);
                    CurrentSrc c2 = list.get(j);
                    if (!(c1.Ipk == c2.Ipk && c1.Idc == c2.Idc && c1.frequency == c2.frequency && c1.phase == c2.phase)) {
                        System.out.println("Error -2: Series current sources with different values!!");
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert("Error -2: Series current sources with different values!!","Terminating the simulation...");
                        //System.exit(0);
                    }
                }
                if ((list.get(i).positiveNode.name.equals(list.get(j).positiveNode.name) && list.get(i).positiveNode.adjacentNodes.size() == 2)
                        || (list.get(i).negativeNode.name.equals(list.get(j).negativeNode.name) && list.get(i).negativeNode.adjacentNodes.size() == 2)) {
                    CurrentSrc c1 = list.get(i);
                    CurrentSrc c2 = list.get(j);
                    if (!(c1.Ipk == -c2.Ipk && c1.Idc == -c2.Idc && c1.frequency == c2.frequency && c1.phase == c2.phase)) {
                        System.out.println("Error -2: Series current sources with different values!!");
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert("Error -2: Series current sources with different values!!","Terminating the simulation...");
                        //System.exit(0);
                    }
                }
            }
        }

        ArrayList<VoltageSrc> ls = circuit.volSrcList;
        for (int i = 0; i < ls.size(); i++) {
            for (int j = i + 1; j < ls.size(); j++) {
                if (ls.get(i).positiveNode.name.equals(ls.get(j).positiveNode.name)
                        && ls.get(i).negativeNode.name.equals(ls.get(j).negativeNode.name)) {
                    VoltageSrc c1 = ls.get(i);
                    VoltageSrc c2 = ls.get(j);
                    if (!(c1.Vpk == c2.Vpk && c1.Vdc == c2.Vdc && c1.frequency == c2.frequency && c1.phase == c2.phase)) {
                        System.out.println("Error -3: Parallel voltage sources with different values!!");
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert("Error -3: Parallel voltage sources with different values!!","Terminating the simulation...");
                        //System.exit(0);
                    }
                }
                if (ls.get(i).positiveNode.name.equals(ls.get(j).negativeNode.name)
                        || ls.get(i).negativeNode.name.equals(ls.get(j).positiveNode.name)) {
                    VoltageSrc c1 = ls.get(i);
                    VoltageSrc c2 = ls.get(j);
                    if (!(c1.Vpk == -c2.Vpk && c1.Vdc == -c2.Vdc && c1.frequency == c2.frequency && c1.phase == c2.phase)) {
                        System.out.println("Error -2: Parallel voltage sources with different values!!");
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert("Error -2: Series current sources with different values!!","Terminating the simulation...");
                        //System.exit(0);
                    }
                }
            }
        }
    }

    public static void checkNodes() {
        Node gndNode = null;
        for (Node node : Circuit.nodeList) {
            if (node.adjacentNodes.size() < 2) {
                System.out.println("Error -5: Node " + node.name + " is not in a loop!!");
                System.out.println("Terminating the simulation...");
                DrawEnvironment.showAlert(String.format("Error -5: Node " + node.name + " is not in a loop!!"),"Terminating the simulation...");
                //System.exit(0);
            }
            if (node.name.equals("0")) {
                node.grounded = true;
                gndNode = node;
            }
        }
        for (Node node : gndNode.adjacentNodes) {
            ground(node);
        }
        for (Node node : Circuit.nodeList) {
            if (!node.grounded) {
                System.out.println("Error -5: Node " + node.name + " is not grounded!!");
                System.out.println("Terminating the simulation...");
                DrawEnvironment.showAlert(String.format("Error -5: Node " + node.name + " is not in a loop!!"),"Terminating the simulation...");
                //System.exit(0);
            }
        }
    }

    public static void ground(Node node) {
        if (!node.grounded) {
            for (Node nd : node.adjacentNodes) {
                if (nd.grounded) {
                    node.grounded = true;
                }
            }
            if (node.grounded) {
                for (Node nd : node.adjacentNodes) {
                    ground(nd);
                }
            }
        }
    }

}