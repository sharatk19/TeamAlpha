package views;

import BattleShip.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BattleShipView {
    BattleShipModel model; //reference to model
    Stage stage;
    Boolean game_started;
    Integer squares_placed;
    Stack<String> ships_sizes;
//    PlayerInterface Player;
    Map<String, Integer> ship_definitions;

    PlayerInterface new_playerInterface;
    PlayerShipCommand set_ships_players;

    PlayerMoveCommand player_move;
    Boolean colorBlindMode;
    Button startButton, newButton, resetbutton, rotateButton; //buttons for functions
    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");

    Label currentship = new Label("");

    Label ai_ship_count_label = new Label("");

    Label player_ship_count = new Label("");
    ArrayList<ArrayList<Button>> temp;
    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    int current_size;
    ArrayList<ShipSquare> current_player_Ship;
    Boolean paused;
    Timeline timeline;
    GridPane player_board;
    GridPane ai_board;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public BattleShipView(BattleShipModel model, Stage stage) {
        this.game_started = false;
        this.model = model;
        colorBlindMode = SceneController.colorbool;
        this.stage = stage;
        temp = new ArrayList<>();

        this.new_playerInterface = new PlayerInterface(this.model);
//        this.player_move = new PlayerMoveCommand(this.new_playerInterface, new Move(new int[], new boolean), );


        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {

        this.paused = true;
        this.stage.setTitle("BATTLESHIP");
        this.width = this.model.getWidth()*pieceWidth + 2;
        this.height = this.model.getHeight()*pieceWidth + 2;
//        -fx-background-color: #81c483;
//        colorblind -fx-background-color: #A8A8A8;
        borderPane = new BorderPane();
//      takes information from the controller for whether or not color blind mode is on
        if (colorBlindMode) {
            borderPane.setStyle("-fx-background-color: linear-gradient(to top, #A0A0A0, #6B6B6B)");
        } else {
            borderPane.setStyle("-fx-background-color: linear-gradient(to top, #ff7f50, #6a5acd)");
        }
//        -fx-background-color: linear-gradient(to top, #ff7f50, #6a5acd)
//        colorblind -fx-background-color: linear-gradient(to top, #A0A0A0, #6B6B6B)

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gameModeLabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gameModeLabel.setText("Player is: Human");
        gameModeLabel.setMinWidth(250);
        gameModeLabel.setFont(new Font(20));
        gameModeLabel.setStyle("-fx-text-fill: #e8e6e3");


        ai_ship_count_label.setId("Ai Ships Destroyed: ");
        ai_ship_count_label.setText("AI SHIPS DESTROYED: ");
        ai_ship_count_label.setMinWidth(250);
        ai_ship_count_label.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        ai_ship_count_label.setStyle("-fx-text-fill: white;");

        player_ship_count.setId("Player Ships Destroyed: ");
        player_ship_count.setText("Player SHIPS DESTROYED: ");
        player_ship_count.setMinWidth(250);
        player_ship_count.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        player_ship_count.setStyle("-fx-text-fill: white;");

        //add buttons
//        startButton = new Button("Start");
//        startButton.setId("Start");
//        startButton.setPrefSize(150, 50);
//        startButton.setFont(new Font(12));



//        stopButton = new Button("Stop");
//        stopButton.setId("Start");
//        stopButton.setPrefSize(150, 50);
//        stopButton.setFont(new Font(12));
//        if (colorBlindMode) {
//            stopButton.setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
//        } else {
//            stopButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        }
//
//        saveButton = new Button("Save");
//        saveButton.setId("Save");
//        saveButton.setPrefSize(150, 50);
//        saveButton.setFont(new Font(12));

//        if (colorBlindMode) {
//            saveButton.setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
//        } else {
//            saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        }
//
//        loadButton = new Button("Load");
//        loadButton.setId("Load");
//        loadButton.setPrefSize(150, 50);
//        loadButton.setFont(new Font(12));
//        if (colorBlindMode) {
//            loadButton.setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
//        } else {
//            loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        }

        newButton = new Button("New Game");
        newButton.setId("New");
        newButton.setPrefSize(150, 50);
        newButton.setFont(new Font(12));
        // takes information from the controller for whether or not color blind mode is on
        if (colorBlindMode) {
            newButton.setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
        } else {
            newButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        }


        resetbutton = new Button("Reset Placement");
        resetbutton.setId("Reset");
        resetbutton.setPrefSize(150, 50);

        rotateButton = new Button("Rotate Ship");
        rotateButton.setId("Rotate");
        rotateButton.setPrefSize(150, 50);

        startButton = new Button("Start Game");
        startButton.setId("start");
        startButton.setPrefSize(150, 50);
        // takes information from the controller for whether or not color blind mode is on
        if (colorBlindMode) {
            startButton.setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
        } else {
            startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        }
        VBox controls = new VBox(20, startButton, resetbutton, rotateButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.TOP_RIGHT);


        Slider slider = new Slider(0, 100, 50);
//        slider.setShowTickLabels(true);
        slider.setStyle("-fx-control-inner-background: white;");

        VBox vBox = new VBox(20, slider);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        VBox scoreBox = new VBox(20, ai_ship_count_label, player_ship_count);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

//        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

//        timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        resetbutton.setOnAction(e -> {
            //TO DO!
            if(this.game_started){
                return;
            }

            this.createBoard();
            this.squares_placed = 0;
            this.current_player_Ship = new ArrayList<>();
            this.borderPane.requestFocus();
        });
        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {
            this.model.newGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            this.paused = false;
            this.game_started = true;
            System.out.println("The Game has Begun");

//            System.out.println("Finish Placing Ships, Then the Game Shall Begin");
//            this.model.startGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        rotateButton.setOnAction(e -> {
            this.model.rotateCurrent();
            this.borderPane.requestFocus();
        });


        borderPane.setCenter(canvas);
        borderPane.setCenter(controls);
        borderPane.setBottom(scoreBox);
//        borderPane.setCenter(canvas);
//        borderPane.setBottom(vBox);

        var scene = new Scene(borderPane, 1000, 720);
        this.stage.setScene(scene);
        this.stage.show();
        // Create Player Board for Player to Place Ships
        createBoard();
        createAIBoard();
        paused = false;
    }


    public void set_all_buttons_enabled(){
        for(ArrayList<Button> array_ofButtons: temp){
            for(Button button: array_ofButtons){
                if(button.isDisable()){
                    button = new Button();
                }
            }
        }
    }


    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (this.paused != true) {

            paintBoard();
            this.model.modelTick(0, 0, BattleShipModel.PlayerType.HUMAN);
            player_ship_count.setText("Player SHIPS DESTROYED: " + model.get_ai_Board().getDeadShips().size());
            ai_ship_count_label.setText("AI SHIPS DESTROYED: " + model.get_player_Board().getDeadShips().size());
        }
    }

    /**
     * Update score on UI
     */
//    private void updateScore() {
//        if (this.paused != true) {
//            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());
//        }
//    }

    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    private final int xPixel(int x) {
        return (int) Math.round(this.width -1 - (x+1)*dX());
    }
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.get_player_Board().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.get_player_Board().getHeight() );
    }

    /**
     * Draw the board
     */
    public void createBoard(){
        player_board = new GridPane();
        for(int i = 0; i < 10; i++){
            player_board.getColumnConstraints().add(new ColumnConstraints(30));
            player_board.getRowConstraints().add(new RowConstraints(30));
        }
        ArrayList<Button> temp2 = new ArrayList<>();

        for(int x = 0; x< 10; x++){
            for(int y = 0; y < 10; y++){
                final Button[] button = {new Button()};
                button[0].setTranslateX(550);
                button[0].setTranslateY(200);
                button[0].setPrefHeight(30);
                button[0].setPrefWidth(30);
                GridPane.setConstraints(button[0], y, x);
                player_board.getChildren().add(button[0]);
                temp2.add(button[0]);

                int temp_x = x;
                int temp_y = y;
                final Button[] temp4 = {button[0]};

                button[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (model.getGamePhase() == 0) {
                            model.hoverMove(GridPane.getRowIndex(button[0]), GridPane.getColumnIndex(button[0]));
                        }
//                        System.out.println();
//                        Arrays.stream(model.get_player_Board().getViewGrid()).forEach(s -> System.out.println(Arrays.toString(s)));
                    }
                });

                button[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (model.getGamePhase() == 0) {
                            PlayerShipCommand playerShipCommand = new PlayerShipCommand(new_playerInterface);
                            playerShipCommand.execute();
                        }
                        // takes information from the controller for whether or not color blind mode is on
                        if (colorBlindMode) {
                            button[0].setStyle("-fx-background-color: #595959; -fx-text-fill: white;");
                        } else {
                            button[0].setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
                        }
                    }
                });
            }
            temp.add(temp2);
            temp2 = new ArrayList<>();
        }
        borderPane.getChildren().add(player_board);
    }

    public void createAIBoard(){
        ai_board = new GridPane();
        ArrayList<Button> temp2 = new ArrayList<>();

        for(int x = 0; x< 10; x++){
            for(int y = 0; y < 10; y++){
                Button button = new Button();
                button.setTranslateX(100);
                button.setTranslateY(200);
                button.setPrefHeight(30);
                button.setPrefWidth(30);
                GridPane.setConstraints(button, y, x);
                ai_board.getChildren().add(button);
                temp2.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PlayerMoveCommand playerMoveCommand = new PlayerMoveCommand(new_playerInterface, GridPane.getRowIndex(button), GridPane.getColumnIndex(button));
                        playerMoveCommand.execute();

//                        System.out.println("Attacked Enemy on ShipSquare on Row:" + GridPane.getRowIndex(button));
//                        System.out.println("Attacked Enemy on on Column:" + GridPane.getColumnIndex(button));
//                        if (colorBlindMode) {
//                            button.setStyle("-fx-background-color: #595959; -fx-text-fill: #7F7F7F;");
//                        } else {
//                            button.setStyle("-fx-background-color: grey; -fx-text-fill: grey;");
//                        }
                    }
                });
            }
            temp.add(temp2);
            temp2 = new ArrayList<>();
        }
        borderPane.getChildren().add(ai_board);


    }
    public void timer(){
        return;
    }




    public void paintBoard() {
        boolean[][] playerGrid = model.get_player_Board().getViewGrid();
        BoardSquare[][] pBoard = model.get_player_Board().getBoard();
        BoardSquare[][] aBoard = model.get_ai_Board().getBoard();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Button pButton = (Button) player_board.getChildren().get(10 * x + y);
                Button aButton = (Button) ai_board.getChildren().get(10 * x + y);
                if (model.getGamePhase() == 0) {
                    if (playerGrid[x][y]) {
                        pButton.setStyle("-fx-background-color: red; -fx-text-fill: grey;");
                    } else {
                        pButton.setStyle(null);
                    }
                } else if (model.getGamePhase() == 1) {
                    if (pBoard[x][y].getState() == 1) {
                        pButton.setStyle("-fx-background-color: #4c4c4c; -fx-text-fill: grey;");
                    } else if (pBoard[x][y].getState() == 2) {
                        pButton.setStyle("-fx-background-color: red; -fx-text-fill: grey;");
                    } else if (pBoard[x][y].hasShip()) {
                        pButton.setStyle("-fx-background-color: green; -fx-text-fill: grey;");
                    }

                    if (aBoard[x][y].getState() == 1) {
                        aButton.setStyle("-fx-background-color: #4c4c4c; -fx-text-fill: grey;");
                    } else if (aBoard[x][y].getState() == 2) {
                        aButton.setStyle("-fx-background-color: red; -fx-text-fill: grey;");
                    }
                }

            }

        }
    }
//
//        // Draw a rectangle around the whole screen
//        if (colorBlindMode) {
//            gc.setStroke(Color.color(150, 150, 150));
//        } else {
//            gc.setStroke(Color.GREEN);
//        }
//        if (colorBlindMode) {
//            gc.setFill(Color.color(248, 248, 248));
//        } else {
//            gc.setFill(Color.GHOSTWHITE);
//        }
//        gc.fillRect(-100, 100, 2*this.width, this.height);
//
//        // Draw the line separating the top area on the screen
////        gc.setStroke(Color.BLACK);
////        int spacerY = yPixel(this.model.get_player_Board().getHeight() - this.model.BUFFERZONE - 1);
////        gc.strokeLine(0, spacerY, this.width-1, spacerY);
//
//        // Factor a few things out to help the optimizer
//        final int dx = Math.round(dX()-2);
//        final int dy = Math.round(dY()-2);
//        final int bWidth = this.model.get_player_Board().getWidth();;
//
//        int x, y;
//        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
//        for (x=0; x<bWidth; x++) {
//            int left = xPixel(x);	// the left pixel
//            // draw from 0 up to the col height
//            final int yHeight = 10;
//            for (y=0; y<yHeight; y++) {
//                if (this.model.get_player_Board().getGrid(x, y)) {
//                    if (colorBlindMode) {
//                        gc.setFill(Color.color(76, 76, 76));
//                    } else {
//                        gc.setFill(Color.RED);
//                    }
//                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
//                    if (colorBlindMode) {
//                        gc.setFill(Color.color(248, 248, 248));
//                    } else {
//                        gc.setFill(Color.GHOSTWHITE);
//                    }
//                }
//            }
//        }
//
//    }

    /**
     * Create the view to save a board to a file
     */
    private void createSaveView(){
        SaveView saveView = new SaveView(this);
    }

    /**
     * Create the view to select a board to load
     */
    private void createLoadView(){
        LoadView loadView = new LoadView(this);
    }



}

