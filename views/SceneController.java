package views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import BattleShip.BattleShipModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SceneController {

    BattleShipModel model;
    BattleShipView view;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static Boolean colorbool = false;

    public static Boolean musicbool = false;

    MediaPlayer player;

    @FXML
    public ToggleButton colorButton;

    @FXML
    public ToggleButton musicButton;

    @FXML
    private Label colortext;

    @FXML
    private Label musictext;

    @FXML
    private AnchorPane optionsBackground;

    @FXML
    private AnchorPane menuBackground;

    public void switchToMainMenu(ActionEvent event) throws IOException {
        if (colorbool){
            URL url = getClass().getResource("MainMenuBW.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            URL url = getClass().getResource("MainMenu.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void switchToOptions(ActionEvent event) throws IOException {
        if (colorbool & musicbool){
            URL url = getClass().getResource("OptionsMusicBW.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (colorbool) {
            URL url = getClass().getResource("OptionsBW.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (musicbool){
            URL url = getClass().getResource("OptionsMusic.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else{
            URL url = getClass().getResource("Options.fxml");
            root = FXMLLoader.load(url);

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    // tie this to actually starting the game
    public void start(ActionEvent event) throws IOException {

        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();

        this.model = new BattleShipModel(); // create a model
        this.view = new BattleShipView(model, stage);
        this.model.startGame();
    }

    public void toggleButton(ActionEvent event) throws IOException {
        if (event.getSource() == colorButton){
            if (colorButton.isSelected()) {
                colortext.setText("Color Blind Mode: On");
                colorbool = true;
                optionsBackground.getStylesheets().clear();
                optionsBackground.getStylesheets().add("CssStyle/BackgroundBlackAndWhite.css");
            } else {
                colortext.setText("Color Blind Mode: Off");
                colorbool = false;
                optionsBackground.getStylesheets().clear();
                optionsBackground.getStylesheets().add("CssStyle/BackgroundPicture.css");
            }
        }
        if (event.getSource() == musicButton){
            if (musicButton.isSelected()) {
                musictext.setText("Music: On");
                musicbool = true;
                playBackgroundMusic();
            } else {
                musictext.setText("Music: Off");
                musicbool = false;
                stopBackgroundMusic();
            }
        }
    }

    private void playBackgroundMusic(){
        File musicfile = new File("music");
        File song = musicfile.listFiles()[0];
        Media media = new Media(song.toURI().toString());
        player = new MediaPlayer(media);
        player.play();
    }

    private void stopBackgroundMusic(){
        player.stop();
    }
}
