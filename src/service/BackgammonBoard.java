package service;

import dto.Checkers;
import dto.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BackgammonBoard {

    private static Scanner scanner = new Scanner(System.in);
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

    public static void move(int position, int moveNumber){
        Checkers shiftCheckers = checkersPosition.get(position-1).remove(0);
        checkersPosition.get(position-moveNumber-1).add(shiftCheckers);
    }


    public static void options(int dice1, int dice2, Boolean p1turn) {
        int player = p1turn ? 1 : -1;

        List<Move> legalMoves = getLegalMoves(dice1, dice2, player);

        System.out.println("Legal Moves for Player " + (p1turn ? 1 : 2) + " after rolling " + dice1 + " and " + dice2 + ":");
        for (int i = 0; i < legalMoves.size(); i++) {
            System.out.println((char) ('A' + i) + ": " + legalMoves.get(i).toString());
        }

     
        System.out.print("Enter the letter code for the desired move (e.g., 'A'): ");
        char userInput = scanner.next().toUpperCase().charAt(0);

      
        if (userInput >= 'A' && userInput < 'A' + legalMoves.size()) {
            Move selectedMove = legalMoves.get(userInput - 'A');
            applyMove(selectedMove);
        } else {
            System.out.println("Invalid move. Please enter a valid letter code.");
        }
    }

    public static List<Move> getLegalMoves(int dice1, int dice2, int player) {
        List<Move> legalMoves = new ArrayList<>();

   
        for (int source = 0; source < positionsNumber; source++) {
            if (checkersPosition.get(source).isEmpty() || getColorPlayer(checkersPosition.get(source).get(0).getColor()) != player) {
                continue; 
            }

           
            int destination1 = source + (player == 1 ? dice1 : -dice1);
            int destination2 = source + (player == 1 ? dice2 : -dice2);

            if (isValidMove(source, destination1)) {
                legalMoves.add(new Move(source, destination1));
            }

            if (isValidMove(source, destination2) && destination2 != destination1) {
                legalMoves.add(new Move(source, destination2));
            }
        }

        return legalMoves;
    }

    public static void applyMove(Move move) {
        int source = move.source;
        int destination = move.destination;

        Checkers shiftedChecker = checkersPosition.get(source).remove(0);
        checkersPosition.get(destination).add(shiftedChecker);
    }
    public static int getColorPlayer(colors color) {
        return color == colors.Black ? 1 : -1;
    }
    
    public static boolean isValidMove(int source, int destination) {
      
        if (source < 0 || source >= positionsNumber || destination < 0 || destination >= positionsNumber) {
            return false;
        }
    
       
        if (checkersPosition.get(source).isEmpty()) {
            return false;
        }
    
   
        colors sourceColor = checkersPosition.get(source).get(0).getColor();
        if (!checkersPosition.get(destination).isEmpty() && checkersPosition.get(destination).get(0).getColor() != sourceColor) {
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
    
    
}
