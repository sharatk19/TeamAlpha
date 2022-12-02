package BattleShip;

public class BoardSquare {
    private int state;
    private Ship ship;

    public static final int UNHIT = 0;
    public static final int MISS = 1;
    public static final int HIT = 2;

    public BoardSquare() {
        this.state = UNHIT;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return ship == null;
    }

    public int testShot() {
        if (ship == null) {
            state = 1;
        } else {
            state = 2;
        }

        return state;
    }
}
