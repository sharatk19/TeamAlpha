package BattleShip;

import java.util.ArrayList;
import java.util.Random;

public class Search implements Strategy{
    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        Random r = new Random();

        return new int[]{r.nextInt(10), r.nextInt(10)};
    }
}
