import java.util.Random;
import java.util.Scanner;

public class BackgammonGame {
    private String p1name;
    private String p2name;
    private int p1score;
    private int p2score;
    private static final int STARTING_PIP_COUNT = 167;

    public BackgammonGame(String p1name, String p2name) {
        this.p1name = p1name;
        this.p2name = p2name;
        this.p1score = STARTING_PIP_COUNT;
        this.p2score = STARTING_PIP_COUNT;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Backgammon!");
        System.out.println("Initial board:");
        BackgammonBoard.initialize();
        BackgammonBoard.display();
        System.out.println("Rolling one die for each player to determine which player \r\n" +
                "goes first:\n");

        int numPlayers = 2;
        int[] diceResults = new int[numPlayers];

      
        for (int player = 0; player < numPlayers; player++) {
            diceResults[player] = rollDie();
            System.out.println("Player " + (player + 1) + " rolled a " + diceResults[player]);
        }

        int firstPlayer = determineFirstPlayer(diceResults);
        System.out.println("Player " + (firstPlayer + 1) + " goes first!");

        boolean p1Turn = (firstPlayer == 0); 

        while (true) {
            System.out.println("Pip count - " + p1name + ": " + p1score + ", " + p2name + ": " + p2score);
            System.out.println();
            System.out.print((p1Turn ? p1name : p2name) + ", enter 'roll' to roll the dice, 'quit' to exit: ");
            String input = scanner.nextLine();

            if (input.equals("quit")) {
                System.out.println("Game over!");
                break;
            }

            if (input.equals("roll")) {
                int dice1 = random.nextInt(6) + 1;
                int dice2 = random.nextInt(6) + 1;

                System.out.println((p1Turn ? p1name : p2name) + " rolled a " + dice1 + " and a " + dice2);

                int totalDice = dice1 + dice2;

                if (p1Turn) {
                    p1score -= totalDice;
                } else {
                    p2score -= totalDice;
                }

                System.out.println("Total of the dice for " + (p1Turn ? p1name : p2name) + ": " + totalDice);
                //System.out.println("Remaining pip count for " + (p1Turn ? p1name : p2name) + ": " + (p1Turn ? p1score : p2score));
               
                p1Turn = !p1Turn;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of Player 1: ");
        String player1Name = scanner.nextLine();
        System.out.print("Enter the name of Player 2: ");
        String player2Name = scanner.nextLine();

        BackgammonGame game = new BackgammonGame(player1Name, player2Name);
        game.play();
    }

    private static int determineFirstPlayer(int[] diceResults) {
        int maxRoll = 0;
        int firstPlayer = 0;

        for (int player = 0; player < diceResults.length; player++) {
            if (diceResults[player] > maxRoll) {
                maxRoll = diceResults[player];
                firstPlayer = player;
            }
        }

        return firstPlayer;
    }

    private static int rollDie() {
        Random random = new Random();
        return random.nextInt(6) + 1; 
    }
}
