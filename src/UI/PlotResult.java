package UI;

import Kernel.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PlotResult {

    static int checkPointNum = 150;
    //@Override
    //public void start(Stage stage) {
    public static void plotResult (Stage stage){
        //Defining the x an y axes
        NumberAxis timeAxis = new NumberAxis();
        NumberAxis timeAxis2 = new NumberAxis();
        NumberAxis timeAxis3 = new NumberAxis();
        NumberAxis timeAxis4 = new NumberAxis();
        NumberAxis VIP_Axis = new NumberAxis();
        NumberAxis V_Axis = new NumberAxis();
        NumberAxis I_Axis = new NumberAxis();
        NumberAxis P_Axis = new NumberAxis();

        //Setting labels for the axes
        timeAxis.setLabel("Time");
        timeAxis2.setLabel("Time");
        timeAxis3.setLabel("Time");
        timeAxis4.setLabel("Time");
        VIP_Axis.setLabel("Value");
        V_Axis.setLabel("Voltage");
        I_Axis.setLabel("Current");
        P_Axis.setLabel("Power");

        //Creating line charts
        LineChart<Number, Number> vipChart = new LineChart<Number, Number>(timeAxis, VIP_Axis);
        LineChart<Number, Number> voltageChart = new LineChart<Number, Number>(timeAxis2, V_Axis);
        LineChart<Number, Number> currentChart = new LineChart<Number, Number>(timeAxis3, I_Axis);
        LineChart<Number, Number> powerChart = new LineChart<Number, Number>(timeAxis4, P_Axis);


        XYChart.Series voltageSeries = new XYChart.Series();
        XYChart.Series currentSeries = new XYChart.Series();
        XYChart.Series powerSeries = new XYChart.Series();
        XYChart.Series V_Series = new XYChart.Series();
        XYChart.Series I_Series = new XYChart.Series();
        XYChart.Series P_Series = new XYChart.Series();

        double timeDomain = CirSim.timeDomain;

        Slider slider = new Slider();
        slider.setMax(timeDomain);
        slider.setMin(0.0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setValueChanging(true);
        slider.setPrefSize(150,25);
        slider.setLayoutX(470);
        slider.setLayoutY(10);
        slider.setValue(timeDomain);

        Font buttonFont = Font.font("Verdana", FontWeight.EXTRA_BOLD, 11);
        Font font = Font.font("Verdana", FontWeight.BLACK, 11);
        DropShadow shadow = new DropShadow();

        Slider pointNumSlider = new Slider();
        pointNumSlider.setMax(300);
        pointNumSlider.setMin(50);
        pointNumSlider.setValueChanging(true);
        pointNumSlider.setShowTickLabels(true);
        pointNumSlider.setShowTickMarks(true);
        pointNumSlider.setPrefSize(150,25);
        pointNumSlider.setLayoutX(800);
        pointNumSlider.setLayoutY(10);
        pointNumSlider.setValue(150);

        Label label2 = new Label("Check points number:");
        label2.setLayoutX(660);
        label2.setLayoutY(5);
        label2.setFont(font);
        label2.setPrefSize(180,25);

        Button printButton =new Button();
        printButton.setPrefSize(200,25);
        printButton.setWrapText(true);
        printButton.setFont(buttonFont);
        printButton.setEffect(shadow);
        printButton.setText("Print simulation result");
        printButton.setLayoutX(130);
        printButton.setLayoutY(10);

        Label label = new Label("time domain:");
        label.setLayoutX(380);
        label.setLayoutY(5);
        label.setFont(font);
        label.setPrefSize(100,25);


        printButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                voltageSeries.getData().clear();
                currentSeries.getData().clear();
                powerSeries.getData().clear();
                V_Series.getData().clear();
                I_Series.getData().clear();
                P_Series.getData().clear();
                //vipChart.getData().clear();
                //voltageChart.getData().clear();
                //currentChart.getData().clear();
                //powerChart.getData().clear();

                //Preparing the data points for the lines:
                checkPointNum = (int) (pointNumSlider.getValue());

                double r = slider.getValue()/timeDomain;
                Element element = DrawEnvironment.element;
                double time=0;
                double Dt = CirSim.Dt;
                int step = (int) ((element.voltageList.size()-1)*r/checkPointNum);

                for (int i=1 ; i< (int)(element.voltageList.size()*r)-step-1 ; i+=step ) {
                    voltageSeries.getData().add(new XYChart.Data(time, element.voltageList.get(i)));
                    V_Series.getData().add(new XYChart.Data(time, element.voltageList.get(i)));
                    time+=Dt*step;
                }

                time=0;
                step = (int) ((element.currentList.size()-1)*r/checkPointNum);

                for (int i = 1; i <= (int)(element.currentList.size()*r)-step-1 ; i+=step) {
                    currentSeries.getData().add(new XYChart.Data(time, element.currentList.get(i)));
                    powerSeries.getData().add(new XYChart.Data(
                            time,(element.currentList.get(i)*element.voltageList.get(i))));
                    I_Series.getData().add(new XYChart.Data(time, element.currentList.get(i)));
                    P_Series.getData().add(new XYChart.Data(
                            time,(element.currentList.get(i)*element.voltageList.get(i))));
                    time+=Dt*step;
                }

                // just for test!!
                /*ArrayList<Double> testList = new ArrayList<Double>();
                for (int i = 0; i < 10; i++)
                    testList.add(-1*i/10.0+0.5);

                double time=0;
                double Dt= 0.1;
                for (Double aDouble : testList) {
                    voltageSeries.getData().add(new XYChart.Data(String.format("%.2f", time), aDouble));
                    currentSeries.getData().add(new XYChart.Data(String.format("%.2f", time), 1-aDouble));
                    powerSeries.getData().add(
                            new XYChart.Data(String.format("%.2f", time), 0.2));
                    V_Series.getData().add(new XYChart.Data(String.format("%.2f", time), aDouble));
                    I_Series.getData().add(new XYChart.Data(String.format("%.2f", time), 1-aDouble));
                    P_Series.getData().add(
                            new XYChart.Data(String.format("%.2f", time), 0.2));
                    time+=Dt;
                }*/

                //Setting the name to the line (series)
                voltageSeries.setName("Voltage");
                currentSeries.setName("Current");
                powerSeries.setName("Power");
                V_Series.setName("Voltage");
                I_Series.setName("Current");
                P_Series.setName("Power");

                //Setting the data to Line chart
                vipChart.getData().addAll(V_Series,I_Series,P_Series);
                voltageChart.getData().addAll(voltageSeries);
                currentChart.getData().addAll(currentSeries);
                powerChart.getData().addAll(powerSeries);

                vipChart.autosize();
                voltageChart.autosize();
                currentChart.autosize();
                powerChart.autosize();
            }
        } );

        //Creating a stack pane to hold the chart
        StackPane VIP_pane = new StackPane(vipChart);
        StackPane V_pane = new StackPane(voltageChart);
        StackPane I_pane = new StackPane(currentChart);
        StackPane P_pane = new StackPane(powerChart);

        //set Locations and size:
        VIP_pane.setPrefSize(500,380);
        V_pane.setPrefSize(500,380);
        I_pane.setPrefSize(500,380);
        P_pane.setPrefSize(500,380);

        VIP_pane.setLayoutY(50);
        VIP_pane.setLayoutX(0);
        V_pane.setLayoutY(50);
        V_pane.setLayoutX(500);
        I_pane.setLayoutY(430);
        I_pane.setLayoutX(0);
        P_pane.setLayoutY(430);
        P_pane.setLayoutX(500);

        //pane.setPadding(new Insets(15, 15, 15, 15));
        VIP_pane.setStyle("-fx-background-color: BEIGE");
        V_pane.setStyle("-fx-background-color: azure");
        I_pane.setStyle("-fx-background-color: azure");
        P_pane.setStyle("-fx-background-color: BEIGE");

        Button backButton =new Button();
        backButton.setPrefSize(50,25);
        backButton.setWrapText(true);
        backButton.setFont(buttonFont);
        backButton.setEffect(shadow);
        backButton.setText("Back");
        backButton.setLayoutX(25);
        backButton.setLayoutY(10);

        //Setting the Scene
        Group root = new Group();
        ObservableList list = root.getChildren();
        list.addAll(V_pane,I_pane,P_pane,VIP_pane,backButton,slider,printButton,label,pointNumSlider,label2);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Simulation Result");
        stage.setScene(scene);
        stage.show();

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                root.setVisible(false);
                stage.setScene(DrawEnvironment.scene);
                DrawEnvironment.rootPane.setVisible(true);
            }
        } );
    }

    //public static void plot(String args[]){
    //    launch(args);
    //}
}