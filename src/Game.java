import java.util.*;

/**
 * Created by Sergey on 17.05.2017.
 */
public class Game {

    private int participants2InPower;
    private int interestCommand;
    private int[] intCommGames;
    private int numberOfParticipants;
    private int gamePlaces;
    private double[] startRating;

    private List<Integer> initialOrder;
    private List<Integer> toss;
    private int[] playedGamesOnLevel;
    private double[] tmpRating;

    public static void main(String[] args) {
        Game game = new Game(5, 1);
    }

    public Game(int participants2InPower, int interestCommand) {
        this.participants2InPower = participants2InPower;
        this.interestCommand = interestCommand - 1;
        this.intCommGames = new int[participants2InPower];
        numberOfParticipants = (int) Math.pow(2, participants2InPower);
        gamePlaces = numberOfParticipants * 2 - 2;
        startRating = new double[numberOfParticipants];
        for (int i = 0; i < numberOfParticipants; i++) {
//            startRating[i] = Math.sqrt((double) i + 1) + 10;
            startRating[i] = 1000 * i;
        }
        experiment();
    }

    public void experiment() {
        int[] finalScore = new int[numberOfParticipants];
        initialOrder = new ArrayList<Integer>();
        for (int i = 0; i < numberOfParticipants; i++) {
            initialOrder.add(i);
        }
        for (int gameNumber = 0; gameNumber < 1000; gameNumber++) {
            Collections.shuffle(initialOrder);
            for (int i = 0; i < initialOrder.size(); i++) {
                Integer integer = initialOrder.get(i);

            }
            toss = new ArrayList<Integer>();
            for (int i = 0; i < initialOrder.size(); i++) {
                toss.add(initialOrder.get(i));
            }
            for (int i = initialOrder.size(); i <= gamePlaces; i++) {
                toss.add(null);
            }
            int winer = competition();
            finalScore[initialOrder.get(winer)]++;
        }
        System.out.println(Arrays.toString(finalScore));
        System.out.println(Arrays.toString(intCommGames));
    }

    public int competition() {
        preparationToCompetition();
        return playGame(gamePlaces, 0);
    }

    public int playGame(int indexOfWiner, int lvl) {
        int lvlPenalty = (int) Math.pow(2, lvl);
        int player1 = indexOfWiner - lvlPenalty - playedGamesOnLevel[lvl];
        int player2 = indexOfWiner - lvlPenalty - playedGamesOnLevel[lvl] - 1;
        if (toss.get(player1) == null) {
            playGame(player1, lvl + 1);
        }
        if (toss.get(player2) == null) {
            playGame(player2, lvl + 1);
        }
        // тут плюсуем если участвует интересная для нас команда
        if (toss.get(player1).equals(interestCommand) || toss.get(player2).equals(interestCommand)) {
            intCommGames[participants2InPower - lvl - 1]++;
        }
        playedGamesOnLevel[lvl]++;
        toss.set(indexOfWiner, fight(player1, player2));
        return toss.get(indexOfWiner);
    }

    private int fight(int player1, int player2) {
        double totalRating = tmpRating[toss.get(player1)] + tmpRating[toss.get(player2)];
        double result = Math.random() * totalRating;
        if (tmpRating[toss.get(player1)] > result) {
            tmpRating[toss.get(player1)] = result;
            return toss.get(player1);
        } else {
            tmpRating[toss.get(player2)] = result;
            return toss.get(player2);
        }
    }

    private void preparationToCompetition() {
        tmpRating = new double[numberOfParticipants];
        for (int i = 0; i < initialOrder.size(); i++) {
            tmpRating[i] = startRating[initialOrder.get(i)];
        }
//        System.out.println(Arrays.toString(startRating));
//        System.out.println(initialOrder.toString());
//        System.out.println(Arrays.toString(tmpRating));
//        System.out.println("----------------------------------");
        playedGamesOnLevel = new int[participants2InPower + 1];
    }

}
