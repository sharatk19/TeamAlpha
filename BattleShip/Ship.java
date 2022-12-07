package BattleShip;

import java.util.*;

public class Ship {
    private String name;
    private ShipSquare[] body; // y and x values that make up the body of the piece.
    private int width;
    private int height;
    private int health;
    private boolean horizontal = true;
    static private Ship[] pieces;	// array of rotations for this piece
    int x;
    int y;


    // Size constants for the standard 5 ships
    public static final HashMap<String, Integer> DEFAULTS;

    static {
        DEFAULTS = new HashMap<>();
        DEFAULTS.put("CARRIER", 5);
        DEFAULTS.put("BATTLESHIP", 4);
        DEFAULTS.put("SUBMARINE", 3);
        DEFAULTS.put("DESTROYER", 3);
        DEFAULTS.put("CRUISER", 2);
    }

    /**
     * Defines a new piece given a ShipSquare[] array that makes up its body.
     * Makes a copy of the input array and the ShipSquare inside it.
     * Note that this constructor should define the piece's "lowestYVals". For each x value
     * in the body of a piece, the lowestYVals will contain the lowest y value for that x in the body.
     * This will become useful when computing where the piece will land on the board!!
     */
    public Ship(String name, int size) {
        this.body = new ShipSquare[size];
        this.name = name;
        this.height = 1;
        this.width = size;
        this.health = size;

        for (int i = 0; i < size; i++) {
            body[i] = new ShipSquare(0, i);
        }
    }

    /**
     * Returns the width of the piece measured in blocks.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the piece measured in blocks.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a pointer to the piece's body. The caller
     * should not modify this array.
     *
     * @return point array that defines piece body
     */
    public ShipSquare[] getBody() {
        return body;
    }

    public String getName() {
        return this.name;
    }

    public boolean hit() {
        this.health -= 1;
        System.out.println("health: " + health);
        return this.health == 0;
    }

    /**
     * Returns true if two pieces are the same --
     * their bodies contain the same points.
     * Interestingly, this is not the same as having exactly the
     * same body arrays, since the points may not be
     * in the same order in the bodies. Used internally to detect
     * if two rotations are effectively the same.
     *
     * @param obj the object to compare to this
     *
     * @return true if objects are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Ship piece)) return false;

        if (!(this.getBody().length == piece.getBody().length)) return false;

        for (ShipSquare point: this.getBody()) {
            if (Arrays.stream(piece.getBody()).noneMatch(p -> p.equals(point))) {
                return false;
            }
        }

        return true;
    }

    /**
     * This is a static method that will return all rotations of
     * each of the 7 standard tetris pieces:
     * STICK, L1, L2, S1, S2, SQUARE, PYRAMID.
     * The next (counterclockwise) rotation can be obtained
     * from each piece with the fastRotation() method.
     * This method will be called by the model to facilitate
     * selection of random pieces to add to the board.
     * The pieces can be easily rotated because the rotations
     * have been precomputed.
     *
     * @return a list of all the rotations for all the given pieces.
     */
    public static Ship[] getShips() {
        ArrayList<Ship> ships = new ArrayList<>();

        for (String key: Ship.DEFAULTS.keySet()){
            ships.add(new Ship(key, Ship.DEFAULTS.get(key)));
        }
        Ship.pieces = ships.toArray(new Ship[0]);

        return Ship.pieces;
    }

    /**
     * Returns a pre-computed piece that is 90 degrees counter-clockwise
     * rotated from the receiver. Fast because the piece is pre-computed.
     * This only works on pieces set up by makeFastRotations(), and otherwise
     * just returns null.
     *
     * @return the next rotation of the given piece
     */
    public void rotate() {
        if (horizontal) {
            for (ShipSquare shipSquare : this.body) {
                shipSquare.y = shipSquare.x;
                shipSquare.x = 0;
            }

            height = this.body.length;
            width = 1;
        } else {
            for (ShipSquare shipSquare : this.body) {
                shipSquare.x = shipSquare.y;
                shipSquare.y = 0;
            }
            width = this.body.length;
            height = 1;
        }

        horizontal = !horizontal;
    }

    /**
     * Print the points within the piece
     *
     * @return a string representation of the piece
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ShipSquare shipSquare : body) {
            str.append(shipSquare.toString());
        }
        return str.toString();
    }

    public int[] getAbsHead() {
        return new int[]{this.body[0].x + this.x, this.body[0].y + y};
    }

    public int[] getAbsTail() {
        return new int[]{this.body[this.body.length-1].x + this.x, this.body[this.body.length-1].y + y};
    }

    private boolean overlap(int[] s1, int[] s2, int[] s3) {
        return s2[0] <= Math.max(s1[0], s3[0]) &&
                s2[0] >= Math.min(s1[0], s3[0]) &&
                s2[1] <= Math.max(s1[1], s3[1]) &&
                s2[1] >= Math.min(s1[1], s3[1]);
    }

    private int cHelper(int[] s1, int[] s2, int[] s3) {
        int result = (s2[1] - s1[1]) * (s3[0] - s2[0]) - (s3[1] - s2[1]) * (s2[0] - s1[0]);
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public boolean checkCollision(Ship ship, int x, int y) {
        this.x = x;
        this.y = y;

        int a = cHelper(this.getAbsHead(), this.getAbsTail(), ship.getAbsHead());
        int b = cHelper(this.getAbsHead(), this.getAbsTail(), ship.getAbsTail());
        int c = cHelper(ship.getAbsHead(), ship.getAbsTail(), this.getAbsHead());
        int d = cHelper(ship.getAbsHead(), ship.getAbsTail(), this.getAbsTail());

        if ((a != b) && (c != d)) return true;

        return ((a == 0) && overlap(this.getAbsHead(), ship.getAbsHead(), this.getAbsTail())) ||
                ((b == 0) && overlap(this.getAbsHead(), ship.getAbsTail(), this.getAbsTail())) ||
                ((c == 0) && overlap(ship.getAbsHead(), this.getAbsHead(), ship.getAbsTail())) ||
                ((d == 0) && overlap(ship.getAbsHead(), this.getAbsTail(), ship.getAbsTail()));
    }
}
