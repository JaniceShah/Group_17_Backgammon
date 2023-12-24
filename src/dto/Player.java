package dto;

public class Player {

    public static final int STARTING_PIP_COUNT = 167;

    private String name;
    private int score;
    private int matchScore; // Added matchScore field

    public Player(String testPlayer, int i) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

   
    public int getMatchScore() {
        return matchScore;
    }

    public Player() {
        this.score = STARTING_PIP_COUNT;
        this.matchScore = 0; 
    }
}