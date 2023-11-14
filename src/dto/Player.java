package dto;

public class Player {

    private static final int STARTING_PIP_COUNT = 167;

    private String name;
    private int score;

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

    public Player() {
        this.score = STARTING_PIP_COUNT;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", score=" + score;
    }
}