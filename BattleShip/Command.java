package BattleShip;

public interface Command {

    public default void execute(){
        return;
    }
}
