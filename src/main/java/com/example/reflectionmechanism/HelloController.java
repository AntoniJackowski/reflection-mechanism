package com.example.reflectionmechanism;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
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

    // Map to store references to dynamically created text controls for saving later
    private final Map<String, TextInputControl> fieldInputs = new HashMap<>();

    @FXML
    protected void onCreateObjectClick() {
        String className = classNameInput.getText().trim();

        // Reset the visual form, console, and internal storage map
        propertiesGrid.getChildren().clear();
        fieldInputs.clear();
        consoleOutput.clear();

        try {
            // Dynamically load the class using its full package name
            Class<?> clazz = Class.forName(className);

            // Instantiate the class using the default parameterless constructor
            currentObject = clazz.getDeclaredConstructor().newInstance();
            consoleOutput.appendText("Successfully created object of type: " + className + "\n");

            // Extract all properties (fields) declared within this class
            Field[] fields = clazz.getDeclaredFields();
            int rowIndex = 0;

            // Loop through each discovered field to build the form
            for (Field field : fields) {
                String fieldName = field.getName();
                TextInputControl inputControl;

                // Requirement check: If the field name contains "text", use a TextArea. Otherwise, use a TextField.
                if (fieldName.toLowerCase().contains("text")) {
                    TextArea textArea = new TextArea();
                    textArea.setPrefRowCount(3); // Set a default size for text areas
                    inputControl = textArea;
                } else {
                    inputControl = new TextField();
                }

                // Invoke the getter method using reflection to get the initial value
                // Capitalize the first letter of the field name to follow JavaBean standards (e.g., "title" -> "Title")
                String capitalizedName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String getterName = "get" + capitalizedName;

                try {
                    // Look up the public getter method with no parameters
                    Method getter = clazz.getMethod(getterName);
                    // Call the method on our current object instance
                    Object value = getter.invoke(currentObject);

                    // If the object has an initial value, display it in the form
                    if (value != null) {
                        inputControl.setText(value.toString());
                    }
                } catch (NoSuchMethodException e) {
                    consoleOutput.appendText("Warning: No getter found for property '" + fieldName + "' (" + getterName + "())\n");
                }

                // Store the input reference and display it in the visual grid layout
                fieldInputs.put(fieldName, inputControl);

                Label nameLabel = new Label("<- " + fieldName);

                // Add to GridPane
                propertiesGrid.add(inputControl, 0, rowIndex);
                propertiesGrid.add(nameLabel, 1, rowIndex);

                rowIndex++;
            }

        } catch (ClassNotFoundException e) {
            consoleOutput.appendText("Error: Class '" + className + "' could not be found. Please check your package path.\n");
        } catch (Exception e) {
            consoleOutput.appendText("Error: Failed to dynamically inspect or instantiate the class.\n");
            e.printStackTrace();
        }
    }



    @FXML
    protected void onSaveChangesClick() {

        // Save logic
    }
}