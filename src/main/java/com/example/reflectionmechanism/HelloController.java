package com.example.reflectionmechanism;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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
        // Get package and class name from input
        String className = classNameInput.getText().trim();

        consoleOutput.clear();
        propertiesGrid.getChildren().clear();

        // Handle reflection mechanism
        try {
            Class<?> loadedClass = Class.forName(className);
            Constructor<?> constructor = loadedClass.getDeclaredConstructor();

            currentObject = constructor.newInstance();
            consoleOutput.appendText("Object of class " + className + " created\n");

            int rowIndex = 0;

            Field[] fields = loadedClass.getDeclaredFields();


            for (Field field : fields) {
                if (field.getName().toLowerCase().contains("text")) {
                    propertiesGrid.add(new TextArea(), 0, rowIndex);
                    propertiesGrid.add(new Label("<- " + field.getName()), 1, rowIndex);
                } else {
                    propertiesGrid.add(new TextField(), 0, rowIndex);
                    propertiesGrid.add(new Label("<- " + field.getName()), 1, rowIndex);
                }
                rowIndex++;
            }

        } catch (ClassNotFoundException e) {
            consoleOutput.setText("Class " + className + " not found.");
        } catch (NoSuchMethodException e) {
            consoleOutput.setText("Cannot find constructor for class " + className);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSaveChangesClick() {


        consoleOutput.clear();
        propertiesGrid.getChildren().clear();
    }
}