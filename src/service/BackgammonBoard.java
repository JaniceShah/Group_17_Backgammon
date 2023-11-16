package service;

import dto.Checkers;
import dto.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class BackgammonBoard {

    private static Scanner scanner = new Scanner(System.in);
    public enum colors {Black, White}

    public static int positionsNumber = 24;

    private static List<Checkers> whiteOutCheckers = new ArrayList<>();
    private static List<Checkers> blackOutCheckers = new ArrayList<>();
    private static List<Checkers> movedOutWhiteCheckers = new ArrayList<>();
    private static List<Checkers> movedOutBlackCheckers = new ArrayList<>();
    private static List<Checkers> movedOutCheckers = new ArrayList<>();

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

        if(!whiteOutCheckers.isEmpty()){
            System.out.println("The number of white checkers outside board are:"+ whiteOutCheckers);
        }
        if(!blackOutCheckers.isEmpty()){
            System.out.println("The number of black checkers outside board are:"+ blackOutCheckers);
        }

        if (isGameOver()) {
            int winner = determineWinner();
            System.out.println("Game over! Player " + winner + " wins!");
        }
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
        int totalPlacesToMove = 0;
        if(dice2!=dice1){
            totalPlacesToMove = dice1+dice2;
        }else{
            totalPlacesToMove = dice1*4;
        }
        int checkNextTurn = p1turn ? 1: 0;

        while(totalPlacesToMove>0) {
            List<Move> legalMoves = new ArrayList<>();

            if(!whiteOutCheckers.isEmpty() && checkNextTurn ==0){
                legalMoves = optionOutBoard(dice1, dice2, player, whiteOutCheckers);
            } else if (!blackOutCheckers.isEmpty() && checkNextTurn ==1) {
                legalMoves = optionOutBoard(dice1, dice2, player, blackOutCheckers);
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
            char userInput = scanner.next().toUpperCase().charAt(0);

            if (userInput >= 'A' && userInput < 'A' + legalMoves.size()) {
                Move selectedMove = legalMoves.get(userInput - 'A');
                applyMove(selectedMove);
                int sourcePos = selectedMove.source;
                // For white checker outside the board
                if(abs(sourcePos-selectedMove.destination)>12){
                    sourcePos = 24;
                }
                int numberOfMoveMade =abs(sourcePos-selectedMove.destination);
                totalPlacesToMove-=numberOfMoveMade;
                if(dice1==numberOfMoveMade && totalPlacesToMove==dice2){
                    dice1=0;
                }
                else if(dice2==numberOfMoveMade && totalPlacesToMove==dice1){
                    dice2=0;
                }
                display();
            } else {
                System.out.println("Invalid move. Please enter a valid letter code.");
            }
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

    public static void applyMove(Move move) {
        int source = move.source;
        int destination = move.destination;
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
            if (move.destination > 12) {
                movedOutBlackCheckers.add(checkersPosition.get(source).remove(0));
            } else {
                movedOutWhiteCheckers.add(checkersPosition.get(source).remove(0));
            }
            return;
        }
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

    public static boolean isValidOutCheckerMove(int destination, List<Checkers> checkersList) {

        if (destination < 0 || destination >= positionsNumber) {
            return false;
        }

        colors sourceColor = checkersList.get(0).getColor();
        List<Checkers> destinationList = checkersPosition.get(destination);

        if (!destinationList.isEmpty() &&
                destinationList.get(0).getColor() != sourceColor &&
                destinationList.size()>1) {
            return false;
        }
        return true;
    }

    private static List<Move> optionOutBoard(int dice1, int dice2, int player, List<Checkers> checkersList){
        List<Move> legalMoves = new ArrayList<>();
        // If player == 1 then it is white otherwise here, assigned -1 to it otherwise black which is -2
        int source = -1;

        int destination1 = (checkersList.get(0).getColor()==colors.White ? positionsNumber-dice1: dice1-1 );
        int destination2 = (checkersList.get(0).getColor()==colors.White ? positionsNumber-dice2: dice2-1 );

        if (source!=destination1 && isValidOutCheckerMove(destination1, checkersList)) {
            legalMoves.add(new Move(source, destination1));
        }
        if (source!=destination2 && isValidOutCheckerMove(destination2, checkersList)
                && destination2 != destination1) {
            legalMoves.add(new Move(source, destination2));
        }
        return legalMoves;

    }
    
//     //Game is over when all the checkers of one colour move out of the board into a list
//     public static boolean isGameOver(int player) {
//         List<Checkers> outCheckers = (player == 1) ? whiteOutCheckers : blackOutCheckers;
//         return outCheckers.size() == 15; 

// }

public static boolean isGameOver() {
    return (movedOutBlackCheckers.size() ==15 || movedOutWhiteCheckers.size() == 15 );
}

public static int determineWinner() {
    return (whiteOutCheckers.size() == 15) ? 1 : 2;
}


}
