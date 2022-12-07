package BattleShip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class AiPlayer implements Serializable{
    ArrayList<Move> moves;
    boolean search;

    public AiPlayer() {
        moves = new ArrayList<>();
        search = true;
    }


    private Strategy selectStrategy(Board board, int gameState) {
        if (gameState == 0) {
            return new Place();
        }
        if (search) {
            return new Search();
        }

        return new Destroy();
    }

    public int[] executeStrategy(Board board, int gameState) {
        Move move = new Move(selectStrategy(board, gameState).executeMove(board, moves), false);
        if (gameState != 0) {
            moves.add(move);
        }
        return move.getCoord();
    }

    public void update(boolean hit, boolean destroyed) {
        moves.get(moves.size() - 1).setHit(hit);

        if (destroyed) {
            search = true;
        }
        else if (hit && search) {
            search = false;
            moves = (ArrayList<Move>) Collections.singletonList(moves.get(moves.size() - 1));
        }
    }
}
