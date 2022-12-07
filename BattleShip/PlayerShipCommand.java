package BattleShip;

import java.util.ArrayList;

public class PlayerShipCommand implements Command{
    private PlayerInterface player;

    public PlayerShipCommand(PlayerInterface player){
        this.player = player;
    }
    public void execute(){
        this.player.setPlayerShips();
    }
}