package UI;


import Kernel.Circuit;
import Kernel.Element;
import Kernel.IdealDiode;
import Kernel.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PlotCircuit {
    static int s = 80;
    static int z = 4;

    public static void plot(Pane pane) {
        if (Circuit.diodeList.size() > 0) {
            for (IdealDiode diode : Circuit.diodeList) {
                Circuit.elementList.add(diode);
            }
        }

        Dot[][] dot = new Dot[6][6];
        int[] groundTaken = new int[6];
        int[] gnd = new int[6];
        pane.getChildren().clear();
        // Plotting ground
        for (int i = 0; i < 6; i++) {
            dot[0][i] = new Dot(50 + i * s, 50 + 5 * s, 0);
        }
        Label label;
        // Plotting non-ground dots in the circuit
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                dot[i][j] = new Dot(50 + j * s, 50 + 5 * s - s * i, 6 * i + j - 5);
                for (Node node : Circuit.nodeList) {
                    if (Integer.parseInt(node.name) == 6 * i + j - 5) {
                        Arc a = new Arc(dot[i][j].x, dot[i][j].y, 3, 3, 0, 360);
                        label = new Label(Integer.toString(dot[i][j].num));
                        label.setTextFill(Color.RED);
                        label.setFont(new Font(12));
                        label.setLayoutX(dot[i][j].x + 5);
                        label.setLayoutY(dot[i][j].y);
                        pane.getChildren().addAll(label, a);
                    }
                }

            }
        }
        ArrayList<Element> elemList = new ArrayList<>();
        Dot pDot, nDot;
        Color color = Color.BLACK;
        for (int i = 1; i <= 30; i++) {
            elemList = new ArrayList<>();
            for (Element elem : Circuit.elementList) {
                if ((Integer.parseInt(elem.negativeNode.name) == i && Integer.parseInt(elem.positiveNode.name) == 0) ||
                        (Integer.parseInt(elem.positiveNode.name) == i && Integer.parseInt(elem.negativeNode.name) == 0)) {
                    elemList.add(elem);
                }
            }
            if (elemList.size() > 5) {
                System.out.println("Handling more than five parallel elements makes the representation inconvenient");
                System.out.println("Returning to the parent class...");
                return;
            } else {
                for (int j = 0; j < elemList.size(); j++) {
                    if (elemList.get(j).negativeNode.name.equals("0")) {
                        int pNum = Integer.parseInt(elemList.get(j).positiveNode.name);
                        nDot = dot[0][(pNum - 1) % 6];
                        pDot = dot[pNum / 6 + 1][(pNum - 1) % 6];
                        gnd[(pNum - 1) % 6]++;
                        if (pNum > 6) {
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, 0);
                        } else {
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, groundTaken[pNum - 1]);
                            groundTaken[pNum - 1]++;
                        }
                    } else if (elemList.get(j).positiveNode.name.equals("0")) {
                        int nNum = Integer.parseInt(elemList.get(j).negativeNode.name);
                        pDot = dot[0][(nNum - 1) % 6];
                        nDot = dot[nNum / 6 + 1][(nNum - 1) % 6];
                        gnd[(nNum - 1) % 6]++;
                        if (nNum > 6) {
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, 0);
                        } else {
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, groundTaken[nNum - 1]);
                            groundTaken[nNum - 1]++;
                        }
                    }
                }
            }
        }
        boolean groundLabel = false;
        Font font = new Font(12);
        Text text = new Text("Ground");
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: black;");
        for (int i = 0; i < 6 && !groundLabel; i++) {
            if (gnd[i] != 0) {
                groundLabel = true;
                text.setLayoutX(dot[0][i].x);
                text.setLayoutY(dot[0][i].y + 15);
                Arc a = new Arc(dot[0][i].x, dot[0][i].y, 3, 3, 0, 360);
                pane.getChildren().addAll(text, a);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gnd[i] != 0 && gnd[j] != 0) {
                    DrawElement.drawWire(pane, dot[0][i], dot[0][j], color);
                }
            }
        }
        for (int i = 1; i <= 30; i++) {
            for (int k = i + 1; k <= 30; k++) {
                elemList = new ArrayList<>();
                for (Element elem : Circuit.elementList) {
                    if ((Integer.parseInt(elem.negativeNode.name) == i && Integer.parseInt(elem.positiveNode.name) == k) ||
                            (Integer.parseInt(elem.positiveNode.name) == i && Integer.parseInt(elem.negativeNode.name) == k)) {
                        elemList.add(elem);
                    }
                }
                if (elemList.size() > 3) {
                    System.out.println("Handling more than three parallel elements makes the representation inconvenient");
                    System.out.println("Returning to the parent class...");
                    return;
                } else {
                    for (int j = 0; j < elemList.size(); j++) {
                        if (Integer.parseInt(elemList.get(j).negativeNode.name) == i) {
                            nDot = dot[i / 6 + 1][(i - 1) % 6];
                            pDot = dot[k / 6 + 1][(k - 1) % 6];
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, j);
                        } else if (Integer.parseInt(elemList.get(j).positiveNode.name) == i) {
                            pDot = dot[i / 6 + 1][(i - 1) % 6];
                            nDot = dot[k / 6 + 1][(k - 1) % 6];
                            DrawElement.draw(elemList.get(j), pane, pDot, nDot, color, j);
                        }
                    }
                }
            }
        }
    }
}
