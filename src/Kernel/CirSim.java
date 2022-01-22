package Kernel;

import java.util.ArrayList;

public abstract class CirSim {
    public static double Dv;
    public static double Di;
    public static double Dt;
    public static double timeDomain;

    public static void simulate(Circuit circuit) {
        int diodeNum = Circuit.diodeList.size();
        if (diodeNum>0) {
            boolean isItOk = false;

            for (int j = 0 ; (j <Math.pow(2,diodeNum))&&(!isItOk) ; j++) {
                for (int k = 0; k <= j%Circuit.diodeList.size() ; k++) {
                    IdealDiode diode = Circuit.diodeList.get(k);
                    diode.isON = !diode.isON;
                }

                ArrayList<Element> tempElm =  Circuit.elementList;
                Circuit.elementList = new ArrayList<Element>();
                for (Element element : tempElm) {
                    if (element!=null){
                        if (element.type!=null){
                            if (element.type.equals("DIODE")){
                                IdealDiode diode = (IdealDiode) element;
                                if (diode.isON){
                                    Circuit.elementList.add(element);
                                }
                            } else {
                                Circuit.elementList.add(element);
                            }
                        }
                    }
                }

                CirSim.setNodesIndexesForElements();
                Node.setNodesForAllElements();
                Node.setElementListForAllNodes();
                Union.setElementListForAllUnions();
                circuit.makeUnions();

                reArrangeElementListForNodes();
                ArrayList<Node> temp = Circuit.nodeList;
                Circuit.nodeList = new ArrayList<Node>();
                for (Node node : temp) {
                    if (!node.name.equals("0"))
                        Circuit.nodeList.add(node);
                }

                setTempVForAllNodes(0);
                setUnionIndexForAllElements();
                initializingLists(0.0);

                int n = (int) (timeDomain / Dt);
                DC_Analyze();

                for (int i = 2; i < n; i++) {
                    solver(i * Dt);

                    isItOk = true;
                    for (IdealDiode diode : Circuit.diodeList) {
                        if (diode.isON) {
                            if (diode.getLastVoltage() < 0)
                                isItOk = false;
                        } else {
                            if (diode.getVoltage(i*Dt) > 0)
                                isItOk = false;
                        }
                    }
                }
            }
        } else {
            CirSim.setNodesIndexesForElements();
            Node.setNodesForAllElements();
            Node.setElementListForAllNodes();
            Union.setElementListForAllUnions();
            circuit.makeUnions();

            reArrangeElementListForNodes();
            ArrayList<Node> temp = Circuit.nodeList;

            Circuit.nodeList = new ArrayList<Node>();
            for (Node node : temp) {
                if (!node.name.equals("0"))
                    Circuit.nodeList.add(node);
            }

            setTempVForAllNodes(0);
            setUnionIndexForAllElements();
            initializingLists(0.0);

            int n = (int) (timeDomain / Dt);
            DC_Analyze();

            for (int i = 2; i < n; i++) {
                solver(i * Dt);
            }
        }
    }

    private static void DC_Analyze(){
        int i=0;
        while (i<35000){
            for (Union union : Circuit.unionList) {
                union.doStep(CirSim.Dt);
                for (Node node : union.nodeList) {
                    node.voltageList.add(node.getTempV());
                }
            }
            i++;
        }
        for (Union union : Circuit.unionList) {
            for (Node node : union.nodeList) {
                node.voltageList.add(node.getTempV());
            }
        }
        for (Element element : Circuit.elementList) {
            element.voltageList.add(element.getVoltage(CirSim.Dt));
            element.currentList.add(element.getCurrent(CirSim.Dt));
        }
    }

    private static void initializingLists(double val){
        for (Element element : Circuit.elementList) {
            if (element.voltageList.size() == 0) {
                element.voltageList.add(val);
                element.currentList.add(val);
            }
        }
        for (Node node : Circuit.nodeList) {
            if (node.voltageList.size() == 0)
                node.voltageList.add(val);
        }
    }

    private static void reArrangeElementListForNodes(){
        boolean found;
        for (Node node : Circuit.nodeList) {
            ArrayList<Element> temp = node.elementList;
            node.elementList = new ArrayList<Element>();
            for (Element element : temp) {
                found = false;
                for (Element addedElm : node.elementList) {
                    if (addedElm.name.equals(element.name))
                        found=true;
                }
                if (!found){
                    node.elementList.add(element);
                }
            }
        }
    }

    private static void solver(double time) {
        for (Union union : Circuit.unionList) {
            union.doStep(time);
            for (Node node : union.nodeList) {
                node.voltageList.add(node.getTempV());
            }
        }
        updateElementsDetails(time);
    }

    private static void updateElementsDetails(double time) {
        for (Element element : Circuit.elementList) {
            element.voltageList.add(element.getVoltage(time));
            element.currentList.add(element.getCurrent(time));
        }
    }

    private static boolean checkAllDeltas() {
        boolean isSet = true;
        for (Union union : Circuit.unionList) {
            if (union.delta > 0.0001)
                isSet = false;
        }
        return isSet;
    }

    private static void setTempVForAllNodes(double tempVoltage) {
        for (Node node : Circuit.nodeList) {
            node.tempV = tempVoltage;
        }
    }

    private static void setUnionIndexForAllElements() {
        int i = 0;
        for (Union union : Circuit.unionList) {
            union.index = i;
            i++;
        }
        for (Union union : Circuit.unionList) {
            for (Node node : union.nodeList) {
                for (Element element : node.elementList) {
                    element.unionIndex = union.index;
                }
            }
        }
    }

    public static void arrange(){
        ArrayList<Element> tempElm =  Circuit.elementList;
        Circuit.elementList = new ArrayList<Element>();
        for (Element element : tempElm) {
            if (!element.type.equals("DIODE")){
                Circuit.elementList.add(element);
            }
        }
    }

    public static void setNodesIndexesForElements(){
        for (Element element : Circuit.elementList) {
            element.negativeNodeIndex = Integer.parseInt(element.negativeNode.name);
            element.positiveNodeIndex = Integer.parseInt(element.positiveNode.name);
        }
    }

    public static void printResults() {
        int j = 1;
        System.out.println("******          Nodes voltages:         ******");
        //for (Node node : Circuit.nodeList) {
        //    System.out.printf("%d", j++);
        //    for (Double voltage : node.voltageList) {
        //        System.out.printf(" %f ", voltage);
        //    }
        //    System.out.printf("\n");
        //}
        for (Node element : Circuit.nodeList) {
            Double b = element.voltageList.get(element.voltageList.size()-1);
            System.out.printf("( %f ) \n",b);
        }
        System.out.println("******          Elements voltages:         ******");
        for (IdealDiode diode : Circuit.diodeList) {
            System.out.printf(diode.getName());
        }
        for (Element element : Circuit.elementList) {
            System.out.printf(element.getName() + " ");
            System.out.printf("(%f , %f ,", element.voltageList.get(element.voltageList.size()-1),
                    element.currentList.get(element.currentList.size()-1));
            System.out.printf(" %f) \n",
                    element.voltageList.get(element.voltageList.size()-1) * element.currentList.get(element.currentList.size()-1));
        }
    }
}
