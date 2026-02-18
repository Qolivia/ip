package saru;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Saru saru;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/unuko.png"));
    private Image saruImage = new Image(this.getClass().getResourceAsStream("/images/monkey.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }


    /** Injects the Saru instance */
    public void setSaru(Saru s) {
        saru = s;
    }

    public void showWelcome() {
        dialogContainer.getChildren().add(
                DialogBox.getSaruDialog(saru.getWelcomeMessage(), saruImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Saru's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = saru.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSaruDialog(response, saruImage)
        );
        userInput.clear();
    }
}
