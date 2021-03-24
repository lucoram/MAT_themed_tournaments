import java.io.IOException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class FollowAStar {

    static int height;
    static int width;
    static char[][] grid;
    static int[] initialPos;
    static int[] playerMoves;
    static int iterCount;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        long start = System.currentTimeMillis();

        height = scan.nextInt();
        width = scan.nextInt();

        scan.nextLine();

        grid = new char[height][];

        for (int r = 0; r < height; r++) {
            grid[r] = scan.nextLine().toCharArray();
        }

        initialPos = new int[] { scan.nextInt(), scan.nextInt() };
        int nbMoves = scan.nextInt();

        for (int i = 0; i < nbMoves; i++) {
            playerMoves = new int[] { scan.nextInt(), scan.nextInt() };
            System.out.println(chooseMove());
        }

        long end = System.currentTimeMillis();

        System.out.println((end - start) + " ms");
        System.out.println("iterations : " + iterCount);
    }

    static int[][] diffs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    private static String chooseMove() {
        CellNode source = new CellNode(initialPos[0], initialPos[1]);
        source.g = 0;

        CellNode dest = new CellNode(playerMoves[0], playerMoves[1]);

        Set<CellNode> visited = new HashSet<>();
        visited.add(source);

        Queue<CellNode> aStarPQueue = new PriorityQueue<>();
        aStarPQueue.add(source);

        while (!aStarPQueue.isEmpty()) {
            CellNode current = aStarPQueue.poll();

            if (current.equals(dest)) {
                dest.pred = current.pred;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int r = current.r + diffs[i][0];
                int c = current.c + diffs[i][1];

                if (isSafe(r, c)) {
                    CellNode nei = new CellNode(r, c);
                    nei.g = current.g + 1;
                    nei.h = calcManhattanDist(r, c);
                    nei.pred = current;

                    iterCount++;

                    if (!visited.contains(nei)) {
                        visited.add(nei);
                        aStarPQueue.add(nei);
                    }
                }
            }
        }

        boolean move = !dest.pred.equals(source);

        while (dest.pred != null && dest.pred.pred != null) {
            dest = dest.pred;
        }

        if (move) {
            initialPos[0] = dest.r;
            initialPos[1] = dest.c;
        }

        return move ? "move to " + dest.toString() : "stay at " + source.toString();
    }

    private static int calcManhattanDist(int r, int c) {
        return Math.abs(r - playerMoves[0]) + Math.abs(c - playerMoves[1]);
    }

    private static boolean isSafe(int r, int c) {
        return r >= 0 && c >= 0 && r < height && c < width && grid[r][c] != '#';
    }
}

class CellNode implements Comparable<CellNode> {
    String id;
    int r;
    int c;
    int g;
    int h;
    CellNode pred;

    CellNode(int r, int c) {
        this.r = r;
        this.c = c;
        this.id = r + "-" + c;
    }

    @Override
    public String toString() {
        return r + " " + c;
    }

    @Override
    public boolean equals(Object obj) {
        return ((CellNode) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getF() {
        return g + h;
    }

    @Override
    public int compareTo(CellNode o) {
        int f = getF();
        int of = o.getF();

        if (f == of) {
            if (r == o.r) {
                return c - o.c;
            }
            return r - o.r;
        }

        return f - of;
    }
}
