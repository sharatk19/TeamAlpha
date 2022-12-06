package BattleShip.Player;

public interface Command {

    public default void execute(){
        return;
    }
}
