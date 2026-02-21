package saru;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Saru using FXML.
 */
public class Main extends Application {

    private Saru saru = new Saru();

    /**
     * Starts the JavaFX application by loading the main window and showing the welcome message.
     *
     * @param stage Primary stage created by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setScene(scene);
            stage.setTitle("Saru");
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            MainWindow controller = fxmlLoader.getController();
            controller.setSaru(saru);
            controller.showWelcome();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
