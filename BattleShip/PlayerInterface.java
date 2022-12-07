package BattleShip;

import BattleShip.Board;
import BattleShip.Move;
import BattleShip.Ship;
import BattleShip.ShipSquare;

import java.util.ArrayList;

public class PlayerInterface {

    public ArrayList<Move> moves;
    public Board player_board;


    public PlayerInterface(Board player_board){
        this.moves = new ArrayList<>();
        this.player_board = player_board;
    }

    public void makeMove(Ship[] ships, int x, int y){
        // Takes in Enemy Ships, and checks if Any Squares Hit



        // Make a move

        // Given and x and y, hit that position so that
        // this.playerboard.testplayershot()

        // or


        for(Ship ship: ships){
            for(ShipSquare square: ship.getBody()){
                if(square.x == x && square.y == y){
                    this.player_board.testShot(x, y);
                    // Update the board at that square to True
                }
            }
        }

        return;
    }

    public void addMove(Move move){
        this.moves.add(move);
    }
    public void setPlayerShips(ArrayList<ShipSquare> shipsquares, String name){
        Ship ship = new Ship(name, shipsquares.size());
        ship.changeBody(shipsquares);

        for(ShipSquare squares: ship.getBody()){
            this.player_board.placeSquare(squares);
        }
    }
}
