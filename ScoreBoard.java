package AI_ML_dev_contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {

    public static void main(String[] args) {
        List<Contestant> allContestants = new ArrayList<>();

        allContestants.add(buildContestant("rado_niain_r", "463-2:48:59"));
        allContestants.add(buildContestant("valdo_n", "325-2:09:51"));
        allContestants.add(buildContestant("ramamj", "300-0:57:22"));
        allContestants.add(buildContestant("ainaraza", "1307-2:56:44"));
        allContestants.add(buildContestant("andry_henintsoa", "1200-2:24:32"));
        allContestants.add(buildContestant("aintenaina", "600-1:47:22"));
        allContestants.add(buildContestant("afmika", "600-2:59:08"));
        allContestants.add(buildContestant("ratife", "483-2:58:16"));
        allContestants.add(buildContestant("teboka_roa", "300-1:03:55"));
        allContestants.add(buildContestant("fanantenan_1", "250-0:39:46"));
        allContestants.add(buildContestant("aryna369", "200-0:10:31"));
        allContestants.add(buildContestant("kimi_no_a", "130-0:52:13"));
        allContestants.add(buildContestant("tiantsoa_r1", "110-0:43:02"));
        allContestants.add(buildContestant("sedera-tax", "25-0:18:21"));

        Collections.sort(allContestants);

        printScoreBoard(allContestants);
    }

    private static Contestant buildContestant(String username, String... contestResults) {
        Contestant newContestant = new Contestant(username);

        for (String result : contestResults) {
            String[] parts = result.split("-");
            newContestant.addContestResult(new ContestResult(Integer.parseInt(parts[0]), convertTimeToSecs(parts[1])));
        }

        return newContestant;
    }

    private static void printScoreBoard(List<Contestant> allContestants) {

        int rank = 1;

        for (Contestant contestant : allContestants) {
            System.out.println(rank + " : " + contestant.toString());
            rank++;
        }
    }

    private static String zeroPad(int value) {
        return String.format("%02d", value);
    }

    private static int convertTimeToSecs(String timeTaken) {
        String[] parts = timeTaken.split(":");
        int timeInSecs = Integer.parseInt(parts[2]);
        timeInSecs += Integer.parseInt(parts[1]) * 60;
        timeInSecs += Integer.parseInt(parts[0]) * 3600;

        return timeInSecs;
    }

    public static String formatSecsToTime(int timeTakenInSecs) {
        int hours = timeTakenInSecs / 3600;
        timeTakenInSecs -= (hours * 3600);
        int minutes = timeTakenInSecs / 60;
        timeTakenInSecs -= (minutes * 60);

        return zeroPad(hours).concat(":").concat(zeroPad(minutes)).concat(":").concat(zeroPad(timeTakenInSecs));
    }
}

class Contestant implements Comparable<Contestant> {

    private String username;

    private int totalScore;
    private int totalTimeTaken;

    private List<ContestResult> contestResults;

    public Contestant(String username) {
        this.username = username;
        this.contestResults = new ArrayList<>();
    }

    public void addContestResult(ContestResult newResult) {
        contestResults.add(newResult);
        totalScore += newResult.getScore();
        totalTimeTaken += newResult.getTimeTaken();
    }

    @Override
    public int compareTo(Contestant other) {
        if (totalScore == other.totalScore) {
            return totalTimeTaken - other.totalTimeTaken;
        }
        return other.totalScore - totalScore;
    }

    @Override
    public String toString() {
        return username.concat(" | ").concat(String.valueOf(totalScore)).concat(" | ")
                .concat(ScoreBoard.formatSecsToTime(totalTimeTaken));
    }
}

class ContestResult {
    private int score;
    private int timeTaken;

    public ContestResult(int score, int timeTaken) {
        this.score = score;
        this.timeTaken = timeTaken;
    }

    public int getScore() {
        return score;
    }

    public int getTimeTaken() {
        return timeTaken;
    }
}
