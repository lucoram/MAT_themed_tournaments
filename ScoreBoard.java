import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class ScoreBoard {

    public static void main(String[] args) {
        List<Contestant> allContestants = new ArrayList<>();

        try
        {
	        File file=new File("./scoreboard.csv");
	        Scanner sc = new Scanner(file);
	        while (sc.hasNextLine()) {
		        String scoreLine = sc.nextLine();
		        String[] parts = scoreLine.split(", ");
		        String name = parts[0];
		        String[] score = Arrays.copyOfRange(parts, 1, parts.length);
		        allContestants.add(buildContestant(name, score));
	        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
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
