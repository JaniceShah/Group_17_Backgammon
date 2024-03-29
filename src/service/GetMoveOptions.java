package service;
import dto.Checkers;
import dto.Move;
import dto.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.abs;
import static service.BackgammonBoard.checkersPosition;
import static service.BackgammonBoard.positionsNumber;

public class GetMoveOptions {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Gets the player color value based on the BackgammonBoard.colors enum.
     *
     * @param color The color of the player (Black or White).
     * @return 1 for Black, -1 for White.
     */
    public static int getColorPlayer(BackgammonBoard.colors color) {
        return color == BackgammonBoard.colors.Black ? 1 : -1;
    }

    /**
     * Displays legal move options for the player based on the rolled dice values.
     *
     * @param dice1      The value of the first die.
     * @param dice2      The value of the second die.
     * @param p1turn     True if it's Player 1's turn, false otherwise.
     * @param playerRolling The player rolling the dice.
     * @param isTest     True if running in test mode, false otherwise.
     */
    public static void options(int dice1, int dice2, Boolean p1turn, Player playerRolling, boolean isTest) {
        int player = p1turn ? 1 : -1;
        int totalPlacesToMove = 0;
        if(dice2!=dice1){
            totalPlacesToMove = dice1+dice2;
        }else{
            totalPlacesToMove = dice1*4;
        }
        int checkNextTurn = p1turn ? 1: 0;

        while(totalPlacesToMove>0) {
            List<Move> legalMoves;
            int checkIfEndGame = 0;
            if (!BackgammonBoard.whiteCheckersOnBar.isEmpty() && checkNextTurn == 0) {
                legalMoves = optionOutBoard(dice1, dice2, player, BackgammonBoard.whiteCheckersOnBar);
            } else if (!BackgammonBoard.blackCheckersOnBar.isEmpty() && checkNextTurn == 1) {
                legalMoves = optionOutBoard(dice1, dice2, player, BackgammonBoard.blackCheckersOnBar);
            } else if ((BackgammonBoard.whiteEnd && !p1turn)|| (BackgammonBoard.blackEnd && p1turn)) {
                checkIfEndGame = 1;
                legalMoves = getLegalEndGameMoves(dice1, dice2, player, 0);
                if(legalMoves.isEmpty())
                    legalMoves = getLegalEndGameMoves(dice1, dice2, player, 1);
                if(legalMoves.isEmpty()) {
                    legalMoves = getLegalMoves(dice1, dice2, player);
                    checkIfEndGame = 0;
                }

            } else {
                legalMoves = getLegalMoves(dice1, dice2, player);
                checkNextTurn = 2;
            }

            String printRolls = dice1==0? Integer.toString(dice2): dice2==0? Integer.toString(dice1)
                    : dice1 + " and " + dice2 + ":";
            if(legalMoves.isEmpty()){
                System.out.println("Legal Moves for Player " + (p1turn ? 1 : 2) + " after rolling "+ printRolls);
                System.out.println("No moves available");
                break;
            }
            System.out.println("Legal Moves for Player " + (p1turn ? 1 : 2) + " after rolling "+ printRolls);
            for (int i = 0; i < legalMoves.size(); i++) {
                System.out.println((char) ('A' + i) + ": " + legalMoves.get(i).toString());
            }

            System.out.print("Enter the letter code for the desired move (e.g., 'A'): ");
            char userInput;
            if(!isTest){
                userInput = scanner.next().toUpperCase().charAt(0);
            }else{
                userInput = 'A';
            }

            if (userInput >= 'A' && userInput < 'A' + legalMoves.size()) {
                Move selectedMove = legalMoves.get(userInput - 'A');
                if(checkIfEndGame==1){
                    BackgammonBoard.applyEndMoves(selectedMove);
                    int diceUsed = abs(selectedMove.source-selectedMove.destination);
                    PlayerActions.calculatePipCount(diceUsed, playerRolling);
                    if(diceUsed==dice1){
                        totalPlacesToMove -= dice1;
                        if (totalPlacesToMove == dice2) {
                            dice1 = 0;
                        }
                    }
                    else if(diceUsed==dice2) {
                        totalPlacesToMove -= dice2;
                        if (totalPlacesToMove == dice1) {
                            dice2 = 0;
                        }
                    } else {
                        if(diceUsed<dice1 && ((dice1!=0 && dice2!=0  && dice1<dice2) || (dice2==0 && dice1!=0))){
                            totalPlacesToMove -= dice1;
                            if (totalPlacesToMove == dice2) {
                                dice1 = 0;
                            }
                        }
                        else {
                            totalPlacesToMove -= dice2;
                            if (totalPlacesToMove == dice1) {
                                dice2 = 0;
                            }
                        }
                    }
                }else {
                    BackgammonBoard.applyMove(selectedMove);
                    int sourcePos = selectedMove.source;
                    // For white checker outside the board
                    int numberOfMoveMade = abs(sourcePos - selectedMove.destination);
                    PlayerActions.calculatePipCount(numberOfMoveMade, playerRolling);
                    if (numberOfMoveMade > 12) {
                        sourcePos = 24;
                    }
                    totalPlacesToMove -= numberOfMoveMade;
                    if (dice1 == numberOfMoveMade && totalPlacesToMove == dice2) {
                        dice1 = 0;
                    } else if (dice2 == numberOfMoveMade && totalPlacesToMove == dice1) {
                        dice2 = 0;
                    }
                }
                PlayerActions.getPipCount(playerRolling);
                BackgammonBoard.display();

            } else {
                System.out.println("Invalid move. Please enter a valid letter code.");
            }
        }
    }

    /**
     * Gets legal moves for a player based on the rolled dice values.
     *
     * @param dice1  The value of the first die.
     * @param dice2  The value of the second die.
     * @param player The player making the move.
     * @return A list of legal moves.
     */
    public static List<Move> getLegalMoves(int dice1, int dice2, int player) {
        List<Move> legalMoves = new ArrayList<>();

        for (int source = 0; source < positionsNumber; source++) {
            if (checkersPosition.get(source).isEmpty() || getColorPlayer(checkersPosition.get(source).get(0).getColor()) != player) {
                continue;
            }

            int destination1 = source + (player == 1 ? dice1 : -dice1);
            int destination2 = source + (player == 1 ? dice2 : -dice2);
            if (source!=destination1 && isValidMove(source, destination1)) {
                legalMoves.add(new Move(source, destination1));
            }
            if (source!=destination2 && isValidMove(source, destination2)
                    && destination2 != destination1) {
                legalMoves.add(new Move(source, destination2));
            }
        }

        return legalMoves;
    }

    /**
     * Checks if a move from a source to a destination is valid.
     *
     * @param source      The source position.
     * @param destination The destination position.
     * @return True if the move is valid, false otherwise.
     */
    public static boolean isValidMove(int source, int destination) {

        if (source < 0 || source >= positionsNumber || destination < 0 || destination >= positionsNumber) {
            return false;
        }
        if (checkersPosition.get(source).isEmpty()) {
            return false;
        }
        BackgammonBoard.colors sourceColor = checkersPosition.get(source).get(0).getColor();
        List<Checkers> destinationList = checkersPosition.get(destination);

        if (!destinationList.isEmpty() &&
                destinationList.get(0).getColor() != sourceColor &&
                destinationList.size()>1) {
            return false;
        }


        int moveDirection = destination - source;
        int player = getColorPlayer(sourceColor);
        if (player == 1 && moveDirection <= 0) {
            return false;
        } else if (player == -1 && moveDirection >= 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a move with an out checker is valid.
     *
     * @param destination  The destination position.
     * @param checkersList List of checkers on the bar.
     * @return True if the move is valid, false otherwise.
     */
    public static boolean isValidOutCheckerMove(int destination, List<Checkers> checkersList) {

        if (destination < 0 || destination >= positionsNumber) {
            return false;
        }

        BackgammonBoard.colors sourceColor = checkersList.get(0).getColor();
        List<Checkers> destinationList = checkersPosition.get(destination);

        if (!destinationList.isEmpty() &&
                destinationList.get(0).getColor() != sourceColor &&
                destinationList.size()>1) {
            return false;
        }
        return true;
    }

    /**
     * Gets legal moves for checkers on the bar attempting to move out of the board.
     *
     * @param dice1        The value of the first die.
     * @param dice2        The value of the second die.
     * @param player       The player making the move.
     * @param checkersList List of checkers on the bar.
     * @return A list of legal moves.
     */
    private static List<Move> optionOutBoard(int dice1, int dice2, int player, List<Checkers> checkersList){
        List<Move> legalMoves = new ArrayList<>();
        // If player == 1 then it is white otherwise here, assigned -1 to it otherwise black which is -2
        int source = -1;

        int destination1 = (checkersList.get(0).getColor()== BackgammonBoard.colors.White ? positionsNumber-dice1: dice1-1 );
        int destination2 = (checkersList.get(0).getColor()== BackgammonBoard.colors.White ? positionsNumber-dice2: dice2-1 );

        if (source!=destination1 && isValidOutCheckerMove(destination1, checkersList)) {
            legalMoves.add(new Move(source, destination1));
        }
        if (source!=destination2 && isValidOutCheckerMove(destination2, checkersList)
                && destination2 != destination1) {
            legalMoves.add(new Move(source, destination2));
        }
        return legalMoves;

    }

    /**
     * Gets legal moves for checkers at the end of the game.
     *
     * @param dice1      The value of the first die.
     * @param dice2      The value of the second die.
     * @param player     The player making the move.
     * @param moreMoves  Additional move options flag.
     * @return A list of legal moves.
     */
    public static List<Move> getLegalEndGameMoves(int dice1, int dice2, int player, int moreMoves) {
        List<Move> legalMoves = new ArrayList<>();
        int finalPos = 0;
        BackgammonBoard.colors color= BackgammonBoard.colors.Black;
        int startLoop = player==1?18:0;
        int endLoop = player==1?24:6;
        for (int source = startLoop; source < endLoop; source++) {
            if(finalPos== 0 && !checkersPosition.get(source).isEmpty()
                    && getColorPlayer(checkersPosition.get(source).get(0).getColor()) == player){
                color = checkersPosition.get(source).get(0).getColor();
                finalPos = color== BackgammonBoard.colors.Black?24:-1;
            }

            if(checkersPosition.get(source).isEmpty())
                continue;

            if(moreMoves==0) {
                if (dice1 != 0 && dice1 == abs(finalPos - source) && (checkersPosition.get(source).get(0).getColor() == color)) {
                    legalMoves.add(new Move(source, finalPos));
                    dice1 = 0;
                } else if (dice2 != 0 && dice2 == abs(finalPos - source) && (checkersPosition.get(source).get(0).getColor() == color)) {
                    legalMoves.add(new Move(source, finalPos));
                    dice2 = 0;
                }
            } else{
                if(((dice1!=0 && abs(finalPos - source)<dice1) || (dice2!= 0 && abs(finalPos - source)<dice2))){
                    legalMoves.add(new Move(source, finalPos));
                }
            }
        }
        return legalMoves;
    }

}
