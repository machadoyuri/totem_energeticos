package br.feevale;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("primary.fxml")
        );

        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Totem de Energéticos");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(FXMLLoader.load(App.class.getResource(fxml + ".fxml")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
