public class BackgammonBoard {
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
}
