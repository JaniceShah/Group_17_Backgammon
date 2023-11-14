package service;

import dto.Checkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackgammonBoard {

    public enum colors {Black, White}

    public static int positionsNumber = 24;

    private static List<List<Checkers>> checkersPosition= new ArrayList<>();

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
    }

    public static void initialize(){
        System.out.println("Welcome to Backgammon!");
        System.out.println("Initial board:");

        List<Integer> blackPositions = Arrays.asList(0, 11, 16, 18);
        List<Integer> whitePositions = Arrays.asList(23, 12, 7, 5);
        List<Integer> numberOfCheckers = Arrays.asList(2,5,3,5);
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

    public static int determineFirstPlayer() {
        System.out.println("Rolling one die for each player to determine which player \r\n" +
                "goes first:\n");

        int numPlayers = 2;
        int[] diceResults = new int[numPlayers];

        for (int player = 0; player < numPlayers; player++) {
            diceResults[player] = Actions.rollDie();
            System.out.println("Player " + (player + 1) + " rolled a " + diceResults[player]);
            if(player==1 && diceResults[0]==diceResults[1]){
                player=-1;
                System.out.println("Since both players have got same dice number shuffling the dice again");
            }
        }

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
}
