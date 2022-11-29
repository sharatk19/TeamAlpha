package BattleShip;

import java.io.Serializable;
import java.util.ArrayList;

public class AiPlayer implements Serializable{
    ArrayList<Move> moves = new ArrayList<>();
    boolean search = true;

    private Strategy selectStrategy(Board board) {
        return new Search();
    }

    private int[] executeStrategy(Board board) {
        return selectStrategy(board).executeMove(board, moves);
    }
}
