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

        while (true) {
            System.out.print(p1name + ", enter 'roll' to roll the dice or 'quit' to exit: ");
            String input = scanner.nextLine();

            if (input.equals("quit")) {
                System.out.println("Game over. Final scores:");
                System.out.println(p1name + ": " + p1score);
                System.out.println(p2name + ": " + p2score);
                break;
            }

            if (input.equals("roll")) {
                int dice1 = random.nextInt(6) + 1;
                int dice2 = random.nextInt(6) + 1;

                System.out.println(p1name + " rolled a " + dice1 + " and a " + dice2);
                p1score += dice1 + dice2;
                System.out.println("Current score: " + p1name + ": " + p1score + ", " + p2name + ": " + p2score);
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of Player 1: ");
        String p1name = scanner.nextLine();
        System.out.print("Enter the name of Player 2: ");
        String p2name = scanner.nextLine();

        BackgammonGame game = new BackgammonGame(p1name, p2name);
        game.play();
    }
    
}
