package com.example.reflectionmechanism;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML
    private TextField classNameInput;

    @FXML
    private GridPane propertiesGrid;

    @FXML
    private TextArea consoleOutput;

    // Reference to currently edited object
    private Object currentObject;

    @FXML
    public void initialize() {
        // Set default value
        classNameInput.setText("com.example.reflectionmechanism.Song");
    }

    @FXML
    protected void onCreateObjectClick() {
        String className = classNameInput.getText().trim();

        // Reflection logic
    }

    @FXML
    protected void onSaveChangesClick() {

        // Save logic
    }
}