package BattleShip;

public class PlayerMoveCommand implements Command {

    private PlayerInterface player;
    private int x;
    private int y;



    public PlayerMoveCommand(PlayerInterface player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public void execute(){
        this.player.makeMove(this.x, this.y);
    }
}
