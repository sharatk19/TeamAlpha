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

    // Switch to main menu upon clicking back in the options screen.
    public void switchToMainMenu(ActionEvent event) throws IOException {
        // If color blind mode is on it sets it to a black and white main menu, and if it isn't it sets it to a colored
        // one. Same thing done for switchToOptions for 4 separate .fxml files based off music and color.
        if (colorbool){
            URL url = getClass().getResource("MainMenuBW.fxml");
            root = FXMLLoader.load(url); // Load the .fxml file into root.

            Node source = (Node)event.getSource();
            stage = (Stage)source.getScene().getWindow(); // Get the stage.
            scene = new Scene(root); // Create a new scene with the root.
            // Set the scene onto the stage and display it, same thing is done within all switching functions
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
        // same implementation as switchToMenu
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

    // Terminate the program when clicking quit.
    public void logout(ActionEvent event) throws IOException {
        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    // Start the battleship game upon clicking start
    public void start(ActionEvent event) throws IOException {

        Node source = (Node)event.getSource();
        stage = (Stage)source.getScene().getWindow();

        this.model = new BattleShipModel(); // create a model
        this.view = new BattleShipView(model, stage);
        this.model.startGame();
    }

    // Assign functionality to the buttons and make changes based off of the choices the user makes in options.
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

    // Play and stop music.
    private void playBackgroundMusic(){
        File musicfile = new File("music");
        File song = musicfile.listFiles()[0]; // Store the background music in a file.
        Media media = new Media(song.toURI().toString()); // Create a new media object.
        // Pass the media object into the media player and play it.
        player = new MediaPlayer(media);
        player.play();
    }

    private void stopBackgroundMusic(){
        player.stop();
    }
}
