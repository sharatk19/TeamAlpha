package BattleShip;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int width; //board height and width
    private int height;
    private BoardSquare[][] board;
    protected boolean[][] viewGrid; //board grid

    private ArrayList<Ship> liveShips;
    private ArrayList<Ship> deadShips;
    boolean committed;
    private boolean[][] backupGrid; //to back up your grid
    public static final int ADD_OK = 0;
    public static final int ADD_OUT_BOUNDS = 2;
    public static final int ADD_BAD = 3;

    /**
     * Constructor for an empty board of the given width and height measured in blocks.
     *
     * @param aWidth    width
     * @param aHeight    height
     */
    public Board(int aWidth, int aHeight) {
        width = aWidth;
        height = aHeight;

        newGame();
    }

    /**
     * Helper to fill new game grid with empty values
     */
    public void newGame() {
        viewGrid = new boolean[width][height];
        backupGrid = new boolean[width][height];
        board = new BoardSquare[width][height];
        liveShips = new ArrayList<>();
        deadShips = new ArrayList<>();

        for (BoardSquare[] col: board) {
            for (int i = 0; i < height; i++) {
                col[i] = new BoardSquare();
            }
        }

        for (boolean[] booleans : viewGrid) {
            Arrays.fill(booleans, false);
        }
        backupGrid();
        committed = true;
    }

    /**
     * Getter for board width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for board height
     */
    public int getHeight() {
        return height;
    }

    public ArrayList<Ship> getLiveShips() {
        return liveShips;
    }

    public ArrayList<Ship> getDeadShips() {
        return deadShips;
    }

    public int testShot(int x, int y) {
        if (this.board[x][y].getState() != 0) {
            return 3;
        }

        Ship ship = this.board[x][y].testShot();

        if (ship != null) {
            boolean destroyed = ship.hit();
            if (destroyed) {
                liveShips.remove(ship);
                deadShips.add(ship);
                return 2;
            }
            return 1;
        } else {
            return 0;
        }
    }

    public boolean[][] getViewGrid() {
        return viewGrid;
    }

    public BoardSquare[][] getBoard() {
        return board;
    }

    /**
     * Returns true if the given block is filled in the board. Blocks outside of the
     * valid width/height area always return true (as we can't place anything there).
     *
     * @param x grid position, x
     * @param y grid position, y
     *
     * @return true if the given block at x,y is filled, else false
     */
    public boolean getGrid(int x, int y) {
        return x >= width || x < 0 || y >= height || y < 0;
    }

    /**
     * Attempts to add the body of a piece to the board. Copies the piece blocks into the board grid.
     * Returns ADD_OK for a regular placement, or ADD_ROW_FILLED
     * for a regular placement that causes at least one row to be filled.
     *
     * Error cases:
     * A placement may fail in two ways. First, if part of the piece may fall out
     * of bounds of the board, ADD_OUT_BOUNDS is returned.
     * Or the placement may collide with existing blocks in the grid
     * in which case ADD_BAD is returned.
     * In both error cases, the board may be left in an invalid
     * state. The client can use undo(), to recover the valid, pre-place state.
     *
     * @param ship piece to place
     * @param x placement position, x
     * @param y placement position, y
     *
     * @return static int that defines result of placement
     */
    public int placePiece(Ship ship, int x, int y) {
        committed = false;
        ShipSquare[] body = ship.getBody();

        if (getGrid(body[0].x + x, body[0].y + y) ||
                getGrid(body[body.length - 1].x + x, body[body.length - 1].y + y)) {
            return ADD_OUT_BOUNDS;
        }

        for (ShipSquare square: ship.getBody()) {
            viewGrid[square.x + x][square.y + y] = true;
        }

        return ADD_OK;
    }

    public int placeSquare(ShipSquare square) {
        committed = false;

        if(getGrid(square.x, square.y)){
            return ADD_OUT_BOUNDS;
        }
        else{
            viewGrid[square.x][square.y] = true;
            return ADD_OK;
        }
//        ShipSquare[] body = ship.getBody();
//
//        if (getGrid(body[0].x + x, body[0].y + y) ||
//                getGrid(body[body.length - 1].x + x, body[body.length - 1].y + y)) {
//            return ADD_OUT_BOUNDS;
//        }
//
//        for (ShipSquare square: ship.getBody()) {
//            viewGrid[square.x + x][square.y + y] = true;
//        }
//
//        return ADD_OK;
    }

    /**
     * Reverts the board to its state before up to one call to placePiece() and one to clearRows();
     * If the conditions for undo() are not met, such as calling undo() twice in a row, then the second undo() does nothing.
     * See the overview docs.
     */
    public void undo() {
        if (committed) return;  //a committed board cannot be undone!

        if (backupGrid == null) throw new RuntimeException("No source for backup!");  //a board with no backup source cannot be undone!

        //make a copy!!
        for (int i = 0; i < backupGrid.length; i++) {
            System.arraycopy(backupGrid[i], 0, viewGrid[i], 0, backupGrid[i].length);
        }

        committed = true;
    }

    /**
     * Copy the backup grid into the grid that defines the board (to support undo)
     */
    private void backupGrid() {
        for (int i = 0; i < viewGrid.length; i++) {
            System.arraycopy(viewGrid[i], 0, backupGrid[i], 0, viewGrid[i].length);
        }
    }

    /**
     * Puts the board in the 'committed' state.
     *
     * @return 0 for good commit, 1 for bad commit
     */
    public int commit(Ship ship) {
        if (ship != null) {
            for (Ship s: liveShips) {
                if (ship.checkCollision(s, ship.x, ship.y)) {
                    return 1;
                }
            }

            for (ShipSquare square: ship.getBody()) {
                board[square.x + ship.x][square.y + ship.y].setShip(ship);
            }

            liveShips.add(ship);
        }


        backupGrid();
        committed = true;
        return 0;
    }

    /**
     * Print the board
     *
     * @return a string representation of the board (useful for debugging)
     */
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int y = height-1; y>=0; y--) {
            buff.append('|');
            for (int x=0; x<width; x++) {
                if (getGrid(x,y)) buff.append('+');
                else buff.append(' ');
            }
            buff.append("|\n");
        }
        for (int x=0; x<width+2; x++) buff.append('-');
        return(buff.toString());
    }
}
