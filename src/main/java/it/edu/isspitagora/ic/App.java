package it.edu.isspitagora.ic;

import it.edu.isspitagora.ic.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Controller controller; //riferimento al controller
    private static Model model; //riferimento al model

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        scene = new Scene(loadFXML("secondary"), 640, 480);
        controller.setModel(model); //passo il model al controller corrente
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        controller.setModel(model);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        Parent risultato;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        risultato = fxmlLoader.load();
        controller = fxmlLoader.getController();
        return risultato;
    }

    public static void main(String[] args) {
        launch();
    }

}