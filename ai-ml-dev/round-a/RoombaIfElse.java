
public class RoombaIfElse {

    static String roombaIfElse(String[] roomStatusesOverTime) {
        StringBuilder answerBuilder = new StringBuilder();
        char position = 'A';

        for (String status : roomStatusesOverTime) {

            boolean sucked = false;

            if (position == 'A') {
                if (status.charAt(0) == '#') {
                    answerBuilder.append("S");
                    sucked = true;
                }
                if (status.charAt(1) == '#') {
                    answerBuilder.append("RS");
                    position = 'B';
                    sucked = true;
                }
            } else if (position == 'B') {
                if (status.charAt(1) == '#') {
                    answerBuilder.append("S");
                    sucked = true;
                }
                if (status.charAt(0) == '#') {
                    answerBuilder.append("LS");
                    position = 'A';
                    sucked = true;
                }
            }

            if (!sucked) {
                answerBuilder.append("I");
            }
        }

        return answerBuilder.toString();
    }
}
