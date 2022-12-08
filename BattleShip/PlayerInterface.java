package BattleShip;

import BattleShip.Board;
import BattleShip.Move;
import BattleShip.Ship;
import BattleShip.ShipSquare;

import java.util.ArrayList;

public class PlayerInterface {

    public ArrayList<Move> moves;
    public BattleShipModel model;


    public PlayerInterface(BattleShipModel m){
        this.model = m;
    }

    public void makeMove(int x, int y){
        // Takes in Enemy Ships, and checks if Any Squares Hit

        this.model.executeShot(x, y);
    }

    public void setPlayerShips(){
        this.model.placeShip();
    }
}
