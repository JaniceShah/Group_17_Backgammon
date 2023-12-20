package service;

import dto.Checkers;
import dto.Move;
import dto.Player;

import java.util.*;

import static java.lang.Math.abs;

public class BackgammonBoard {

    public enum colors {Black, White}
    public static int positionsNumber = 24;
    private static int matchLength;

    private static List<Checkers> whiteOutCheckers;
    private static List<Checkers> blackOutCheckers;
    static List<Checkers> whiteCheckersTimeOut;
    static List<Checkers> blackCheckersTimeOut;
    static boolean whiteEnd = true;
    static boolean blackEnd = false;

    static List<List<Checkers>> checkersPosition= new ArrayList<>();

    public static void setMatchLength(int length) {
        matchLength = length;
    }
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

        if(!whiteOutCheckers.isEmpty()){
            System.out.println("The number of white checkers outside board are:"+ whiteOutCheckers);
        }
        if(!blackOutCheckers.isEmpty()){
            System.out.println("The number of black checkers outside board are:"+ blackOutCheckers);
        }

        if(!whiteCheckersTimeOut.isEmpty()){
            System.out.println("The number of white checkers timed out of board are:"+ whiteCheckersTimeOut);
        }
        if(!blackCheckersTimeOut.isEmpty()){
            System.out.println("The number of black checkers timed out of board are:"+ blackCheckersTimeOut);
        }

        if (isGameOver()) {
            int winner = determineWinner();
            System.out.println("Game over! Player " + winner + " wins!");
        }
    }

    public void initialize(){
        System.out.println("Welcome to Backgammon!");
        System.out.println("Match Length: " + matchLength + " games");
        System.out.println("Initial board:");

        List<Integer> blackPositions = Arrays.asList(10, 11, 16, 18);
        List<Integer> whitePositions = Arrays.asList(0, 1, 2, 3);
        List<Integer> numberOfCheckers = Arrays.asList(2,5,3,5);
        whiteOutCheckers = new ArrayList<>();
        checkersPosition= new ArrayList<>();
        blackOutCheckers = Collections.EMPTY_LIST;
        whiteCheckersTimeOut = Collections.EMPTY_LIST;
        blackCheckersTimeOut = Collections.EMPTY_LIST;
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
                blackOutCheckers.add(destinationList.remove(0));
            }else{
                whiteOutCheckers.add(destinationList.remove(0));
            }
        }

        if(source==-1){
            if(move.destination<12){
                checkersPosition.get(destination).add(blackOutCheckers.remove(0));
            }else{
                checkersPosition.get(destination).add(whiteOutCheckers.remove(0));
            }
            return;
        }

        Checkers shiftedChecker = checkersPosition.get(source).remove(0);
        checkersPosition.get(destination).add(shiftedChecker);

        //checkers to the movedOutCheckers list
        if (source == -1) {
            if(sourceColor==colors.Black){
                blackCheckersTimeOut.add(checkersPosition.get(source).remove(0));
            } else {
                whiteCheckersTimeOut.add(checkersPosition.get(source).remove(0));
            }
            return;
        }
    }
    public static void applyEndMoves(Move move){
        colors color = move.destination==24? colors.Black: colors.White;
        Checkers removedChecker = checkersPosition.get(move.source).remove(0);
        if(color==colors.White){
            whiteOutCheckers.add(removedChecker);
        }else{
            blackOutCheckers.add(removedChecker);
        }
    }

    public static boolean isGameOver() {
        return (whiteOutCheckers.size() ==15 || blackOutCheckers.size() == 15 );
    }

    public static int determineWinner() {
        return (whiteOutCheckers.size() == 15) ? 1 : 2;
    }

}
