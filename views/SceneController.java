package views;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController {

    SceneController controller;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public ToggleButton colorButton;

    @FXML
    public ToggleButton musicButton;

    @FXML
    public ToggleButton audioButton;

    @FXML
    private Label colortext;

    @FXML
    private Label musictext;

    @FXML
    private Label audiotext;

    @FXML
    private AnchorPane optionsBackground;

    @FXML
    private AnchorPane menuBackground;

    public void initialize(){
        this.controller = this;
    }
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

    public void logout(ActionEvent event) throws IOException {

        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    // tie this to actually starting the game
//    public void start(ActionEvent event) throws IOException {
//
//        Node source = (Node)event.getSource();
//        stage = (Stage)source.getScene().getWindow();
//    }

    public void toggleButton(ActionEvent event) throws IOException {
        if (event.getSource() == colorButton){
            if (colorButton.isSelected()) {
                colortext.setText("Color Blind Mode: On");
//                colorBlindMode();
            } else {
                colortext.setText("Color Blind Mode: Off");
            }
        }
        if (event.getSource() == musicButton){
            if (musicButton.isSelected()) {
                musictext.setText("Music: On");
            } else {
                musictext.setText("Music: Off");
            }
        }
        if (event.getSource() == audioButton){
            if (audioButton.isSelected()) {
                audiotext.setText("Audio: On");
            } else {
                audiotext.setText("Audio: Off");
            }
        }
    }
//
//    private void colorBlindMode() throws IOException {
//        optionsBackground.getStylesheets().add()
//    }
}
