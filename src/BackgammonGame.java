import java.util.Random;
import java.util.Scanner;

public class BackgammonGame {
    private String p1name;
    private String p2name;
    private int p1score;
    private int p2score;

    public BackgammonGame(String p1name, String p2name) {
        this.p1name = p1name;
        this.p2name = p2name;
        this.p1score = 0;
        this.p2score = 0;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Backgammon!");
        System.out.println("Initial board:");
        BackgammonBoard.display();

        boolean p1Turn = true;

        while (true) {
            p1score = 0;
            p2score = 0;
            System.out.print((p1Turn ? p1name : p2name) + ", enter 'roll' to roll the dice or 'quit' to exit: ");
            String input = scanner.nextLine();

            if (input.equals("quit")) {
                System.out.println("Game over!");
                break;
            }

            if (input.equals("roll")) {
                int dice1 = random.nextInt(6) + 1;
                int dice2 = random.nextInt(6) + 1;

                System.out.println((p1Turn ? p1name : p2name) + " rolled a " + dice1 + " and a " + dice2);

                if (p1Turn) {
                    p1score += dice1 + dice2;
                } else {
                    p2score += dice1 + dice2;
                }

                System.out.println("Total of the dice: " + p1name + ": " + p1score + ", " + p2name + ": " + p2score);
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
}
