class GrossFromNetPay {
    int grossFromNetSalary(int[][] taxBrackets, int goalNetSalary) {
        int taxBracketsLength = taxBrackets.length;
        int grossFromNetSalary = 0; // valiny

        // avadika range aloha ilay taxBrackets
        for (int i = taxBracketsLength - 1; i > 0; i--) {
            taxBrackets[i][0] -= taxBrackets[i - 1][0];
        }

        for (int i = 0; i < taxBracketsLength && goalNetSalary > 0; i++) {
            // alaina ny pourcentage (taxe) ao anatin'ilay range
            int percentage = (taxBrackets[i][0] * taxBrackets[i][1]) / 100;

            // tant que mbola mahenika ny range ilay salaire net
            if (goalNetSalary + percentage >= taxBrackets[i][0]) {
                // tonga dia miditra avy hatrany anaty valiny ilay range
                grossFromNetSalary += taxBrackets[i][0];
                // alaina ny salaire net ao amin'ilay range
                int rangeNetSalary = taxBrackets[i][0] - percentage;
                // ahena ny salaire net final
                goalNetSalary -= rangeNetSalary;

            } else {
                // rehefa tsy mahenika ny range intsony ilay salaire net vao mampiasa ilay
                // formule
                grossFromNetSalary += (100 * goalNetSalary) / (100 - taxBrackets[i][1]);
                goalNetSalary = 0;
            }
        }

        return grossFromNetSalary;
    }
}
