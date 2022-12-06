package BattleShip;

import java.util.ArrayList;
import java.util.Random;

public class Search implements Strategy{

    /*
    This function randomly selects a coordinate, then makes sure the coordinate is not already chosen
    in another move.
     */
    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        Random r = new Random();

        int[] result = new int[]{r.nextInt(10), r.nextInt(10)};

        for(Move target: moves){
            if(target.getCoord().equals(result)){
                return executeMove(board, moves);
            }
        }

        return result;
    }
}
