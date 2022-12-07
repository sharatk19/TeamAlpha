package BattleShip;

public class Move {
    private int[] coord = new int[2];
    private boolean hit = false;

    public Move(int[] c, boolean h) {
        this.coord = c;
        this.hit = h;
    }

    public int[] getCoord() {
        return coord;
    }

    public boolean isHit() {
        return hit;
    }
}