package BattleShip;

import java.util.ArrayList;
import java.util.Random;

public class Search implements Strategy{

    /*
    This function randomly selects a coordinate, then makes sure the coordinate is not already chosen
    in another move.
     */
    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        Random r = new Random();
        int x = 0;
        int y = 0;
        boolean canShoot = false;

        while (!canShoot) {
            x = r.nextInt(0, 10);

            if (x % 2 == 0) {
                y = r.nextInt(0, 5) * 2;
            } else {
                y = r.nextInt(0, 4) * 2 + 1;
            }

            if (board.getBoard()[x][y].getState() == 0) {
                canShoot = true;
            }
        }

        return new int[]{x, y};
    }
}
