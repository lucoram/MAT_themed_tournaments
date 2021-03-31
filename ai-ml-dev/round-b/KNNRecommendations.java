
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Scanner;

public class KNNRecommendations {
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);

        int d = scan.nextInt();
        int a = scan.nextInt();
        int k = scan.nextInt();
        int i = scan.nextInt();

        int[][] dataSet = new int[d][a];

        for (int j = 0; j < d; j++) {
            for (int l = 0; l < a; l++) {
                dataSet[j][l] = scan.nextInt();
            }
        }

        StringBuilder resBuilder = new StringBuilder();

        for (int index : knnRecommendations(dataSet, i, k)) {
            resBuilder.append(" ").append(index);
        }

        resBuilder.deleteCharAt(0);
        System.out.println(resBuilder.toString());

        scan.close();
    }

    private static int[] knnRecommendations(int[][] dataSet, int poiId, int k) {
        Entry[] dataSetEntries = buildEntries(dataSet);
        Entry pointOfinterest = dataSetEntries[poiId];

        pointOfinterest.distFromSource = BigDecimal.valueOf(Double.MAX_VALUE);

        for (Entry entry : dataSetEntries) {
            if (entry.equals(pointOfinterest)) {
                continue;
            }
            calcEuclidianDist(entry, pointOfinterest);
        }

        Arrays.sort(dataSetEntries);

        int[] kNearestNeighbors = new int[k];

        Arrays.fill(kNearestNeighbors, -1);

        for (int i = 0; i < k; i++) {
            kNearestNeighbors[i] = dataSetEntries[i].id;
        }

        return kNearestNeighbors;
    }

    private static void calcEuclidianDist(Entry entry, Entry pointOfinterest) {

        BigDecimal distance = BigDecimal.ZERO;

        for (int i = 0; i < pointOfinterest.data.length; i++) {
            distance = distance.add(squaredAbs(entry.data[i], pointOfinterest.data[i]));
        }

        entry.distFromSource = distance.sqrt(new MathContext(10));

        // Java 8
        // entry.distFromSource = sqrt(distance, 10);
    }

    private static BigDecimal squaredAbs(int x1, int x2) {
        BigDecimal abs = BigDecimal.valueOf(Math.abs(x1 - x2));
        return abs.pow(2);
    }

    private static Entry[] buildEntries(int[][] dataSet) {

        int n = dataSet.length;
        Entry[] dataSetEntries = new Entry[n];

        for (int i = 0; i < n; i++) {
            dataSetEntries[i] = new Entry(i, dataSet[i]);
        }

        return dataSetEntries;
    }

    // Java 8
    /*
    private static BigDecimal sqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, RoundingMode.HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(BigDecimal.valueOf(2), SCALE, RoundingMode.HALF_UP);

        }
        return x1;
    }
    */
}

class Entry implements Comparable<Entry> {
    int id;
    int[] data;
    BigDecimal distFromSource;

    Entry(int id, int[] data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Entry) obj).id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Entry o) {
        int comp = distFromSource.compareTo(o.distFromSource);

        if (comp == 0) {
            return id - o.id;
        }

        return comp;
    }
}
