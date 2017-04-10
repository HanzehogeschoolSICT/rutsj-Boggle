package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.BoggleModel;
import main.java.view.MainView;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/view.fxml"));
        Parent root = loader.load();
        MainView view = loader.getController();

        BoggleModel model = new BoggleModel();
        model.loadTrie();
        view.init(model);

        stage.setTitle("Boggle Solver");
        stage.setScene(new Scene(root));
        stage.setMinWidth(650);
        stage.setMinHeight(450);

        stage.setOnCloseRequest(event -> {
            model.stopThreads();
            System.exit(0);
        });

        stage.show();
    }
}
