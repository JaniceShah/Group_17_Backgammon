import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackgammonBoard {

    public enum colors {Black, White};

    public static int positionsNumber = 24;

    private static List<List<Checkers>> checkersPosition= new ArrayList<>();

    public static void display() {
        System.out.println("  24 23 22 21 20 19 18 |BAR| 17 16 15 14 13 12 11");
        System.out.println("+-------------------+---+-------------------+");

        for (int row = 0; row < 5; row++) {
            System.out.print("| ");
            for (int i = 0; i < 6; i++) {
                System.out.print("X  ");
            }
            System.out.print("|   | ");
            for (int i = 0; i < 6; i++) {
                System.out.print("O  ");
            }
            System.out.println("|");
        }

        System.out.println("|                   |BAR|                   |");

        for (int row = 0; row < 5; row++) {
            System.out.print("| ");
            for (int i = 0; i < 6; i++) {
                System.out.print("O  ");
            }
            System.out.print("|   | ");
            for (int i = 0; i < 6; i++) {
                System.out.print("X  ");
            }
            System.out.println("|");
        }

        System.out.println("+-------------------+---+-------------------+");
        System.out.println("1  2  3  4  5  6  7 |BAR| 8  9 10 11 12 13 14");
    }

    public static void initialize(){
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
}
