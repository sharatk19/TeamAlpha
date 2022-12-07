package BattleShip;

public class PlayerMoveCommand implements Command {

    private PlayerInterface player;
    private int x;
    private int y;
    private Move move;



    public PlayerMoveCommand(PlayerInterface player, Move move, int x, int y){
        this.player = player;
        this.move = move;
        this.x = x;
        this.y = y;
    }

    public void execute(){
        this.player.makeMove(this.x, this.y);
        this.player.addMove(move);

    }
}
