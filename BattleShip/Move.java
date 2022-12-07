package BattleShip;

import java.util.Arrays;

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

    public void setHit(boolean h) {
        hit = h;
    }

    public boolean isHit() {
        return hit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Move move)) return false;
        return(Arrays.equals(coord, move.getCoord()));
    }
}
