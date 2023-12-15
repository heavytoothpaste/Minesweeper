import javax.swing.*;
public class Minesweeper {
    public static void main(String[] args) {
        // JFrame frame = new JFrame("Minesweeper");
        Grid board = new Grid(15, 15, 30);
        board.setVisible(true);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
