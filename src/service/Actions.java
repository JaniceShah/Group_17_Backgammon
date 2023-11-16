package service;

import dto.Player;

import java.util.Random;
import java.util.Scanner;

public class Actions {


    public static String roll = "roll";
    public static String quit = "quit";
    public static String hint = "hint";
    public static String pip = "pip";

    public static int rollDie() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
    public static void rollChance(Player player){
        int dice1 = rollDie();
        int dice2 = rollDie();

        System.out.println(player.getName() + " rolled a " + dice1 + " and a " + dice2);

        int totalDice = dice1 + dice2;
        Scanner scanner = new Scanner(System.in);
        BackgammonBoard.display();
        player.setScore(player.getScore() - totalDice);

        System.out.println("Total of the dice for " + player.getName() + ": " + totalDice
                +"\npip count:"+ player.getScore());
    }
}
