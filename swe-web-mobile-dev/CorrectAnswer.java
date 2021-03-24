class CorrectAnswer {
    static String[] qwertyNeighboring = { "qwsz", "vghn", "xdfv", "serfcx", "wsdr", "drtgvc", "ftyhbv", "gyujnb",
            "ujko", "uihkmn", "iolmj", "kop", "njk", "bhjm", "iklp", "ol", "aw", "edft", "awedxz", "rfgy", "yhji",
            "cfgb", "qase", "zsdc", "tghu", "asx" };

    String[][] inputsArray;

    String correctAnswer(String expected, String provided) {

        inputsArray = new String[3][];
        inputsArray[0] = split(clean(provided, true));
        inputsArray[1] = split(clean(expected, true));
        inputsArray[2] = split(clean(expected, false));

        int len = inputsArray[0].length;

        if (inputsArray[1].length != len) {
            return "INCORRECT";
        }

        for (int i = 0; i < len; i++) {
            int checkDiff = checkDiff(inputsArray[0][i], inputsArray[1][i]);

            if (checkDiff == 1) {
                expected = expected.replace(inputsArray[2][i], "[" + inputsArray[2][i] + "]");
            }

            if (checkDiff == 2) {
                return "INCORRECT";
            }
        }

        return expected;
    }

    // toLower = false raha mila tazomina ny casse original (ho an'ilay input
    // fahatelo)
    String clean(String value, boolean toLower) {

        value = value.trim().replaceAll("[^a-zA-Z ]", " ");

        if (toLower) {
            value = value.toLowerCase();
        }

        while (value.contains("  ")) {
            value = value.replaceFirst("  ", " ");
        }

        return value;
    }

    int checkDiff(String pro, String exp) {
        if (pro.equals(exp)) {
            return 0;
        }

        int proLen = pro.length();
        int expLen = exp.length();
        int diff = Math.abs(proLen - expLen);

        if (diff > 1) {
            return 2;
        }

        if (diff == 0) {
            if (qwertyTypo(pro, exp, proLen)) {
                return 1;
            }

            return 2;
        }

        if (lengthDiff(pro, exp, proLen, expLen)) {
            return 1;
        }

        return 2;
    }

    boolean lengthDiff(String pro, String exp, int proLen, int expLen) {

        int diffCount = 0;
        int i = 0;
        int j = 0;

        for (; i < proLen && j < expLen;) {
            char p = pro.charAt(i);
            char e = exp.charAt(j);

            // raha vao tsy mitovy ireo chars roa, dia incrementer-na ny index-izay mot lava
            // kokoa ary incrementer-na ihany koa ny difference
            if (p != e) {
                if (proLen < expLen) {
                    j++;
                } else {
                    i++;
                }
                diffCount++;
                continue;
            }
            i++;
            j++;
        }

        // mila tsy mihoatran'ny roa ny difference ary samy tonga any amin'ny farany
        // daholo ny indexes an'ireo mots roa
        return diffCount < 2
                && (i == proLen && j == expLen || i == proLen - 1 && j == expLen || i == proLen && j == expLen - 1);
    }

    boolean qwertyTypo(String pro, String exp, int proLen) {
        int typorCount = 0;

        for (int i = 0; i < proLen; i++) {
            char p = pro.charAt(i);
            char e = exp.charAt(i);

            if (p != e) {

                // 'a' - 'a' == 0, 'b' - 'a' == 1, etc.
                if (qwertyNeighboring[e - 'a'].contains(String.valueOf(p))) {
                    typorCount++;
                } else {
                    return false;
                }
            }
        }

        return typorCount < 2;
    }

    String[] split(String value) {
        return value.split(" ");
    }
}
