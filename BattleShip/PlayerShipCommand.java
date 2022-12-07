package BattleShip;

public class PlayerShipCommand implements Command{
    private PlayerInterface player;

    private Ship ship;

    public PlayerShipCommand(PlayerInterface player, Ship ship){
        this.player = player;
        this.ship = ship;
    }
    public void execute(){
        this.player.setPlayerShips(ship);
    }
}
