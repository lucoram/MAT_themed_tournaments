import java.util.Scanner;

public class AbsolutelySimplestBackpropagation {

    static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        Double input = scan.nextDouble();
        Double expectedOutput = scan.nextDouble();
        int nbNeurons = scan.nextInt();

        Double[] weights = new Double[nbNeurons];
        Double[] inputs = new Double[nbNeurons];
        Double[] outputs = new Double[nbNeurons];

        inputs[0] = input;

        for (int i = 0; i < nbNeurons; i++) {
            weights[i] = scan.nextDouble();
        }

        Double learningRate = 0.1;
        Double currentOutput = runFeedForward(inputs, outputs, weights, nbNeurons);

        while (Math.abs(expectedOutput - currentOutput) > 0.00001) {
            runBackProp(currentOutput, expectedOutput, weights, inputs, outputs, nbNeurons, learningRate);

            StringBuilder lineBuilder = new StringBuilder();

            for (Double weight : weights) {
                lineBuilder.append(formatLine(weight)).append(" ");
            }

            System.out.println(lineBuilder.toString().trim());

            currentOutput = runFeedForward(inputs, outputs, weights, nbNeurons);
        }
    }

    private static void runBackProp(Double currentOutput, Double expectedOutput, Double[] weights, Double[] inputs,
            Double[] outputs, int nbNeurons, Double learningRate) {
        int lastIndex = nbNeurons - 1;
        Double mse = 2 * (currentOutput - expectedOutput); // Mean Squared Error

        Double cumProdWeight = 1d;

        for (int i = lastIndex; i >= 0; i--) {
            weights[i] = weights[i] - learningRate * inputs[i] * cumProdWeight * mse;
            cumProdWeight *= weights[i];
        }
    }

    private static Double runFeedForward(Double[] inputs, Double[] outputs, Double[] weights, int nbNeurons) {

        outputs[0] = inputs[0] * weights[0];

        for (int i = 1; i < nbNeurons; i++) {
            inputs[i] = outputs[i - 1];
            outputs[i] = inputs[i] * weights[i];
        }

        return outputs[nbNeurons - 1];
    }

    private static String formatLine(Double value) {
        return String.valueOf(Math.round(value * 100000d) / 100000d);
    }
}
