package BattleShip;

public class PlayerCommandManager {

    private Command currentCmd; //current command

    /**
     * Set current command
     *
     * @param cmd current command
     */
    public void setCommand(Command cmd) {
        currentCmd = cmd;
    }

    /**
     * Execute current command
     */
    public void playerCommand_execution() {
        this.currentCmd.execute();
    }
}
