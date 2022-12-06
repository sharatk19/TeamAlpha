package BattleShip.Player;

import BattleShip.Board;
import BattleShip.Move;
import BattleShip.Ship;
import BattleShip.ShipSquare;

import java.util.ArrayList;

public class PlayerInterface {

    public ArrayList<Move> moves;
    public Board[] player_board;


    public PlayerInterface(Board[] player_board){
        this.moves = new ArrayList<>();
        this.player_board = player_board;
    }

    public void makeMove(int x, int y){

        // Make a move

        // Given and x and y, hit that position so that
        // this.playerboard.testplayershot()

        return;
    }

    public void addMove(Move move){
        this.moves.add(move);
    }
    public void setPlayerShips(ArrayList<Ship> ships){
        for(Ship ship: ships){
            for(ShipSquare square: ship.getBody()){
                // call place piece on board but change call to ShipSquare instead of Ship
            }
        }
    }
}
