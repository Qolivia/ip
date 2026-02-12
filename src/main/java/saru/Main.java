package saru;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Basic JavaFX GUI stub.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label hello = new Label("Hello World!");
        Scene scene = new Scene(hello, 300, 100);

        stage.setTitle("Saru");
        stage.setScene(scene);
        stage.show();
    }
}
