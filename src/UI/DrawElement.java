package UI;

import Kernel.Element;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class DrawElement {
    public static int s = 80;
    public static int z = 4;
    static Color secondary = Color.DODGERBLUE;
    static Color textColor = Color.RED;

    public static void draw(Element elem, Pane pane, Dot pDot, Dot nDot, Paint first, int index) {
        if (elem != null) {
            switch (elem.type) {
                case "CAP":
                    drawDirectional("CAP", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "RES":
                    drawDirectional("RES", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "IND":
                    drawDirectional("IND", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "DIODE":
                    drawDirectional("DIODE", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "VOLSRC":
                    drawDirectional("VOLSRC", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "CURSRC":
                    drawDirectional("CURSRC", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "CCS":
                    drawDirectional("CCS", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "CVS":
                    drawDirectional("CVS", pane, pDot, nDot, first, index, elem.name);
                    break;
                case "WIRE":
                    drawWire(pane, pDot, nDot, first);
                    break;
                default:
                    break;
            }
        }
    }

    public static void drawDirectional(String type, Pane pane, Dot pDot, Dot nDot, Paint first, int index, String name) {
        Line l1, l2;
        Dot dot1 = null, dot2 = null;
        if (pDot.y == nDot.y && Math.abs(pDot.x - nDot.x) == s) {
            if (index == 0) {
                if (pDot.x < nDot.x) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y, 0);
                    dot2 = new Dot(pDot.x + 3 * s / 4, pDot.y, 0);
                } else {
                    dot2 = new Dot(nDot.x + s / 4, pDot.y, 0);
                    dot1 = new Dot(nDot.x + 3 * s / 4, pDot.y, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 1) {
                if (pDot.x < nDot.x) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y - s / 4, 0);
                    dot2 = new Dot(pDot.x + 3 * s / 4, pDot.y - s / 4, 0);
                } else {
                    dot2 = new Dot(nDot.x + s / 4, pDot.y - s / 4, 0);
                    dot1 = new Dot(nDot.x + 3 * s / 4, pDot.y - s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 2) {
                if (pDot.x < nDot.x) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x + 3 * s / 4, pDot.y + s / 4, 0);
                } else {
                    dot2 = new Dot(nDot.x + s / 4, pDot.y + s / 4, 0);
                    dot1 = new Dot(nDot.x + 3 * s / 4, pDot.y + s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 3) {
                if (pDot.x < nDot.x) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y - s / 2, 0);
                    dot2 = new Dot(pDot.x + 3 * s / 4, pDot.y - s / 2, 0);
                } else {
                    dot2 = new Dot(nDot.x + s / 4, pDot.y - s / 2, 0);
                    dot1 = new Dot(nDot.x + 3 * s / 4, pDot.y - s / 2, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 4) {
                if (pDot.x < nDot.x) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y + s / 2, 0);
                    dot2 = new Dot(pDot.x + 3 * s / 4, pDot.y + s / 2, 0);
                } else {
                    dot2 = new Dot(nDot.x + s / 4, pDot.y + s / 2, 0);
                    dot1 = new Dot(nDot.x + 3 * s / 4, pDot.y + s / 2, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            }
        } else if (pDot.x == nDot.x && Math.abs(pDot.y - nDot.y) == s) {
            if (index == 0) {
                if (pDot.y < nDot.y) {
                    dot1 = new Dot(pDot.x, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x, pDot.y + 3 * s / 4, 0);
                } else {
                    dot2 = new Dot(pDot.x, nDot.y + s / 4, 0);
                    dot1 = new Dot(pDot.x, nDot.y + 3 * s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 1) {
                if (pDot.y < nDot.y) {
                    dot1 = new Dot(pDot.x - s / 4, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x - s / 4, pDot.y + 3 * s / 4, 0);
                } else {
                    dot2 = new Dot(pDot.x - s / 4, nDot.y + s / 4, 0);
                    dot1 = new Dot(pDot.x - s / 4, nDot.y + 3 * s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 2) {
                if (pDot.y < nDot.y) {
                    dot1 = new Dot(pDot.x + s / 4, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x + s / 4, pDot.y + 3 * s / 4, 0);
                } else {
                    dot2 = new Dot(pDot.x + s / 4, nDot.y + s / 4, 0);
                    dot1 = new Dot(pDot.x + s / 4, nDot.y + 3 * s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 3) {
                if (pDot.y < nDot.y) {
                    dot1 = new Dot(pDot.x - s / 2, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x - s / 2, pDot.y + 3 * s / 4, 0);
                } else {
                    dot2 = new Dot(pDot.x - s / 2, nDot.y + s / 4, 0);
                    dot1 = new Dot(pDot.x - s / 2, nDot.y + 3 * s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            } else if (index == 3) {
                if (pDot.y < nDot.y) {
                    dot1 = new Dot(pDot.x + s / 2, pDot.y + s / 4, 0);
                    dot2 = new Dot(pDot.x + s / 2, pDot.y + 3 * s / 4, 0);
                } else {
                    dot2 = new Dot(pDot.x + s / 2, nDot.y + s / 4, 0);
                    dot1 = new Dot(pDot.x + s / 2, nDot.y + 3 * s / 4, 0);
                }
                l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
                l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
                switch (type) {
                    case "DIODE":
                        pvtDiode(pane, dot1, dot2, first);
                        break;
                    case "CAP":
                        pvtCap(pane, dot1, dot2, first);
                        break;
                    case "RES":
                        pvtRes(pane, dot1, dot2, first);
                        break;
                    case "IND":
                        pvtInd(pane, dot1, dot2, first);
                        break;
                    case "VOLSRC":
                        pvtVolSrc(pane, dot1, dot2, first);
                        break;
                    case "CURSRC":
                        pvtCurSrc(pane, dot1, dot2, first);
                        break;
                    case "CCS":
                        pvtCCS(pane, dot1, dot2, first);
                        break;
                    case "CVS":
                        pvtCVS(pane, dot1, dot2, first);
                        break;
                }
                pane.getChildren().addAll(l1, l2);
            }
        } else if (pDot.x > nDot.x && pDot.y > nDot.y) {
            dot2 = new Dot(nDot.x + s / 2, nDot.y + s / 4, 0);
            dot1 = new Dot(nDot.x + s / 2, nDot.y + 3 * s / 4, 0);
            l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
            l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
            l1.setStroke(secondary);
            l2.setStroke(secondary);
            switch (type) {
                case "DIODE":
                    pvtDiode(pane, dot1, dot2, secondary);
                    break;
                case "CAP":
                    pvtCap(pane, dot1, dot2, secondary);
                    break;
                case "RES":
                    pvtRes(pane, dot1, dot2, secondary);
                    break;
                case "IND":
                    pvtInd(pane, dot1, dot2, secondary);
                    break;
                case "VOLSRC":
                    pvtVolSrc(pane, dot1, dot2, secondary);
                    break;
                case "CURSRC":
                    pvtCurSrc(pane, dot1, dot2, secondary);
                    break;
                case "CCS":
                    pvtCCS(pane, dot1, dot2, secondary);
                    break;
                case "CVS":
                    pvtCVS(pane, dot1, dot2, secondary);
                    break;
            }
            pane.getChildren().addAll(l1, l2);
        } else if (nDot.x > pDot.x && nDot.y > pDot.y) {
            dot1 = new Dot(pDot.x + s / 2, pDot.y + s / 4, 0);
            dot2 = new Dot(pDot.x + s / 2, pDot.y + 3 * s / 4, 0);
            l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
            l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
            l1.setStroke(secondary);
            l2.setStroke(secondary);
            switch (type) {
                case "DIODE":
                    pvtDiode(pane, dot1, dot2, secondary);
                    break;
                case "CAP":
                    pvtCap(pane, dot1, dot2, secondary);
                    break;
                case "RES":
                    pvtRes(pane, dot1, dot2, secondary);
                    break;
                case "IND":
                    pvtInd(pane, dot1, dot2, secondary);
                    break;
                case "VOLSRC":
                    pvtVolSrc(pane, dot1, dot2, secondary);
                    break;
                case "CURSRC":
                    pvtCurSrc(pane, dot1, dot2, secondary);
                    break;
                case "CCS":
                    pvtCCS(pane, dot1, dot2, secondary);
                    break;
                case "CVS":
                    pvtCVS(pane, dot1, dot2, secondary);
                    break;
            }
            pane.getChildren().addAll(l1, l2);
        } else if (nDot.x > pDot.x && nDot.y < pDot.y) {
            dot1 = new Dot(nDot.x - s / 2, nDot.y + 3 * s / 4, 0);
            dot2 = new Dot(nDot.x - s / 2, nDot.y + s / 4, 0);
            l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
            l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
            l1.setStroke(secondary);
            l2.setStroke(secondary);
            switch (type) {
                case "DIODE":
                    pvtDiode(pane, dot1, dot2, secondary);
                    break;
                case "CAP":
                    pvtCap(pane, dot1, dot2, secondary);
                    break;
                case "RES":
                    pvtRes(pane, dot1, dot2, secondary);
                    break;
                case "IND":
                    pvtInd(pane, dot1, dot2, secondary);
                    break;
                case "VOLSRC":
                    pvtVolSrc(pane, dot1, dot2, secondary);
                    break;
                case "CURSRC":
                    pvtCurSrc(pane, dot1, dot2, secondary);
                    break;
                case "CCS":
                    pvtCCS(pane, dot1, dot2, secondary);
                    break;
                case "CVS":
                    pvtCVS(pane, dot1, dot2, secondary);
                    break;
            }
            pane.getChildren().addAll(l1, l2);
        } else {
            dot1 = new Dot(pDot.x - s / 2, pDot.y + s / 4, 0);
            dot2 = new Dot(pDot.x - s / 2, pDot.y + 3 * s / 4, 0);
            l1 = new Line(nDot.x, nDot.y, dot2.x, dot2.y);
            l2 = new Line(pDot.x, pDot.y, dot1.x, dot1.y);
            l1.setStroke(secondary);
            l2.setStroke(secondary);
            switch (type) {
                case "DIODE":
                    pvtDiode(pane, dot1, dot2, secondary);
                    break;
                case "CAP":
                    pvtCap(pane, dot1, dot2, secondary);
                    break;
                case "RES":
                    pvtRes(pane, dot1, dot2, secondary);
                    break;
                case "IND":
                    pvtInd(pane, dot1, dot2, secondary);
                    break;
                case "VOLSRC":
                    pvtVolSrc(pane, dot1, dot2, secondary);
                    break;
                case "CURSRC":
                    pvtCurSrc(pane, dot1, dot2, secondary);
                    break;
                case "CCS":
                    pvtCCS(pane, dot1, dot2, secondary);
                    break;
                case "CVS":
                    pvtCVS(pane, dot1, dot2, secondary);
                    break;
            }
            pane.getChildren().addAll(l1, l2);
        }
        Label label = new Label(name.toUpperCase());
        label.setTextFill(textColor);
        label.setFont(new Font(12));
        label.setLayoutY((dot2.y + dot1.y) / 2 + 6);
        if (type.equals("DIODE")) {
            label.setLayoutX((dot1.x + dot2.x) / 2 - 6);
        } else {
            label.setLayoutX((dot1.x + dot2.x) / 2 + 6);
        }
        pane.getChildren().add(label);
    }

    public static void pvtCap(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z / 2, y);
            l2 = new Line(x + s / 4 - z / 2, y - z, x + s / 4 - z / 2, y + z);
            l3 = new Line(x + s / 4 + z / 2, y - z, x + s / 4 + z / 2, y + z);
            l4 = new Line(x + s / 4 + z / 2, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z / 2);
            l2 = new Line(x - z, y + s / 4 - z / 2, x + z, y + s / 4 - z / 2);
            l3 = new Line(x - z, y + s / 4 + z / 2, x + 4, y + s / 4 + z / 2);
            l4 = new Line(x, y + s / 4 + z / 2, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4);

    }

    public static void pvtRes(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5, l6, l7, l8, l9;
        if (pDot.x == nDot.x) {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - 3 * z);
            l2 = new Line(x, y + s / 4 - 3 * z, x - z, y + s / 4 - 2.5 * z);
            l3 = new Line(x - z, y + s / 4 - 2.5 * z, x + z, y + s / 4 - 1.5 * z);
            l4 = new Line(x + z, y + s / 4 - 1.5 * z, x - z, y + s / 4 - 0.5 * z);
            l5 = new Line(x - z, y + s / 4 - 0.5 * z, x + z, y + s / 4 + 0.5 * z);
            l6 = new Line(x + z, y + s / 4 + 0.5 * z, x - z, y + s / 4 + 1.5 * z);
            l7 = new Line(x - z, y + s / 4 + 1.5 * z, x + z, y + s / 4 + 2.5 * z);
            l8 = new Line(x + z, y + s / 4 + 2.5 * z, x, y + s / 4 + 3 * z);
            l9 = new Line(x, y + s / 4 + 3 * z, x, y + s / 2);
        } else {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - 3 * z, y);
            l2 = new Line(x + s / 4 - 3 * z, y, x + s / 4 - 2.5 * z, y - z);
            l3 = new Line(x + s / 4 - 2.5 * z, y - z, x + s / 4 - 1.5 * z, y + z);
            l4 = new Line(x + s / 4 - 1.5 * z, y + z, x + s / 4 - 0.5 * z, y - z);
            l5 = new Line(x + s / 4 - 0.5 * z, y - z, x + s / 4 + 0.5 * z, y + z);
            l6 = new Line(x + s / 4 + 0.5 * z, y + z, x + s / 4 + 1.5 * z, y - z);
            l7 = new Line(x + s / 4 + 1.5 * z, y - z, x + s / 4 + 2.5 * z, y + z);
            l8 = new Line(x + s / 4 + 2.5 * z, y + z, x + s / 4 + 3 * z, y);
            l9 = new Line(x + s / 4 + 3 * z, y, x + s / 2, y);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        l6.setStroke(color);
        l7.setStroke(color);
        l8.setStroke(color);
        l9.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, l8, l9);
    }

    private static void pvtInd(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2;
        Arc a1, a2, a3;
        if (pDot.x == nDot.x) {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - 3 * z);
            a1 = new Arc(x, y + s / 4 - 2 * z, z, z, 90, 180);
            a2 = new Arc(x, y + s / 4, z, z, 90, 180);
            a3 = new Arc(x, y + s / 4 + 2 * z, z, z, 90, 180);
            l2 = new Line(x, y + s / 4 + 3 * z, x, y + s / 2);
        } else {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - 3 * z, y);
            a1 = new Arc(x + s / 4 - 2 * z, y, z, z, 0, 180);
            a2 = new Arc(x + s / 4, y, z, z, 0, 180);
            a3 = new Arc(x + s / 4 + 2 * z, y, z, z, 0, 180);
            l2 = new Line(x + s / 4 + 3 * z, y, x + s / 2, y);
        }
        a1.setFill(Color.TRANSPARENT);
        a2.setFill(Color.TRANSPARENT);
        a3.setFill(Color.TRANSPARENT);
        a1.setStroke(color);
        a2.setStroke(color);
        a3.setStroke(color);
        l1.setStroke(color);
        l2.setStroke(color);
        pane.getChildren().addAll(l1, l2, a1, a2, a3);
    }

    private static void pvtDiode(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5, l6;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z / 2, y);
            l2 = new Line(x + s / 4 - z / 2, y - z, x + s / 4 - z / 2, y + z);
            if (pDot.x < nDot.x) {
                l3 = new Line(x + s / 4 - z / 2, y - z, x + s / 4 + z / 2, y);
                l4 = new Line(x + s / 4 - z / 2, y + z, x + s / 4 + z / 2, y);
            } else {
                l3 = new Line(x + s / 4 - z / 2, y, x + s / 4 + z / 2, y - z);
                l4 = new Line(x + s / 4 - z / 2, y, x + s / 4 + z / 2, y + z);
            }
            l5 = new Line(x + s / 4 + z / 2, y - z, x + s / 4 + z / 2, y + z);
            l6 = new Line(x + s / 4 + z / 2, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z / 2);
            l2 = new Line(x - z, y + s / 4 - z / 2, x + z, y + s / 4 - z / 2);
            if (pDot.y < nDot.y) {
                l3 = new Line(x - z, y + s / 4 - z / 2, x, y + s / 4 + z / 2);
                l4 = new Line(x + z, y + s / 4 - z / 2, x, y + s / 4 + z / 2);
            } else {
                l3 = new Line(x - z, y + s / 4 + z / 2, x, y + s / 4 - z / 2);
                l4 = new Line(x + z, y + s / 4 + z / 2, x, y + s / 4 - z / 2);
            }
            l5 = new Line(x - z, y + s / 4 + z / 2, x + 4, y + s / 4 + z / 2);
            l6 = new Line(x, y + s / 4 + z / 2, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        l6.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, l6);
    }

    private static void pvtVolSrc(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5;
        Arc a1;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z * 2, y);
            a1 = new Arc(x + s / 4, y, z * 2, z * 2, 0, 360);
            l2 = new Line(x + s / 4 - z, y - 0.5 * z, x + s / 4 - z, y + 0.5 * z);
            l3 = new Line(x + s / 4 + z, y - 0.5 * z, x + s / 4 + z, y + 0.5 * z);
            if (pDot.x < nDot.x) {
                l4 = new Line(x + s / 4 - .5 * z, y, x + s / 4 - 1.5 * z, y);
            } else {
                l4 = new Line(x + s / 4 + .5 * z, y, x + s / 4 + 1.5 * z, y);
            }
            l5 = new Line(x + s / 4 + 2 * z, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z * 2);
            a1 = new Arc(x, y + s / 4, z * 2, z * 2, 0, 360);
            l2 = new Line(x - .5 * z, y + s / 4 - z, x + .5 * z, y + s / 4 - z);
            l3 = new Line(x - .5 * z, y + s / 4 + z, x + .5 * z, y + s / 4 + z);
            if (pDot.y < nDot.y) {
                l4 = new Line(x, y + s / 4 - .5 * z, x, y + s / 4 - 1.5 * z);
            } else {
                l4 = new Line(x, y + s / 4 + .5 * z, x, y + s / 4 + 1.5 * z);
            }
            l5 = new Line(x, y + s / 4 + 2 * z, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        a1.setFill(Color.TRANSPARENT);
        a1.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, a1);
    }

    private static void pvtCurSrc(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5;
        Arc a1;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z * 2, y);
            a1 = new Arc(x + s / 4, y, z * 2, z * 2, 0, 360);
            l2 = new Line(x + s / 4 - z, y, x + s / 4 + z, y);
            if (pDot.x < nDot.x) {
                l3 = new Line(x + s / 4 + z, y, x + s / 4, y - 0.5 * z);
                l4 = new Line(x + s / 4 + z, y, x + s / 4, y + 0.5 * z);
            } else {
                l3 = new Line(x + s / 4 - z, y, x + s / 4, y - 0.5 * z);
                l4 = new Line(x + s / 4 - z, y, x + s / 4, y + 0.5 * z);
            }
            l5 = new Line(x + s / 4 + 2 * z, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z * 2);
            a1 = new Arc(x, y + s / 4, z * 2, z * 2, 0, 360);
            l2 = new Line(x, y + s / 4 - z, x, y + s / 4 + z);
            if (pDot.y < nDot.y) {
                l3 = new Line(x, y + s / 4 + z, x - .5 * z, y + s / 4);
                l4 = new Line(x, y + s / 4 + z, x + .5 * z, y + s / 4);
            } else {
                l3 = new Line(x, y + s / 4 - z, x - .5 * z, y + s / 4);
                l4 = new Line(x, y + s / 4 - z, x + .5 * z, y + s / 4);
            }
            l5 = new Line(x, y + s / 4 + 2 * z, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        a1.setFill(Color.TRANSPARENT);
        a1.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, a1);
    }

    private static void pvtCCS(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5, l6, l7, l8, l9;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z * 2, y);
            l6 = new Line(x + s / 4 - z * 2, y, x + s / 4, y - z * 2);
            l7 = new Line(x + s / 4 - z * 2, y, x + s / 4, y + z * 2);
            l8 = new Line(x + s / 4 + z * 2, y, x + s / 4, y - z * 2);
            l9 = new Line(x + s / 4 + z * 2, y, x + s / 4, y + z * 2);
            l2 = new Line(x + s / 4 - z, y, x + s / 4 + z, y);
            if (pDot.x < nDot.x) {
                l3 = new Line(x + s / 4 + z, y, x + s / 4, y - 0.5 * z);
                l4 = new Line(x + s / 4 + z, y, x + s / 4, y + 0.5 * z);
            } else {
                l3 = new Line(x + s / 4 - z, y, x + s / 4, y - 0.5 * z);
                l4 = new Line(x + s / 4 - z, y, x + s / 4, y + 0.5 * z);
            }
            l5 = new Line(x + s / 4 + 2 * z, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z * 2);
            l6 = new Line(x, y + s / 4 - z * 2, x + 2 * z, y + s / 4);
            l7 = new Line(x, y + s / 4 - z * 2, x - 2 * z, y + s / 4);
            l8 = new Line(x, y + s / 4 + z * 2, x + 2 * z, y + s / 4);
            l9 = new Line(x, y + s / 4 + z * 2, x - 2 * z, y + s / 4);
            l2 = new Line(x, y + s / 4 - z, x, y + s / 4 + z);
            if (pDot.y < nDot.y) {
                l3 = new Line(x, y + s / 4 + z, x - .5 * z, y + s / 4);
                l4 = new Line(x, y + s / 4 + z, x + .5 * z, y + s / 4);
            } else {
                l3 = new Line(x, y + s / 4 - z, x - .5 * z, y + s / 4);
                l4 = new Line(x, y + s / 4 - z, x + .5 * z, y + s / 4);
            }
            l5 = new Line(x, y + s / 4 + 2 * z, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        l6.setStroke(color);
        l7.setStroke(color);
        l8.setStroke(color);
        l9.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, l8, l9);
    }

    private static void pvtCVS(Pane pane, Dot pDot, Dot nDot, Paint color) {
        Line l1, l2, l3, l4, l5, l6, l7, l8, l9;
        if (pDot.x != nDot.x) {
            int x = Math.min(pDot.x, nDot.x);
            int y = pDot.y;
            l1 = new Line(x, y, x + s / 4 - z * 2, y);
            l6 = new Line(x + s / 4 - z * 2, y, x + s / 4, y - z * 2);
            l7 = new Line(x + s / 4 - z * 2, y, x + s / 4, y + z * 2);
            l8 = new Line(x + s / 4 + z * 2, y, x + s / 4, y - z * 2);
            l9 = new Line(x + s / 4 + z * 2, y, x + s / 4, y + z * 2);
            l2 = new Line(x + s / 4 - z, y - 0.5 * z, x + s / 4 - z, y + 0.5 * z);
            l3 = new Line(x + s / 4 + z, y - 0.5 * z, x + s / 4 + z, y + 0.5 * z);
            if (pDot.x < nDot.x) {
                l4 = new Line(x + s / 4 - .5 * z, y, x + s / 4 - 1.5 * z, y);
            } else {
                l4 = new Line(x + s / 4 + .5 * z, y, x + s / 4 + 1.5 * z, y);
            }
            l5 = new Line(x + s / 4 + 2 * z, y, x + s / 2, y);
        } else {
            int x = pDot.x;
            int y = Math.min(pDot.y, nDot.y);
            l1 = new Line(x, y, x, y + s / 4 - z * 2);
            l6 = new Line(x, y + s / 4 - z * 2, x + 2 * z, y + s / 4);
            l7 = new Line(x, y + s / 4 - z * 2, x - 2 * z, y + s / 4);
            l8 = new Line(x, y + s / 4 + z * 2, x + 2 * z, y + s / 4);
            l9 = new Line(x, y + s / 4 + z * 2, x - 2 * z, y + s / 4);
            l2 = new Line(x - .5 * z, y + s / 4 - z, x + .5 * z, y + s / 4 - z);
            l3 = new Line(x - .5 * z, y + s / 4 + z, x + .5 * z, y + s / 4 + z);
            if (pDot.y < nDot.y) {
                l4 = new Line(x, y + s / 4 - .5 * z, x, y + s / 4 - 1.5 * z);
            } else {
                l4 = new Line(x, y + s / 4 + .5 * z, x, y + s / 4 + 1.5 * z);
            }
            l5 = new Line(x, y + s / 4 + 2 * z, x, y + s / 2);
        }
        l1.setStroke(color);
        l2.setStroke(color);
        l3.setStroke(color);
        l4.setStroke(color);
        l5.setStroke(color);
        l6.setStroke(color);
        l7.setStroke(color);
        l8.setStroke(color);
        l9.setStroke(color);
        pane.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, l8, l9);
    }

    public static void drawWire(Pane pane, Dot pDot, Dot nDot, Paint first) {
        Line l;
        l = new Line(pDot.x, pDot.y, nDot.x, nDot.y);
        l.setStroke(first);
        pane.getChildren().add(l);
    }
}
