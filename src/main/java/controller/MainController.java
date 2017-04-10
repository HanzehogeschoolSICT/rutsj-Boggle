package main.java.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import main.java.model.ModelController;
import main.java.util.Word;

public class MainController {
    private ModelController model;

    public MainController(ModelController model) {
        this.model = model;
    }

    public void onSize(ActionEvent e, String text) {
        int size = text.equals("") ? 4 : Integer.parseInt(text);
        model.setSize(size);
    }

    public void onLetters(ActionEvent e, String text) {
        if (!text.equals(""))
            model.setLetters(text);
    }

    public void wordListListener(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
        model.updateWord(newValue);
    }
}