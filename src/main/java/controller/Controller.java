package main.java.controller;

import javafx.scene.control.TextField;
import main.java.model.Model;

public abstract class Controller {
    abstract void init(Model model);

    public static void setupDigitFieldListener(TextField textField) {
        textField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        textField.setText(newValue.replaceAll("[^\\d]", ""))
        );
    }

    public static void setupCharFieldListener(TextField textField) {
        textField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        textField.setText(newValue.replaceAll("[^a-zA-Z]", ""))
        );
    }
}
