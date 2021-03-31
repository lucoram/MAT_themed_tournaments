import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class EuclidianDistance {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int nbCoords = in.nextInt();

        BigDecimal[] instanceA = new BigDecimal[nbCoords];
        BigDecimal[] instanceB = new BigDecimal[nbCoords];

        for (int i = 0; i < nbCoords; i++) {
            instanceA[i] = BigDecimal.valueOf(in.nextDouble());
        }
        for (int i = 0; i < nbCoords; i++) {
            instanceB[i] = BigDecimal.valueOf(in.nextDouble());
        }

        BigDecimal answer = BigDecimal.ZERO;

        for (int i = 0; i < nbCoords; i++) {
            answer = answer.add(instanceA[i].subtract(instanceB[i]).pow(2));
        }

        answer = answer.sqrt(new MathContext(10));

        // Java 8
        // answer = sqrt(answer, 10);

        System.out.println(formatDecimal(answer));
        in.close();
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

    private static String formatDecimal(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }
}
