package service;

import dto.Checkers;
import dto.Move;

import java.util.*;


/**
 * Represents the state of a Backgammon board and provides methods for game management.
 */
public class BackgammonBoard {

    /**
     * Enum representing the colors of checkers on the board.
     */
    public enum colors {Black, White}
    public static int positionsNumber = 24;
    private static int matchLength;
    private static int whiteScore = 0;
    private static int blackScore = 0;


    public static List<Checkers> whiteBearOffCheckers = new ArrayList<>();
    public static List<Checkers> blackBearOffCheckers = new ArrayList<>();
    static List<Checkers> whiteCheckersOnBar = new ArrayList<>();
    static List<Checkers> blackCheckersOnBar = new ArrayList<>();
    static boolean whiteEnd = true;
    static boolean blackEnd = false;

    public static List<List<Checkers>> checkersPosition= new ArrayList<>();

    public static void setMatchLength(int length) {
        matchLength = length;
    }

    /**
     * This method is used to print the board and show the current positions of the checkers
     * it also prints the checkers present in bar or in
     */
    public static void display() {
        
        System.out.println("| 13 14 15 16 17 18 |BAR| 19 20 21 22 23 24 |");
        System.out.println("+-------------------+---+-------------------+");
        int maxRow = 0;
        for( int row = 12; row < 24; row++){
            int rowSize = checkersPosition.get(row).size();
            if(rowSize>maxRow){
                maxRow = rowSize;
            }
        }

        for (int row = 0; row < maxRow; row++) {
            System.out.print("| ");
            for (int i = 12; i < 18; i++) {
                if(checkersPosition.get(i).size()-row>0){
                    System.out.print(checkersPosition.get(i).get(row));
                }else{
                    System.out.print("   ");
                }
            }
            System.out.print("|   | ");
            for (int i = 18; i < 24; i++) {
                if(checkersPosition.get(i).size()-row>0){
                    System.out.print(checkersPosition.get(i).get(row));
                }else{
                    System.out.print("   ");
                }
            }
            System.out.println("|");
        }

        System.out.println("|-------------------|BAR|-------------------|");

        for( int row = 0; row < 12; row++){
            int rowSize = checkersPosition.get(row).size();
            if(rowSize>maxRow){
                maxRow = rowSize;
            }
        }

        for (int row = maxRow-1; row >=0; row--) {
            System.out.print("| ");
            for (int i = 11; i > 5; i--) {
                if(checkersPosition.get(i).size()-row>0){
                    System.out.print(checkersPosition.get(i).get(row));
                }else{
                    System.out.print("   ");
                }
            }
            System.out.print("|   | ");
            for (int i = 5; i >= 0; i--) {
                if(checkersPosition.get(i).size()-row>0){
                    System.out.print(checkersPosition.get(i).get(row));
                }else{
                    System.out.print("   ");
                }
            }
            System.out.println("|");
        }

        System.out.println("+-------------------+---+-------------------+");
        System.out.println("| 12 11 10 09 08 07 |BAR| 06 05 04 03 02 01 |");

        if(!whiteBearOffCheckers.isEmpty()){
            System.out.println("The number of white checkers outside board are:"+ whiteBearOffCheckers);
        }
        if(!blackBearOffCheckers.isEmpty()){
            System.out.println("The number of black checkers outside board are:"+ blackBearOffCheckers);
        }

        if(!whiteCheckersOnBar.isEmpty()){
            System.out.println("The number of white checkers timed out of board are:"+ whiteCheckersOnBar);
        }
        if(!blackCheckersOnBar.isEmpty()){
            System.out.println("The number of black checkers timed out of board are:"+ blackCheckersOnBar);
        }

        // Game over and winner determination logic
        if (isGameOver()) {
            int winner = determineWinner();
            System.out.println("Game over! Player " + winner + " wins!");
        }
    }

    /**
     * Initializes the Backgammon board with the starting configuration.
     */
    public static void initialize(){
        // Initialization logic
        System.out.println("Welcome to Backgammon!");
        System.out.println("Match Length: " + matchLength + " games");
        System.out.println("Initial board:");

        List<Integer> blackPositions = Arrays.asList(0, 11, 16, 18);
        List<Integer> whitePositions = Arrays.asList(23, 12, 7, 5);
        List<Integer> numberOfCheckers = Arrays.asList(2,5,3,5);
        whiteBearOffCheckers = new ArrayList<>();
        checkersPosition= new ArrayList<>();
        blackBearOffCheckers = Collections.EMPTY_LIST;
        whiteCheckersOnBar = Collections.EMPTY_LIST;
        blackCheckersOnBar = Collections.EMPTY_LIST;
        whiteEnd = true;
        blackEnd = false;

        int blackCheckers = 0;
        int whiteCheckers = 0;
        for(int i=0;i<positionsNumber;i++){
            List<Checkers> checkersList = new ArrayList<>();
            int index = -1;
            colors color= colors.Black;
            if(blackPositions.contains(i)){
                index = blackPositions.indexOf(i);
                color = colors.Black;
            }else if(whitePositions.contains(i)){
                index = whitePositions.indexOf(i);
                color = colors.White;
            }
            if(index!=-1){
                for(int j = 0;j<numberOfCheckers.get(index);j++){
                    Checkers checker = new Checkers(color, color == colors.Black?blackCheckers++:whiteCheckers++);
                    checkersList.add(checker);
                }
            }
            checkersPosition.add(checkersList);
        }
    }

    /**
     * Applies a move to the Backgammon board.
     *
     * @param move The move to apply.
     */
    public static void applyMove(Move move) {
        int source = move.source;
        int destination = move.destination;
        // Total moves to calculate the pip number
        colors sourceColor;
        if(source==-1 && move.destination<12){
            sourceColor = colors.Black;
        }else if(source==-1 && move.destination>12){
            sourceColor = colors.White;
        }else{
            sourceColor = checkersPosition.get(source).get(0).getColor();
        }

        List<Checkers> destinationList = checkersPosition.get(destination);
        if (!destinationList.isEmpty() &&
                destinationList.get(0).getColor() != sourceColor &&
                destinationList.size()==1) {
            if(destinationList.get(0).getColor()== colors.Black){
                blackBearOffCheckers.add(destinationList.remove(0));
            }else{
                whiteBearOffCheckers.add(destinationList.remove(0));
            }
        }

        if(source==-1){
            if(move.destination<12){
                checkersPosition.get(destination).add(blackBearOffCheckers.remove(0));
            }else{
                checkersPosition.get(destination).add(whiteBearOffCheckers.remove(0));
            }
            return;
        }

        Checkers shiftedChecker = checkersPosition.get(source).remove(0);
        checkersPosition.get(destination).add(shiftedChecker);

        //checkers to the movedOutCheckers list
        if (source == -1) {
            if(sourceColor==colors.Black){
                blackCheckersOnBar.add(checkersPosition.get(source).remove(0));
            } else {
                whiteCheckersOnBar.add(checkersPosition.get(source).remove(0));
            }
            return;
        }
    }

    /**
     * Applies moves at the end of the game, specifically for checkers going off the board.
     *
     * @param move The move to apply at the end.
     */
    public static void applyEndMoves(Move move){
        colors color = move.destination==24? colors.Black: colors.White;
        Checkers removedChecker = checkersPosition.get(move.source).remove(0);
        if(color==colors.White){
            whiteBearOffCheckers.add(removedChecker);
        }else{
            blackBearOffCheckers.add(removedChecker);
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public static boolean isGameOver() {
        return (whiteBearOffCheckers.size() == 15 || blackBearOffCheckers.size() == 15);
    }

    /**
     * Determines the winner and updates the scores.
     *
     * @return The number of white checkers outside the board.
     */
    public static int determineWinner() {
        int whiteOutCheckersSize = whiteBearOffCheckers.size();
        int blackOutCheckersSize = blackBearOffCheckers.size();

        if (whiteOutCheckersSize == 15 && blackOutCheckersSize == 15) {
            announceResult("Backgammon");
            updateScores(3, 0);
        } else if (whiteOutCheckersSize == 15) {
            announceResult("Gammon");
            updateScores(2, 0);
        } else if (blackOutCheckersSize == 15) {
            announceResult("Gammon");
            updateScores(0, 2);
        } else {
            announceResult("Single");
            updateScores(1, 1);
        }
        return whiteOutCheckersSize;
    }

    /**
     * Announces the result of the game.
     *
     * @param result The result of the game.
     */
    private static void announceResult(String result) {
        System.out.println("Game over! Result: " + result);
    }

    /**
     * Updates the scores based on the game outcome.
     *
     * @param whiteScoreIncrement The increment in white player's score.
     * @param blackScoreIncrement The increment in black player's score.
     */
    private static void updateScores(int whiteScoreIncrement, int blackScoreIncrement) {
        whiteScore += whiteScoreIncrement;
        blackScore += blackScoreIncrement;
        System.out.println("Match Score - White: " + whiteScore + ", Black: " + blackScore);
    }
}
