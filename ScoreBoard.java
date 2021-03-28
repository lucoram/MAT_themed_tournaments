import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {

    public static void main(String[] args) {
        List<Contestant> allContestants = new ArrayList<>();

        allContestants.add(buildContestant("r_radoniaina11", "463-2:48:59", "600-2:47:50", "570-2:55:47"));
        allContestants.add(buildContestant("valdotsiarohasi1", "325-2:09:51", "33-1:39:11", "270-1:03:22"));
        allContestants.add(buildContestant("ramamj", "300-0:57:22", "740-4:00:21"));
        allContestants.add(buildContestant("nyainarazafindr1", "1307-2:56:44", "655-2:42:58", "694-2:44:37"));
        allContestants.add(buildContestant("andryhenintsoa", "1200-2:24:32", "600-0:55:34", "457-3:13:40"));
        allContestants.add(buildContestant("Antenaina", "600-1:47:22", "840-4:21:18", "547-3:41:55"));
        allContestants.add(buildContestant("afmika", "600-2:59:08", "337-2:27:32", "883-5:08:08"));
        allContestants.add(buildContestant("ratife", "483-2:58:16"));
        allContestants.add(buildContestant("teboka_roa", "300-1:03:55"));
        allContestants.add(buildContestant("fanantenan_1", "250-0:39:46"));
        allContestants.add(buildContestant("aryna369", "200-0:10:31"));
        allContestants.add(buildContestant("kimi_no_a", "130-0:52:13"));
        allContestants.add(buildContestant("tiantsoa_r1", "110-0:43:02"));
        allContestants.add(buildContestant("sedera-tax", "25-0:18:21"));
        allContestants.add(buildContestant("diamondraras", "600-1:56:30"));
        allContestants.add(buildContestant("puchka", "600-2:19:30", "440-2:26:07"));
        allContestants.add(buildContestant("fitiavana_raman1", "300-0:44:38"));
        allContestants.add(buildContestant("dinasoarakoto13", "247-2:06:10"));

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

        System.out.println("----------------------------------------------");
        System.out.format("| %4s | %-16s | %5s | %-8s |\n", "RANK", "USERNAME", "SCORE", "TIME");
        System.out.println("----------------------------------------------");

        int rank = 1;

        for (Contestant contestant : allContestants) {
            formatOutput(rank, contestant);
            rank++;
        }

        System.out.println("----------------------------------------------");
    }

    private static void formatOutput(int rank, Contestant contestant) {
        System.out.format("| %4d | %-16s | %5d | %-8s |\n", rank, contestant.getUsername(), contestant.getTotalScore(),
                formatSecsToTime(contestant.getTotalTimeTaken()));
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

    public String getUsername() {
        return username;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
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
