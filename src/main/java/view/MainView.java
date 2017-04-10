package main.java.view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.java.controller.Controller;
import main.java.controller.MainController;
import main.java.model.Model;
import main.java.model.ModelController;
import main.java.util.CoordPair;
import main.java.util.Word;

import java.util.ArrayList;
import java.util.Objects;

public class MainView extends View {

    @FXML
    private ListView<Word> solutionView;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ButtonBar sizeControlBar;

    @FXML
    private Label sizeControlLabel;

    @FXML
    private TextField sizeControlTextField;

    @FXML
    private Button sizeControlButton;

    @FXML
    private ButtonBar letterControlBar;

    @FXML
    private Label letterControlLabel;

    @FXML
    private TextField lettersControlTextField;

    @FXML
    private Button lettersControlButton;

    @FXML
    private GridPane letterGrid;

    private Model model;
    private MainController controller;
    private int currentGridSize;

    public MainView() {
    }

    public void init(Model model) {
        this.model = model;
        model.addView(this);
        controller = new MainController((ModelController) model);
        currentGridSize = model.getSize();

        Controller.setupDigitFieldListener(sizeControlTextField);
        Controller.setupCharFieldListener(lettersControlTextField);

        sizeControlButton.setOnAction((event) -> controller.onSize(event, sizeControlTextField.getText()));
        lettersControlButton.setOnAction(event -> controller.onLetters(event, lettersControlTextField.getText()));

        solutionView.setItems(model.getWordList());
        solutionView.getSelectionModel().selectedItemProperty().addListener(controller::wordListListener);
        update();
    }

    public MainController getController() {
        return controller;
    }

    private void updateLetterGrid() {
        if (currentGridSize != model.getSize()) currentGridSize = model.getSize();

        letterGrid.getChildren().clear();
        letterGrid.getColumnConstraints().clear();
        letterGrid.getRowConstraints().clear();

        for (int i = 0; i < currentGridSize; i++) {
            for (int j = 0; j < currentGridSize; j++) {
                StackPane basePane = new StackPane(new Text(Character.toString(model.charAt(i, j)).toUpperCase()));
                letterGrid.add(basePane, j, i);
                GridPane.setHalignment(basePane, HPos.CENTER);
            }

            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setPercentWidth(100.0/currentGridSize);
            letterGrid.getColumnConstraints().add(cc);


            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setPercentHeight(100.0/currentGridSize);
            letterGrid.getRowConstraints().add(rc);
        }
    }

    private void selectPath(ArrayList<CoordPair> path) {
        drawPath(path, "-fx-background-color: lightblue;");
    }

    private void unselectPath(ArrayList<CoordPair> path) {
        drawPath(path, "");
    }

    private void drawPath(ArrayList<CoordPair> path, String style) {
        path.stream()
                .filter(Objects::nonNull)
                .forEach(coordPair ->
                        // Occasionly thows an error that should have been catched by the filter.
                    Platform.runLater(() -> getNodeByRowColumnIndex(coordPair.getX(), coordPair.getY(), letterGrid).setStyle(style))
        );
    }

    @Override
    public void update() {
        if (model.getPreviousWord() != null)
            unselectPath(model.getPreviousWord().getPath());

        if (model.getCurrentWord() != null)
            selectPath(model.getCurrentWord().getPath());

        if (model.hasChanged())
            updateLetterGrid();
    }

    // Because JavaFX doesn't have a native API for this...
    // http://stackoverflow.com/a/20828724
    private static Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
                return node;

        return null;
    }
}
