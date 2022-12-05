package views;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainMenu(ActionEvent event) throws IOException {
        URL url = getClass().getResource("MainMenu.fxml");
        root = FXMLLoader.load(url);

        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOptions(ActionEvent event) throws IOException {
        URL url = getClass().getResource("Options.fxml");
        root = FXMLLoader.load(url);

        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
