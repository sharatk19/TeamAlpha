package BattleShip;

import java.util.*;

public class Destroy implements Strategy{

    /*
    The function finds which of the previous moves have hit enemy ship. Then sends the coords and other information to
    findTarget helper method, which chooses where to attack second.
     */

    @Override
    public int[] executeMove(Board board, ArrayList<Move> moves) {
        Random random = new Random();

        int[] origin = moves.get(moves.size() - 1).getCoord();
        Ship[] liveShips = board.getLiveShips().toArray(new Ship[0]);
        Ship[] deadShips = board.getDeadShips().toArray(new Ship[0]);
        Integer[] liveSizes = new Integer[liveShips.length];
        int maxSize;
        ArrayList<int[]> choices = new ArrayList<>();
        Ship testShip = new Ship("test", 1);
        int[][] defaults = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};


        for (int i = 0; i < liveSizes.length; i++) {
            liveSizes[i] = liveShips[i].getBody().length;
        }

        maxSize = Collections.max(Arrays.asList(liveSizes));

        if (moves.size() != 1) {
            for (Move move: moves.subList(1, moves.size())) {
                if (move.isHit()) {
                    if (move.getCoord()[0] == origin[0]) {
                        defaults = new int[][]{{0, 1}, {0, -1}};
                    } else {
                        defaults = new int[][]{{1, 0}, {-1, 0}};
                    }
                }
            }
        }

        for (int[] aDefault : defaults) {
            int[] curr = new int[]{origin[0] + aDefault[0], origin[1] + aDefault[1]};

            if (board.getBoard()[curr[0]][curr[1]].getState() != 1) {
                while (true) {
                    testShip.x = curr[0];
                    testShip.y = curr[1];

                    for (Ship s : deadShips) {
                        if (testShip.checkCollision(s, testShip.x, testShip.y)) {
                            break;
                        }
                    }

                    if (board.getGrid(curr[0], curr[1])) {
                        break;
                    }

                    if (Math.abs(origin[0] - curr[0]) > maxSize || Math.abs(origin[1] - curr[1]) > maxSize) {
                        break;
                    }

                    if (board.getBoard()[curr[0]][curr[1]].getState() == 0) {
                        choices.add(curr);
                        break;
                    }

                    curr[0] += aDefault[0];
                    curr[1] += aDefault[1];
                }
            }

        }

        return choices.get(random.nextInt(0, choices.size()));
    }
}
