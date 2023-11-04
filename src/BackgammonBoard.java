import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackgammonBoard {

    public enum colors {Black, White};

    public static int positionsNumber = 24;

    private static List<List<Checkers>> checkersPosition= new ArrayList<>();

    public static void display() {
        for(int i =0;i<positionsNumber;i++){
            System.out.println(checkersPosition.get(i));
        }
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
