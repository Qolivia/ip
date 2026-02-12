package saru;

import javafx.application.Application;

/**
 * Entry point for JavaFX applications.
 * This class exists to avoid classpath/module issues when launching JavaFX.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
