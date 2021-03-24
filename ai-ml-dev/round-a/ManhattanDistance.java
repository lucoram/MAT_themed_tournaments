class ManhattanDistance {

    private static String[][] calcManhattanDistance(String[] grid) {
        int height = grid.length;
        int width = grid[0].length();

        int[] destCoords = { 0, 0 };

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                char curr = grid[r].charAt(c);
                if (curr == 'D') {
                    destCoords[0] = r;
                    destCoords[1] = c;
                }
            }
        }

        String[][] result = new String[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int h = -1;

                if (grid[r].charAt(c) != '#') {
                    h = manhattanDist(new int[] { r, c }, destCoords);
                }

                result[r][c] = String.valueOf(h);
            }
        }

        return result;
    }

    private static int manhattanDist(int[] currentCoords, int[] refCoords) {
        int dy = Math.abs(currentCoords[0] - refCoords[0]);
        int dx = Math.abs(currentCoords[1] - refCoords[1]);

        return dx + dy;
    }
}
