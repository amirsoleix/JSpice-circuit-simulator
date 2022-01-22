package Kernel;

import UI.DrawEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {
    // The values of time, voltage and current
    public double dt = 0;
    public double t = 0;
    public double dv = 0;
    public double di = 0;

    // Arrays of elements and nodes
    public ArrayList<Element> elemList = new ArrayList<Element>();
    public ArrayList<Node> nodeList = new ArrayList<Node>();
    public int[][] linkMat = new int[31][31];

    // Element Specific arrayLists
    public ArrayList<Resistor> resList = new ArrayList<Resistor>();
    public ArrayList<Capacitor> capList = new ArrayList<Capacitor>();
    public ArrayList<Inductor> indList = new ArrayList<Inductor>();
    public ArrayList<IdealDiode> diodeList = new ArrayList<>();
    public ArrayList<CurrentSrc> curSrcList = new ArrayList<CurrentSrc>();
    public ArrayList<VoltageSrc> volSrcList = new ArrayList<VoltageSrc>();
    public ArrayList<CurrentDepCurrentSrc> CCCSList = new ArrayList<>();
    public ArrayList<VoltageDepCurrentSrc> VCCSList = new ArrayList<>();
    public ArrayList<CurrentDepVoltageSrc> CCVSList = new ArrayList<>();
    public ArrayList<VoltageDepVoltageSrc> VCVSList = new ArrayList<>();

    // Constructor of FileReader
    Scanner sc;

    public FileReader(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        sc = new Scanner(file);
    }

    public double numProcess(String string, String line) {
        String help;
        double value = 0;
        Pattern num = Pattern.compile("[-]?\\d*([.]\\d+)?");
        Matcher numVal = num.matcher(string);
        if (numVal.find()) {
            help = numVal.group();
            if (help != null) {
                value = Double.parseDouble(help);
            }
        }
        String sufStr = null;
        Pattern suffix = Pattern.compile("[a-zA-z]");
        Matcher sufVal = suffix.matcher(string);
        if (sufVal.find()) {
            sufStr = sufVal.group();
        }
        String st = numVal.group();
        if (sufStr != null) {
            if (sufStr.equals("G")) {
                value *= Math.pow(10, 9);
            } else if (sufStr.equals("M")) {
                value *= Math.pow(10, 6);
            } else if (sufStr.equalsIgnoreCase("k")) {
                value *= Math.pow(10, 3);
            } else if (sufStr.equals("m")) {
                value *= Math.pow(10, -3);
            } else if (sufStr.equals("u")) {
                value *= Math.pow(10, -6);
            } else if (sufStr.equals("n")) {
                value *= Math.pow(10, -9);
            } else if (sufStr.equals("p")) {
                value *= Math.pow(10, -12);
            } else {
                // READING ERROR
                System.out.printf("Error in line:\n\" %s \"\n", line);
                System.out.println("Terminating the simulation...");
                DrawEnvironment.showAlert(String.format("Error in line:\n\" %s \"\n", line),"Terminating the simulation...");
                //System.exit(0);
            }
        }
        return value;
    }

    public boolean reader() {
        double val;
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.startsWith("*") || line.startsWith("$")) {
                // Comment Line: No Action Needed
            } else {
                String[] input = line.split("\\s+");
                int num = input.length;
                // Case where we are determining conditions of simulation or creating elements
                if (num < 2) {
                    // READING ERROR
                    System.out.printf("Error in line:\n\" %s \"\n", line);
                    System.out.println("Terminating the simulation...");
                    DrawEnvironment.showAlert(String.format("Error in line:\n\" %s \"\n", line),"Terminating the simulation...");
                    //System.exit(0);
                } else if (num == 2) {
                    val = numProcess(input[1], line);
                    if (val >= 0) {
                        if (input[0].equals("dt") || input[0].equals("dT")) {
                            dt = val;
                            CirSim.Dt = val;
                        } else if (input[0].equals("dv") || input[0].equals("dV")) {
                            dv = val;
                            CirSim.Dv = val;
                        } else if (input[0].equals("di") || input[0].equals("dI")) {
                            di = val;
                            CirSim.Di = val;
                        } else if (input[0].equals(".tran")) {
                            t = val;
                            CirSim.timeDomain = val;
                            break;
                        } else {
                            // READING ERROR
                            System.out.printf("Error in line (Not a simulation parameter):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Not a simulation parameter):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                    } else {
                        // READING ERROR
                        System.out.printf("Error in line (Simulation parameter cannot have negative value):\n\" %s \"\n", line);
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert(String.format("Error in line (Simulation parameter cannot have negative value):\n\" %s \"\n", line),"Terminating the simulation...");
                        //System.exit(0);
                    }
                } else if (num > 2) {
                    Node pN, nN;
                    String elemName = input[0];
                    String pTName = input[1];
                    String nTName = input[2];
                    for (int i = 0; i < elemList.size(); i++) {
                        if (elemName.equals(elemList.get(i).name)) {
                            // READING ERROR
                            System.out.printf("Error in line (Reinitializing an existing element):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Reinitializing an existing element):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                    }
                    boolean pFound = false;
                    boolean nFound = false;
                    // IDE Gives warning for uninitialized objects
                    pN = null;
                    nN = null;
                    for (int i = 0; i < nodeList.size(); i++) {
                        if (pTName.equals(nodeList.get(i).name)) {
                            pN = nodeList.get(i);
                            pFound = true;
                        }
                        if (nTName.equals(nodeList.get(i).name)) {
                            nN = nodeList.get(i);
                            nFound = true;
                        }
                    }
                    if (!pFound || !nFound) {
                        if (!pFound) {
                            pN = new Node(pTName);
                            nodeList.add(pN);
                        }
                        if (!nFound) {
                            nN = new Node(nTName);
                            nodeList.add(nN);
                        }
                    }
                    if (pN.adjacentNodes.contains(nN) == false) {
                        pN.adjacentNodes.add(nN);
                        nN.adjacentNodes.add(pN);
                    }

                    if (num == 3) {
                        if (input[0].startsWith("d") || input[0].startsWith("D")) {
                            IdealDiode idealDiode = new IdealDiode(elemName, 0, nN, pN);
                            elemList.add(idealDiode);
                            diodeList.add(idealDiode);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(idealDiode);
                            nN.elementList.add(idealDiode);
                        }
                    } else if (num == 4) {
                        // The condition for passive elements
                        input[3] = input[3].replaceAll("[AaVvhH]", "");
                        val = numProcess(input[3], line);
                        if (input[0].startsWith("r") || input[0].startsWith("R")) {
                            if (val < 0) {
                                // READING ERROR
                                System.out.printf("Error in line (Active resistor):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (Active resistor):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                            Resistor resistor = new Resistor(elemName, val, pN, nN);
                            elemList.add(resistor);
                            resList.add(resistor);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(resistor);
                            nN.elementList.add(resistor);
                        } else if (input[0].startsWith("c") || input[0].startsWith("C")) {
                            if (val < 0) {
                                // READING ERROR
                                System.out.printf("Error in line (Negative capacitance):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (Negative capacitance):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                            Capacitor capacitor = new Capacitor(elemName, val, pN, nN);
                            elemList.add(capacitor);
                            capList.add(capacitor);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(capacitor);
                            nN.elementList.add(capacitor);
                        } else if (input[0].startsWith("l") || input[0].startsWith("L")) {
                            if (val < 0) {
                                // READING ERROR
                                System.out.printf("Error in line (Negative inductance):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (Negative capacitance):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                            Inductor inductor = new Inductor(elemName, val, pN, nN);
                            elemList.add(inductor);
                            indList.add(inductor);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(inductor);
                            nN.elementList.add(inductor);
                        } else if (input[0].startsWith("d") || input[0].startsWith("D")) {
                            if (val != 0) {
                                // READING ERROR
                                System.out.printf("Error in line (Convention value not used):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (Convention value not used):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                            IdealDiode idealDiode = new IdealDiode(elemName, val, pN, nN);
                            elemList.add(idealDiode);
                            diodeList.add(idealDiode);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            //pN.elementList.add(idealDiode);
                            //nN.elementList.add(idealDiode);
                        } else if (elemName.startsWith("v") || elemName.startsWith("V")) {
                            VoltageSrc volSrc = new VoltageSrc(elemName, val, 0, 0, 0, pN, nN);
                            elemList.add(volSrc);
                            volSrcList.add(volSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(volSrc);
                            pN.adjacentSources.add(volSrc);
                            nN.elementList.add(volSrc);
                            nN.adjacentSources.add(volSrc);
                        } else if (elemName.startsWith("i") || elemName.startsWith("I")) {
                            CurrentSrc curSrc = new CurrentSrc(elemName, val, 0, 0, 0, nN, pN);
                            elemList.add(curSrc);
                            curSrcList.add(curSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(curSrc);
                            nN.elementList.add(curSrc);
                        } else {
                            // READING ERROR
                            System.out.printf("Error in line (Unknown element):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Unknown element):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                    } else if (num == 5) {
                        // CURRENT CONTROLLED DEPENDENT SOURCES
                        Element refElem = null;
                        String refElemName = input[3];
                        double gain = numProcess(input[4], line);
                        for (int i = 0; i < elemList.size(); i++) {
                            if (elemList.get(i).name.toUpperCase().equals(refElemName.toUpperCase())) {
                                refElem = elemList.get(i);
                            }
                        }
                        if (refElem == null) {
                            // READING ERROR
                            System.out.printf("Error in line (No such reference element for dependency):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (No such reference element for dependency):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        } else if (gain < 0) {
                            // READING ERROR
                            System.out.printf("Error in line (Negative value):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Negative value):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                        if (elemName.startsWith("f") || elemName.startsWith("F")) {
                            CurrentDepCurrentSrc CCCS = new CurrentDepCurrentSrc(elemName, gain, nN, pN, refElem);
                            elemList.add(CCCS);
                            curSrcList.add(CCCS);
                            CCCSList.add(CCCS);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(CCCS);
                            nN.elementList.add(CCCS);
                        } else if (elemName.startsWith("h") || elemName.startsWith("H")) {
                            CurrentDepVoltageSrc CCVS = new CurrentDepVoltageSrc(elemName, gain, pN, nN, refElem);
                            elemList.add(CCVS);
                            volSrcList.add(CCVS);
                            CCVSList.add(CCVS);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(CCVS);
                            pN.adjacentSources.add(CCVS);
                            nN.elementList.add(CCVS);
                            pN.adjacentSources.add(CCVS);
                        } else {
                            // READING ERROR
                            System.out.printf("Error in line (No such dependent source):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (No such dependent source):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }

                    } else if (num == 6) {
                        // VOLTAGE CONTROLLED DEPENDENT SOURCES
                        Node pDepNode = null, nDepNode = null;
                        String pDName = input[3];
                        String nDName = input[4];
                        double gain = numProcess(input[5], line);
                        boolean pDNodeFound = false, nDNodeFound = false;
                        for (int i = 0; i < nodeList.size(); i++) {
                            if (pDName.equals(nodeList.get(i).name)) {
                                pDepNode = nodeList.get(i);
                                pDNodeFound = true;
                            }
                            if (nDName.equals(nodeList.get(i).name)) {
                                nDepNode = nodeList.get(i);
                                nDNodeFound = true;
                            }
                        }
                        if (!pDNodeFound || !nDNodeFound) {
                            // READING ERROR
                            System.out.printf("Error in line (Dependent source dependency unknown):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Dependent source dependency unknown):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        } else if (gain < 0) {
                            // READING ERROR
                            System.out.printf("Error in line (Negative value):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Negative value):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                        if (elemName.startsWith("e") || elemName.startsWith("E")) {
                            VoltageDepVoltageSrc VCVS = new VoltageDepVoltageSrc(elemName, gain, pN, nN, pDepNode, nDepNode);
                            elemList.add(VCVS);
                            volSrcList.add(VCVS);
                            VCVSList.add(VCVS);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(VCVS);
                            pN.adjacentSources.add(VCVS);
                            nN.elementList.add(VCVS);
                            nN.adjacentSources.add(VCVS);
                        } else if (elemName.startsWith("g") || elemName.startsWith("G")) {
                            VoltageDepCurrentSrc VCCS = new VoltageDepCurrentSrc(elemName, gain, nN, pN, pDepNode, nDepNode);
                            elemList.add(VCCS);
                            curSrcList.add(VCCS);
                            VCCSList.add(VCCS);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(VCCS);
                            nN.elementList.add(VCCS);
                        } else {
                            // CURRENT CONTROLLED DEPENDENT SOURCES
                            Element refElem = null;
                            boolean found = false;
                            for (int i = 0; i < elemList.size() && !found; i++) {
                                if (elemList.get(i).positiveNode.name.toUpperCase().equals(pDName.toUpperCase()) &&
                                        elemList.get(i).negativeNode.name.toUpperCase().equals(nDName.toUpperCase())) {
                                    refElem = elemList.get(i);
                                    found = true;
                                }
                            }
                            if (refElem == null) {
                                // READING ERROR
                                System.out.printf("Error in line (No such reference element for dependency):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (No such reference element for dependency):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            } else if (gain < 0) {
                                // READING ERROR
                                System.out.printf("Error in line (Negative value):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (Negative value):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                            if (elemName.startsWith("f") || elemName.startsWith("F")) {
                                CurrentDepCurrentSrc CCCS = new CurrentDepCurrentSrc(elemName, gain, nN, pN, refElem);
                                elemList.add(CCCS);
                                curSrcList.add(CCCS);
                                CCCSList.add(CCCS);
                                linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                                linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                                pN.elementList.add(CCCS);
                                nN.elementList.add(CCCS);
                            } else if (elemName.startsWith("h") || elemName.startsWith("H")) {
                                CurrentDepVoltageSrc CCVS = new CurrentDepVoltageSrc(elemName, gain, pN, nN, refElem);
                                elemList.add(CCVS);
                                volSrcList.add(CCVS);
                                CCVSList.add(CCVS);
                                linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                                linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                                pN.elementList.add(CCVS);
                                pN.adjacentSources.add(CCVS);
                                nN.elementList.add(CCVS);
                                pN.adjacentSources.add(CCVS);
                            } else {
                                // READING ERROR
                                System.out.printf("Error in line (No such dependent source):\n\" %s \"\n", line);
                                System.out.println("Terminating the simulation...");
                                DrawEnvironment.showAlert(String.format("Error in line (No such dependent source):\n\" %s \"\n", line),"Terminating the simulation...");
                                //System.exit(0);
                            }
                        }
                    } else if (num == 7) {
                        double offset = numProcess(input[3], line);
                        input[3] = input[3].replaceAll("[AaVv]", "");
                        double amplitude = numProcess(input[4], line);
                        input[4] = input[4].replaceAll("[AaVv]", "");
                        double frequency = numProcess(input[5], line);
                        double phase = numProcess(input[6], line);
                        if (elemName.startsWith("v") || elemName.startsWith("V")) {
                            VoltageSrc volSrc = new VoltageSrc(elemName, offset, amplitude, frequency, phase, pN, nN);
                            elemList.add(volSrc);
                            volSrcList.add(volSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(volSrc);
                            pN.adjacentSources.add(volSrc);
                            nN.elementList.add(volSrc);
                            nN.adjacentSources.add(volSrc);
                        } else if (elemName.startsWith("i") || elemName.startsWith("I")) {
                            CurrentSrc curSrc = new CurrentSrc(elemName, offset, amplitude, frequency, phase, nN, pN);
                            elemList.add(curSrc);
                            curSrcList.add(curSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(curSrc);
                            nN.elementList.add(curSrc);
                        } else {
                            // READING ERROR
                            System.out.printf("Error in line (Unknown source element):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Unknown source element):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                    } else if (num == 8) {
                        double offset = numProcess(input[4], line);
                        input[4] = input[4].replaceAll("[AaVv]", "");
                        double amplitude = numProcess(input[5], line);
                        input[5] = input[5].replaceAll("[AaVv]", "");
                        double frequency = numProcess(input[6], line);
                        double phase = numProcess(input[7], line);
                        if (elemName.startsWith("v") || elemName.startsWith("V")) {
                            VoltageSrc volSrc = new VoltageSrc(elemName, offset, amplitude, frequency, phase, pN, nN);
                            elemList.add(volSrc);
                            volSrcList.add(volSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(volSrc);
                            pN.adjacentSources.add(volSrc);
                            nN.elementList.add(volSrc);
                            nN.adjacentSources.add(volSrc);
                        } else if (elemName.startsWith("i") || elemName.startsWith("I")) {
                            CurrentSrc curSrc = new CurrentSrc(elemName, offset, amplitude, frequency, phase, nN, pN);
                            elemList.add(curSrc);
                            curSrcList.add(curSrc);
                            linkMat[Integer.parseInt(pN.name)][Integer.parseInt(nN.name)]++;
                            linkMat[Integer.parseInt(nN.name)][Integer.parseInt(pN.name)]++;
                            pN.elementList.add(curSrc);
                            nN.elementList.add(curSrc);
                        } else {
                            // READING ERROR
                            System.out.printf("Error in line (Unknown source element):\n\" %s \"\n", line);
                            System.out.println("Terminating the simulation...");
                            DrawEnvironment.showAlert(String.format("Error in line (Unknown source element):\n\" %s \"\n", line),"Terminating the simulation...");
                            //System.exit(0);
                        }
                    } else {
                        // READING ERROR
                        System.out.printf("Error in line (Unknown command):\n\" %s \"\n", line);
                        System.out.println("Terminating the simulation...");
                        DrawEnvironment.showAlert(String.format("Error in line (Unknown command):\n\" %s \"\n", line),"Terminating the simulation...");
                        //System.exit(0);
                    }
                }
            }
        }
        return true;
    }
}