package BattleShip;

public class PlayerMoveCommand implements Command {

    private PlayerInterface player;
    private int x;
    private int y;
    private Move move;
    private Ship[] ships;



    public PlayerMoveCommand(PlayerInterface player, Move move, Ship[] ships, int x, int y){
        this.player = player;
        this.move = move;
        this.x = x;
        this.y = y;
        this.ships = ships;
    }

    public void execute(){
        this.player.makeMove(this.ships, this.x, this.y);
        this.player.addMove(move);

    }
}
