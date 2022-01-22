package UI;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Preview extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        DropShadow shadow = new DropShadow();
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20);
        Button startButton = new Button();
        startButton.setText("Launch JSpice!");
        startButton.setFont(font);
        startButton.setPrefSize(200, 65);
        startButton.setWrapText(true);
        startButton.setEffect(shadow);
        startButton.setLayoutX(400);
        startButton.setLayoutY(650);

        Image image = null;
        try {
            java.net.URL url = getClass().getResource("LaunchIcon.gif");
            image = new Image(new FileInputStream(url.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //try {
        //    java.net.URL url2 = getClass().getResource("JSpice.jpg");
        //    stage.getIcons().add(new Image(getClass().getResourceAsStream(url2.getPath())));
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(800);
        imageView.setFitWidth(1000);
        imageView.setPreserveRatio(true);

        Group root = new Group();
        ObservableList list = root.getChildren();
        list.addAll(imageView, startButton);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Loading JSpice...");
        stage.setScene(scene);
        stage.show();

        startButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent arg0) {
                                        //stage.close();
                                        root.setVisible(false);
                                        DrawEnvironment.showEnvironment(stage);
                                    }
                                }
        );
    }

    public static void show(String args[]) {
        launch(args);
    }
}
