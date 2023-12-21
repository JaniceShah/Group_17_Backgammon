package test;

import dto.Player;
import service.BackgammonBoard;
import service.MatchActions;
import service.PlayerActions;
import service.GetMoveOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static service.PlayerActions.*;

public class TestRunner {

    private static boolean p1Turn = true; // Assuming player 1 starts the turn
    private static Player player1 = new Player();
    private static Player player2 = new Player();

    public static void main(String[] args) {
        BackgammonBoard.initialize();
        MatchActions matchActions = new MatchActions();

        try (BufferedReader br = new BufferedReader(new FileReader("./src/commands.txt"))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                if(lineNum==0){
                    player1.setName(line);
                    lineNum++;
                }
                else if(lineNum==1){
                    player2.setName(line);
                    lineNum++;
                }
                else if(line.equals(quit)){
                    System.out.println("Game Over!");
                    break;
                }
                else if(line.equals("refuseDouble")){
                    matchActions.refuseDouble();
                    break;
                }
                else
                    executeCommand(line, matchActions);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command, MatchActions matchActions) {
        String[] parts = command.split("\\s+");

        switch (parts[0]) {
            case "isDoubleOffered":
                matchActions.isDoubleOffered();
                break;
            case "displayDoublingCube":
                matchActions.displayDoublingCube();
                break;
            case "doubleOffer":
                matchActions.doubleOffer(player1, player2);
                break;
            case "acceptDouble":
                matchActions.acceptDouble();
                break;
            case "roll": {
                int dice1 = PlayerActions.rollDie();
                int dice2 = PlayerActions.rollDie();
                GetMoveOptions.options(dice1, dice2, p1Turn, p1Turn ? player1 : player2, true);
                p1Turn = !p1Turn;
                break;
            }
            case "pip": {
                System.out.println("Pip number for both players are "
                        + player1.getName() + ":" + player1.getScore() + " "
                        + player2.getName() + ":" + player2.getScore());
                break;
            }
            case "hint": {
                System.out.println("Following commands are allowed to be entered:\n1." + pip +
                        "\n 2." + roll +
                        "\n 3." + quit +
                        "\n 4." + new_game +
                        "\n 5." + test +
                        "\n 6." + double_cube);
                break;
            }
            // Add more cases as needed for other commands
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
