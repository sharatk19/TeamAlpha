package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class BattleShipApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



// Old battleship app before menu was implemented:

//import BattleShip.BattleShipModel;
//import javafx.stage.Stage;
//import views.BattleShipView;
//import javafx.application.Application;
//
//public class BattleShipApp extends Application {
//    BattleShipModel model;
//    BattleShipView view;
//
//    /**
//     * Main method
//     *
//     * @param args agument, if any
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    /**
//     * Start method.  Control of application flow is dictated by JavaFX framework
//     *
//     * @param primaryStage stage upon which to load GUI elements
//     */
//    @Override
//    public void start(Stage primaryStage) {
//        this.model = new BattleShipModel(); // create a model
//        this.view = new BattleShipView(model, primaryStage); //tie the model to the view
//        this.model.startGame(); //begin
//    }
//}
