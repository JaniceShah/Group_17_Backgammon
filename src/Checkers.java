import java.util.ArrayList;
import java.util.List;

public class Checkers {

    private BackgammonBoard.colors color;

    public BackgammonBoard.colors getColor() {
        return color;
    }

    private int numberOfChecker;

    public void setColor(BackgammonBoard.colors color) {
        this.color = color;
    }

    public int getNumberOfChecker() {
        return numberOfChecker;
    }

    public void setNumberOfChecker(int numberOfChecker) {
        this.numberOfChecker = numberOfChecker;
    }

    public Checkers(BackgammonBoard.colors color, int numberOfChecker){
        setColor(color);
        setNumberOfChecker(numberOfChecker);
    }

    public Checkers(){

    }

    @Override
    public String toString() {
        if(color== BackgammonBoard.colors.Black){
            return " O ";
        }else{
            return " X ";
        }
    }

}
