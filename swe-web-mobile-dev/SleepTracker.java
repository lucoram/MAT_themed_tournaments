import java.util.Arrays;

class SleepTracker {
    int[] sleepStageDurations;
    String[] result;
    int sleepScore;

    String[] sleepTracker(int[] heartRateAvgPerMinute) {

        System.out.println("Input : " + Arrays.toString(heartRateAvgPerMinute));

        updateSleepStageDudations(heartRateAvgPerMinute);
        updateResult();

        return result;
    }

    void updateResult() {

        int totalSleep = sleepStageDurations[1] + sleepStageDurations[2] + sleepStageDurations[3];

        result = new String[6];
        result[0] = minutesToHHMM(sleepStageDurations[0]);
        result[1] = minutesToHHMM(sleepStageDurations[1]);
        result[2] = minutesToHHMM(sleepStageDurations[2]);
        result[3] = minutesToHHMM(sleepStageDurations[3]);
        result[4] = minutesToHHMM(totalSleep);
        result[5] = calcSleepScore(totalSleep) + ":" + calcSleepQuality();
    }

    int calcSleepScore(int totalSleep) {
        if (totalSleep <= 120 || totalSleep >= 600) {
            sleepScore = 40;
        } else if (totalSleep <= 360) {
            sleepScore = (((totalSleep - 120) * 20) / 240) + 40;
        } else if (totalSleep >= 540) {
            sleepScore = (((600 - totalSleep) * 20) / 60) + 40;
        } else if (totalSleep <= 480) {
            sleepScore = (((totalSleep - 360) * 40) / 120) + 60;
        } else if (totalSleep >= 480) {
            sleepScore = (((540 - totalSleep) * 40) / 60) + 60;
        }

        return sleepScore;
    }

    String calcSleepQuality() {
        if (sleepScore < 60) {
            return "poor";
        }
        if (sleepScore < 80) {
            return "fair";
        }
        if (sleepScore < 95) {
            return "good";
        }

        return "excellent";
    }

    String minutesToHHMM(int minutes) {
        int hour = minutes / 60;
        minutes -= (hour * 60);
        return zeroPad(hour) + "h" + zeroPad(minutes) + "m";
    }

    String zeroPad(int value) {
        return String.format("%02d", value);
    }

    void updateSleepStageDudations(int[] heartRateAvgPerMinute) {
        sleepStageDurations = new int[4];

        for (int bpm : heartRateAvgPerMinute) {
            if (bpm < 50) {
                sleepStageDurations[3]++;
                continue;
            }

            if (bpm < 60) {
                sleepStageDurations[2]++;
                continue;
            }

            if (bpm < 90) {
                sleepStageDurations[1]++;
                continue;
            }

            sleepStageDurations[0]++;
        }
    }
}
