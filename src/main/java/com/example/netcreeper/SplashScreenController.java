package com.example.netcreeper;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreenController {

    @FXML
    private ProgressBar splashprogress;

    @FXML
    private void initialize() {
        // Initialize the ProgressBar
        splashprogress.setProgress(0);

        // Bind the progress property of the ProgressBar to the progress of the initialization Task
        Task<Void> initializationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Initialize your application here
                for (int i = 1; i <= 100; i++) {
                    // Update the progress property of the Task as the initialization progresses
                    updateProgress(i, 100);
                    Thread.sleep(10);
                }
                return null;
            }
        };
        splashprogress.progressProperty().bind(initializationTask.progressProperty());

        // When the Task completes, transition to the main application
        initializationTask.setOnSucceeded(event -> {
            try {
                showMainApplication();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Start the Task
        new Thread(initializationTask).start();
    }

    private void showMainApplication() throws IOException {
        // Hide the splash screen stage
        Stage splashScreenStage = (Stage) splashprogress.getScene().getWindow();
        splashScreenStage.hide();

        // Create the main application stage
        Stage mainApplicationStage = new Stage();

        // Set the scene to your main application FXML file
        Parent root = FXMLLoader.load(getClass().getResource("MainApplication.fxml"));
        Scene scene = new Scene(root);
        mainApplicationStage.setScene(scene);

        // Show the main application stage
        mainApplicationStage.show();
    }
}
