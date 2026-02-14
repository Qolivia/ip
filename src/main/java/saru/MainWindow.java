package saru;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    private Saru saru;

    public void setSaru(Saru saru) {
        this.saru = saru;
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void showWelcome() {
        dialogContainer.getChildren().add(DialogBox.getSaruDialog(saru.getWelcomeMessage()));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = saru.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSaruDialog(response)
        );

        userInput.clear();
    }
}
