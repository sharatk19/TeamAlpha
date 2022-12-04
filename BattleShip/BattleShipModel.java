package BattleShip;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BattleShipModel {

    public static final int WIDTH = 10; //size of the board in blocks
    public static final int HEIGHT = 20; //height of the board in blocks
    public static final int BUFFERZONE = 4; //space at the top

    protected Board player_board;  // Board data structure
    protected Board ai_board;  // Board data structure
    protected Iterator<Ship> ships; // Pieces to be places on the board

    protected Ship currentShip; //Piece we are currently placing
    protected Ship nextShip; //next piece to be placed
    protected int currentX, newX;
    protected int currentY, newY;

    private int count; //Ship # Count

    // State of the game
    protected boolean gameOn;	// true if we are playing
    protected int gamePhase;

    public enum PlayerType {
        HUMAN,
        COMP
    }
    public enum MoveType {
        ROTATE,
        LEFT,
        RIGHT,
        DROP,
        DOWN
    }

    /**
     * Constructor for a tetris model
     */
    public BattleShipModel() {
        player_board = new Board(WIDTH, HEIGHT + BUFFERZONE);
        ai_board = new Board(WIDTH, HEIGHT + BUFFERZONE);
        ships = Arrays.stream(Ship.getShips()).iterator();; //initialize board and pieces
        gameOn = false;
    }


    /**
     * Start new game
     */
    public void startGame() { //start game
        // Set Up Player Ships onto Grid
        playerShip_setup();

        // Set up Player
        ai_playerShip_Setup();

        gameOn = true;
    }

    /**
     * Player Board getter
     *
     * @return  board
     */
    public Board get_player_Board() {
        return this.player_board;
    }

    /**
     * AI Player Board getter
     *
     * @return  board
     */

    public Board get_ai_Board() {
        return this.ai_board;
    }

    public int getGamePhase() {
        return this.gamePhase;
    }


    /**
     * Setter For Player Ships
     */
    public void playerShip_setup(){
        addNewShip();
    }

    /**
     * Setter For AI Player Ships
     */
    public void ai_playerShip_Setup(){
        return;
    }


    /**
     * pause game
     */
    public void stopGame() {
        gameOn = false;
    }

    public void resumeGame() {
        gameOn = true;
    }
    /**
     * Get width
     *
     * @return width
     */
    public double getWidth() {
        return WIDTH;
    }

    /**
     * Get width
     *
     * @return height (with buffer at top accounted for)
     */
    public double getHeight() {
        return HEIGHT + BUFFERZONE;
    }

    /**
     * Compute New Position of Ship in play based on move type to Place ship
     *
     * @param verb type of move to account for when placing shi[
     */
    public void computeNewPosition(MoveType verb) {
        newX = currentX;
        newY = currentY;

        // Make changes based on the verb
        switch (verb) {
            case LEFT -> newX--;
            //move left

            case RIGHT -> newX++;
            //move right

            case ROTATE -> //rotate
                    currentShip.rotate();
            case DOWN -> //down
                    newY--;
            default -> //doh!
                    throw new RuntimeException("Bad movement!");
        }
    }

    /**
     * Put new piece in to play in the Player Ship Board
     */
    public void addNewShip() {
        // commit things the way they are
        player_board.commit(currentShip);
        currentShip = null;

        if(!ships.hasNext()){
           gamePhase += 1;
           return;
        }
        Ship piece = ships.next();

        // Center it up at the top
        int px = (player_board.getWidth() - piece.getWidth())/2;
        int py = (player_board.getHeight() - piece.getHeight())/2;

        int result = setCurrent(piece, px, py);

        if (result > 1) {
            stopGame(); //oops, we lost.
        }
    }


    /**
     * Attempt to set the piece at a given board position
     *
     * @param ship piece to place
     * @param x placement position, x
     * @param y placement position, y
     *
     * @return integer defining if placement is OK or not (see Board.java)
     */
    public int setCurrent(Ship ship, int x, int y) {
        int result = player_board.placePiece(ship, x, y);

        if (result == 0) { // SUCCESS
            this.currentShip = ship;
            this.currentX = x;
            this.currentY = y;
        } else {
            player_board.undo();
        }

        return(result);
    }


    /**
     * Get width
     *
     * @return score of game
     */


    /**
     * Advance the game one tick forward
     * Each tick is associated with a move of some kind!
     * Put the move in play by executing this.
     */
    public void modelTick(MoveType verb, int x, int y, PlayerType type) {

        if (!gameOn) return;

        switch (gamePhase) {
            case 0 -> executeMove(verb);
            case 1 -> executeShot(x, y, type);
        }
    }

    /**
     * Get the best move that is automatically generated by a computer
     * Then execute it.
     */
//    private void computerMove() {
//        MoveType verb = pilot.bestMove(board,currentPiece,currentX,currentY); //which move is best?
//        executeMove(verb);
//    }

    /**
     * Execute a given move.  This will compute the new position of the active ship,
     * set the ship to this location if possible. Place all Players Ships.
     * @param verb the type of move to execute
     */
    private void executeMove(MoveType verb) {
        // Execute that Given move


        if (currentShip != null) {
            if (verb == MoveType.DROP) {
                player_board.placePiece(currentShip, currentX, currentY);
                return;
            }
            player_board.undo();	// remove the piece from its old position
        }
        computeNewPosition(verb);

        // try out the new position (and roll it back if it doesn't work)
        int result;
        if(nextShip != null){
             result = setCurrent(nextShip, newX, newY);
        }
//        int result = setCurrent(nextShip, newX, newY);
        else{
            return;
        }

        boolean failed = (result >= Board.ADD_OUT_BOUNDS);

        // if it didn't work, put it back the way it was
        if (failed) {
            if (currentShip != null) {
                currentShip.rotate();
                player_board.placePiece(currentShip, currentX, currentY);
            }
        }   // Otherwise, add another ship for player board
            else {
                addNewShip();
            }

    }

    private void executeShot(int x, int y, PlayerType type) {
        return;
    }

    /**
     * Start a new game
     */
    public void newGame() {
        this.player_board.newGame();
        this.ai_board.newGame();
        startGame();
    }

    /**
     * Save the current state of the game to a file
     *
     * @param file pointer to file to write to
     */
    public void saveModel(File file) {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
