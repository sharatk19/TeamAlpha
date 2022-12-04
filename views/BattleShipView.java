
package views;

import BattleShip.BattleShipModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class BattleShipView {
    BattleShipModel model; //reference to model
    Stage stage;

    Button startButton, stopButton, loadButton, saveButton, newButton; //buttons for functions
    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");
    ArrayList<ArrayList<Button>> temp;
    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;

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
        this.model = model;
        this.stage = stage;
        temp = new ArrayList<>();
        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.paused = false;
        this.stage.setTitle("BATTLESHIP");
        this.width = this.model.getWidth()*pieceWidth + 2;
        this.height = this.model.getHeight()*pieceWidth + 2;
//        -fx-background-color: #81c483;
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: linear-gradient(to top, #ff7f50, #6a5acd)");
//        -fx-background-color: linear-gradient(to top, #ff7f50, #6a5acd)
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

        final ToggleGroup toggleGroup = new ToggleGroup();

//        RadioButton pilotButtonHuman = new RadioButton("Human");
//        pilotButtonHuman.setToggleGroup(toggleGroup);
//        pilotButtonHuman.setSelected(true);
//        pilotButtonHuman.setUserData(Color.SALMON);
//        pilotButtonHuman.setFont(new Font(16));
//        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");
//
//        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
//        pilotButtonComputer.setToggleGroup(toggleGroup);
//        pilotButtonComputer.setUserData(Color.SALMON);
//        pilotButtonComputer.setFont(new Font(16));
//        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        scoreLabel.setText("Ships Destroyed: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        stopButton = new Button("Stop");
        stopButton.setId("Start");
        stopButton.setPrefSize(150, 50);
        stopButton.setFont(new Font(12));
        stopButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        saveButton = new Button("Save");
        saveButton.setId("Save");
        saveButton.setPrefSize(150, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newButton = new Button("New Game");
        newButton.setId("New");
        newButton.setPrefSize(150, 50);
        newButton.setFont(new Font(12));
        newButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox controls = new HBox(20, saveButton, loadButton, newButton, startButton, stopButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

//        Slider slider = new Slider(0, 100, 50);
//        slider.setShowTickLabels(true);
//        slider.setStyle("-fx-control-inner-background: palegreen;");

//        VBox vBox = new VBox(20, slider);
//        vBox.setPadding(new Insets(20, 20, 20, 20));
//        vBox.setAlignment(Pos.TOP_CENTER);

//        VBox scoreBox = new VBox(20, scoreLabel, gameModeLabel, pilotButtonHuman, pilotButtonComputer);
//        scoreBox.setPadding(new Insets(20, 20, 20, 20));
//        vBox.setAlignment(Pos.TOP_CENTER);

//        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

//        timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(1.00), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {
            this.model.newGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            this.model.resumeGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        stopButton.setOnAction(e -> {
            this.model.stopGame();
            this.borderPane.requestFocus();
        });

        //configure this such that the save view pops up when the saveButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        saveButton.setOnAction(e -> {
            this.createSaveView();
            this.borderPane.requestFocus();
        });

        //configure this such that the load view pops up when the loadButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        loadButton.setOnAction(e -> {
            //TO DO!
            this.createLoadView();
            this.borderPane.requestFocus();
        });

        //configure this such that you adjust the speed of the timeline to a value that
        //ranges between 0 and 3 times the default rate per model tick.  Make sure to return the
        //focus to the borderPane once you're done!
//        slider.setOnMouseReleased(e -> {
//            //TO DO
//            double val = slider.getValue();
//            double temp = (3*val /100);
//            this.timeline.setRate(temp);
//            this.borderPane.requestFocus();
//        });

        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        // TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                //TO DO
                if(k.getCode() == KeyCode.R){
                    model.modelTick(BattleShipModel.MoveType.ROTATE, 0, 0, BattleShipModel.PlayerType.HUMAN);
                }
                else if(k.getCode() == KeyCode.S){
                    model.modelTick(BattleShipModel.MoveType.DROP,  0, 0, BattleShipModel.PlayerType.HUMAN);
                }
                else if(k.getCode() == KeyCode.A){
                    model.modelTick(BattleShipModel.MoveType.RIGHT,  0, 0, BattleShipModel.PlayerType.HUMAN);
                }
                else if(k.getCode() == KeyCode.D){
                    model.modelTick(BattleShipModel.MoveType.LEFT,  0, 0, BattleShipModel.PlayerType.HUMAN);
                }

            }
        });

        borderPane.setTop(controls);
//        borderPane.setRight(scoreBox);
        borderPane.setCenter(canvas);
//        borderPane.setBottom(vBox);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Get user selection of "autopilot" or human player
     *
     * @param value toggle selector on UI
     */
//    private void swapPilot(Toggle value) {
//        RadioButton chk = (RadioButton)value.getToggleGroup().getSelectedToggle();
//        String strVal = chk.getText();
//        if (strVal.equals("Computer (Default)")){
//            this.model.setAutoPilotMode();
//            gameModeLabel.setText("Player is: Computer (Default)");
//        } else if (strVal.equals("Human")) {
//            this.model.setHumanPilotMode();
//            gameModeLabel.setText("Player is: Human");
//        }
//        borderPane.requestFocus(); //give the focus back to the pane with the blocks.
//    }

    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (this.paused != true) {
            createBoard();
//            paintBoard();
            this.model.modelTick(BattleShipModel.MoveType.ROTATE,  0, 0, BattleShipModel.PlayerType.HUMAN);
//            updateScore();
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
        GridPane player_board = new GridPane();
        for(int i = 0; i < 10; i++){
            player_board.getColumnConstraints().add(new ColumnConstraints(30));
            player_board.getRowConstraints().add(new RowConstraints(30));
        }
        ArrayList<Button> temp2 = new ArrayList<>();

        for(int x = 0; x< 10; x++){
            for(int y = 0; y < 10; y++){
                Button button = new Button();
                button.setPrefHeight(30);
                button.setPrefWidth(30);
                GridPane.setConstraints(button, y, x);
                player_board.getChildren().add(button);
                temp2.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Placed ShipSquare on Row:" + GridPane.getRowIndex(button));
                        System.out.println("Placed ShipSquare on Column:" + GridPane.getColumnIndex(button));
                        button.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
                    }
                });
            }
            temp.add(temp2);
            temp2 = new ArrayList<>();
        }
        borderPane.getChildren().add(player_board);
    }





    public void paintBoard() {

        // Draw a rectangle around the whole screen
        gc.setStroke(Color.GREEN);
        gc.setFill(Color.GHOSTWHITE);
        gc.fillRect(-100, 100, 2*this.width, this.height);

        // Draw the line separating the top area on the screen
//        gc.setStroke(Color.BLACK);
//        int spacerY = yPixel(this.model.get_player_Board().getHeight() - this.model.BUFFERZONE - 1);
//        gc.strokeLine(0, spacerY, this.width-1, spacerY);

        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.get_player_Board().getWidth();;

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = 10;
            for (y=0; y<yHeight; y++) {
                if (this.model.get_player_Board().getGrid(x, y)) {
                    gc.setFill(Color.RED);
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(Color.GHOSTWHITE);
                }
            }
        }

    }

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

