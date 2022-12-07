package BattleShip;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BattleShipModel {

    public static final int WIDTH = 10; //size of the board in blocks
    public static final int HEIGHT = 10; //height of the board in blocks

    private AiPlayer comp;
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
        comp = new AiPlayer();
        player_board = new Board(WIDTH, HEIGHT);
        ai_board = new Board(WIDTH, HEIGHT);
        ships = Arrays.stream(Ship.getShips()).iterator();; //initialize board and pieces
        gameOn = false;
    }


    /**
     * Start new game
     */
    public void startGame() { //start game
        // Set Up Player Ships onto Grid
        gamePhase = 0;
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
        for (Ship ship: Ship.getShips()) {
            System.out.println(ship.getName());
            int[] coords = comp.executeStrategy(ai_board, gamePhase);
            System.out.println(Arrays.toString(coords));
            ai_board.placePiece(ship, coords[0], coords[1]);
            ai_board.commit(ship);
        }

        System.out.println(Arrays.deepToString(ai_board.getViewGrid()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
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
        return HEIGHT;
    }

    /**
     * Compute New Position of Ship in play based on move type to Place ship
     *
     * @param verb type of move to account for when placing shi[
     */
//    public void computeNewPosition(int x, int y) {
//        newX = currentX;
//        newY = currentY;
//
//        // Make changes based on the verb
//        switch (verb) {
//            case LEFT -> newX = x;
//            //move left
//
//            case RIGHT -> newX = y;
//            //move right
//
//            case ROTATE -> //rotate
//                    currentShip.rotate();
//            case DOWN -> //down
//                    newY--;
//            default -> //doh!
//                    throw new RuntimeException("Bad movement!");
//        }
//    }

    /**
     * Put new piece in to play in the Player Ship Board
     */
    public void addNewShip() {
        // commit things the way they are
        int result = player_board.commit(currentShip);

        if (result == 1) return;

        currentShip = null;

        if(!ships.hasNext()){
           gamePhase += 1;
           return;
        }
        Ship piece = ships.next();

        // Center it up at the top
        int px = (player_board.getWidth() - piece.getWidth())/2;
        int py = (player_board.getHeight() - piece.getHeight())/2;

        setCurrent(piece, px, py);
    }

    public void rotateCurrent() {
        if (currentShip != null) {
            currentShip.rotate();
            System.out.println(currentShip);
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
    public void modelTick(int x, int y, PlayerType type) {

        if (!gameOn) return;

//        switch (gamePhase) {
//            case 0 -> placeShip(x, y);
//            case 1 -> executeShot(x, y, PlayerType.HUMAN);
//        }
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
    public void hoverMove(int x, int y) {
        // Execute that Given move

        if (currentShip != null) {
            player_board.undo();	// remove the piece from its old position
            currentShip.x = x;
            currentShip.y = y;
            setCurrent(currentShip, x, y);
        }
    }

    public void placeShip() {
        if (gamePhase == 0) {
            addNewShip();
        }
    }

    public void executeShot(int x, int y) {
        if (gamePhase != 1) {return;}

        int result = ai_board.testShot(x, y);

        if (result != 3) {
            int[] coords = comp.executeStrategy(player_board, gamePhase);

            result = player_board.testShot(coords[0], coords[1]);

            comp.update(result == 1 || result == 2, result == 2);
        }
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
