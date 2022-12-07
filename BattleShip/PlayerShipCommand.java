package BattleShip;

import java.util.ArrayList;

public class PlayerShipCommand implements Command{
    private PlayerInterface player;

    private ArrayList<ShipSquare> ship;
    private String ship_type;

    public PlayerShipCommand(PlayerInterface player, ArrayList<ShipSquare> ship){
        this.player = player;
        this.ship = ship;
    }
    public void execute(){
        this.player.setPlayerShips(ship, ship_type);
    }
}
