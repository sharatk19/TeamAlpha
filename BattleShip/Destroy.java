package BattleShip;

import java.util.ArrayList;

public class Destroy implements Strategy{

    private int recursionNumber= 0;


    /*
    The function finds which of the previous moves have hit enemy ship. Then sends the coords and other information to
    findTarget helper method, which chooses where to attack second.
     */
    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        for(Move target: moves){
            if(target.isHit()){
                return findTarget(board, moves, target);
            }
        }
        throw new IllegalArgumentException();
    }

    /*
    The function searches another hit move around the target. If it finds, it starts seaching linearly.
     */
    private int[] findTarget(Board board, ArrayList<Move> moves, Move target){
        //TODO
        return new int[0];
    }
}
