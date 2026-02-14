package saru;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setText(text);
    }

    public static DialogBox getUserDialog(String text) {
        DialogBox box = new DialogBox("You: " + text);
        box.dialog.setStyle("-fx-background-color: #d9fdd3; -fx-padding: 10; -fx-background-radius: 10;");
        return box;
    }

    public static DialogBox getSaruDialog(String text) {
        return new DialogBox("Saru: " + text);
    }
}
