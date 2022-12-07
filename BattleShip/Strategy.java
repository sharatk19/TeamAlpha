package BattleShip;

import java.util.ArrayList;

public interface Strategy {
    int[] executeMove(Board board, ArrayList<Move> moves);
}
