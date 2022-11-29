package BattleShip;

import java.util.ArrayList;

public interface Strategy {
    public int[] executeMove(Board board, ArrayList<Move> moves);
}
