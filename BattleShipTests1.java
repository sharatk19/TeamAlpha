import BattleShip.*;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipTests1 {

    /**
     * Test Cases for the Player Mechnics
     */

    @Test
    void testCommandManagerForPlayerCommand() {
        BattleShipModel model = new BattleShipModel();
        PlayerInterface new_playerInterface = new PlayerInterface(model);
        PlayerCommandManager playerCommands = new PlayerCommandManager();
        PlayerShipCommand playerShipCommand = new PlayerShipCommand(new_playerInterface);
        playerCommands.setCommand(playerShipCommand);

        PlayerShipCommand cmd = new PlayerShipCommand(new PlayerInterface(model));
        assertEquals(playerCommands.get_cmd() instanceof PlayerShipCommand, cmd instanceof PlayerShipCommand);
    }
    @Test
    void testCommandManagerForPlayerMoveCommand() {
        BattleShipModel model = new BattleShipModel();
        PlayerInterface new_playerInterface = new PlayerInterface(model);
        PlayerCommandManager playerCommands = new PlayerCommandManager();
        PlayerMoveCommand playerShipCommand = new PlayerMoveCommand(new_playerInterface, 0, 0);
        playerCommands.setCommand(playerShipCommand);

        PlayerMoveCommand cmd = new PlayerMoveCommand(new PlayerInterface(model), 1, 1);
        assertEquals(playerCommands.get_cmd() instanceof PlayerMoveCommand, cmd instanceof PlayerMoveCommand);
    }

    @Test
    void testSetShips() {
        BattleShipModel model = new BattleShipModel();
        PlayerInterface new_playerInterface = new PlayerInterface(model);
        new_playerInterface.setPlayerShips();
        int ships = model.get_ai_Board().getLiveShips().size();
        int result = model.setCurrent(new Ship("Cruiser", 3), 3, 3);


        assertEquals(0, ships);
        assertEquals(0, result);

        model.placeShip();
        assertEquals( "[(0,0)(0,1)(0,2)]" , model.get_player_Board().getLiveShips().toString());

    }
}
