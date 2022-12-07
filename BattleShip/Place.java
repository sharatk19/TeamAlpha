package BattleShip;

import java.util.ArrayList;
import java.util.Random;

public class Place implements Strategy{
    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        Random random = new Random();
        Ship ship = Ship.getShips()[board.getLiveShips().size()];

        if (random.nextBoolean()) {
            ship.rotate();
        }

        boolean canPlace = false;

        while (!canPlace) {
            ship.x = random.nextInt(0,10);
            ship.y = random.nextInt(0,10);

            if (!board.getGrid(ship.getAbsHead()[0], ship.getAbsHead()[1]) &&
                    !board.getGrid(ship.getAbsTail()[0], ship.getAbsTail()[1])) {
                canPlace = true;
            }

            for (Ship s: board.getLiveShips()) {
                if (ship.checkCollision(s, ship.x, ship.y)) {
                    canPlace = false;
                }
            }
        }

        return new int[]{ship.x, ship.y};
    }
}
