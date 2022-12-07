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
        return ship != null;
    }

    public int getState() {return state;}

    public Ship testShot() {
        if (this.state > 0) {
            return null;
        }
        if (ship == null) {
            state = MISS;
        } else {
            state = HIT;
        }

        return ship;
    }
}
