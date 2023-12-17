package service;

import dto.Checkers;
import dto.Move;
import dto.Player;

import java.util.*;

import static java.lang.Math.abs;

public class BackgammonBoard {

    private static Scanner scanner = new Scanner(System.in);
    public enum colors {Black, White}
   private static int doublingCubePosition = 1;
    private static boolean doubleOffered = false;
    private static Player offeringPlayer = null;
    private static Player receivingPlayer = null;
    public static int positionsNumber = 24;
    private static int matchLength;

    private static List<Checkers> whiteOutCheckers = new ArrayList<>();
    private static List<Checkers> blackOutCheckers = new ArrayList<>();
    private static List<Checkers> whiteCheckersTimeOut = new ArrayList<>();
    private static List<Checkers> blackCheckersTimeOut = new ArrayList<>();

    static boolean whiteEnd = true;
    static boolean blackEnd = false;

    private static List<List<Checkers>> checkersPosition= new ArrayList<>();

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

    public static void initialize(){
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
        doublingCubePosition = 1;
        doubleOffered = false;
        offeringPlayer = null;
        receivingPlayer = null;
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

    public static void options(int dice1, int dice2, Boolean p1turn, Player playerRolling) {
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
            int checkIfEndGame = 0;
            if (!whiteCheckersTimeOut.isEmpty() && checkNextTurn == 0) {
                legalMoves = optionOutBoard(dice1, dice2, player, whiteCheckersTimeOut);
            } else if (!blackCheckersTimeOut.isEmpty() && checkNextTurn == 1) {
                legalMoves = optionOutBoard(dice1, dice2, player, blackCheckersTimeOut);
            } else if ((whiteEnd && !p1turn)|| (blackEnd && p1turn)) {
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
            char userInput = scanner.next().toUpperCase().charAt(0);

            if (userInput >= 'A' && userInput < 'A' + legalMoves.size()) {
                Move selectedMove = legalMoves.get(userInput - 'A');
                System.out.println(checkIfEndGame + " AAAA ");
                if(checkIfEndGame==1){
                    applyEndMoves(selectedMove);
                    int diceUsed = abs(selectedMove.source-selectedMove.destination);
                    Actions.calculatePipCount(diceUsed, playerRolling);
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
                    applyMove(selectedMove);
                    int sourcePos = selectedMove.source;
                    // For white checker outside the board
                    int numberOfMoveMade = abs(sourcePos - selectedMove.destination);
                    if (numberOfMoveMade > 12) {
                        sourcePos = 24;
                    }
                    totalPlacesToMove -= numberOfMoveMade;
                    Actions.calculatePipCount(totalPlacesToMove, playerRolling);
                    if (dice1 == numberOfMoveMade && totalPlacesToMove == dice2) {
                        dice1 = 0;
                    } else if (dice2 == numberOfMoveMade && totalPlacesToMove == dice1) {
                        dice2 = 0;
                    }
                }
                Actions.getPipCount(playerRolling);
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
        System.out.println(removedChecker);
        if(color==colors.White){
            whiteOutCheckers.add(removedChecker);
        }else{
            blackOutCheckers.add(removedChecker);
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

    public static boolean isGameOver() {
        return (whiteOutCheckers.size() ==15 || blackOutCheckers.size() == 15 );
    }

    public static int determineWinner() {
        return (whiteOutCheckers.size() == 15) ? 1 : 2;
    }

    public static boolean isDoubleOffered() {
        return doubleOffered;
    }

    public static void displayDoublingCube() {
        System.out.println("Doubling Cube: Position " + doublingCubePosition +
                (doubleOffered ? (" Offered by: " + offeringPlayer.getName()) : ""));
    }

    public static void doubleOffer(Player offeringPlayer, Player receivingPlayer) {
        doubleOffered = true;
        BackgammonBoard.offeringPlayer = offeringPlayer;
        BackgammonBoard.receivingPlayer = receivingPlayer;
        System.out.println(offeringPlayer.getName() + " offers a double to " + receivingPlayer.getName() + ".");
    }

    public static void acceptDouble() {
        doubleOffered = false;
        doublingCubePosition *= 2; // Double the current position
        int currentStake = (int) Math.pow(2, doublingCubePosition - 1); // 2^(position-1) for the current stake
        System.out.println(receivingPlayer.getName() + " accepts the double. The stake is now " + currentStake + ".");
    }

    public static void refuseDouble() {
        doubleOffered = false;
        int currentStake = 1 << (doublingCubePosition - 1); // 2^(position-1)
        System.out.println(receivingPlayer.getName() + " refuses the double. " +
                offeringPlayer.getName() + " wins the current stake.");
        offeringPlayer.incrementMatchScore();
    }

    public static List<Move> getLegalEndGameMoves(int dice1, int dice2, int player, int moreMoves) {
        List<Move> legalMoves = new ArrayList<>();
        int finalPos = 0;
        colors color=colors.Black;
        int startLoop = player==1?18:0;
        int endLoop = player==1?24:6;
        for (int source = startLoop; source < endLoop; source++) {
            if(finalPos== 0 && !checkersPosition.get(source).isEmpty()
                    && getColorPlayer(checkersPosition.get(source).get(0).getColor()) == player){
                color = checkersPosition.get(source).get(0).getColor();
                finalPos = color==colors.Black?24:-1;
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
