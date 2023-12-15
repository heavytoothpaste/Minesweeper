import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.util.Random;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Grid extends JFrame{
    private int rows;
    private int cols;
    private int numMines;
    private JPanel GUIboard;
    private Square[][] board;

    public Grid(int r, int c, int mine){
        //instance variables
        this.rows = r;
        this.cols = c;
        this.board = new Square[r][c];
        this.numMines = mine;
        this.GUIboard = new JPanel(new GridLayout(this.rows, this.cols));
        // GUI stuff
        this.setSize(40*this.rows, 40*this.cols);
        this.setResizable(false);

        // Creates a new "blank" grid
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                Square but = new Square(i, j);
                but.setMargin(new Insets(0, 0, 0, 0));
                this.board[i][j] = but;
            }
        }
        //randomize mines
        randomizeMines();

        // finishes setting up the board.
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                this.board[i][j].setNumAdjacentMines(setUpMines(i, j));
            }
        }
        
        // this.GUIboard.setSize(25*this.rows, 25*this.cols);
        // this.GUIboard.setPreferredSize(new Dimension(25*this.rows, 25*this.cols));

        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.cols; j++){
                
                // button.setSize(25, 25);
                this.board[i][j].addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        if(SwingUtilities.isLeftMouseButton(e)){
                            Square s = (Square) e.getSource();
                            if(s.isEnabled()){
                                revealSquare(s.getXIndex(), s.getYIndex());
                            }
                        }
                        else if (SwingUtilities.isRightMouseButton(e)){
                            Square s = (Square) e.getSource();
                            if (s.getIsFlagged()){
                                System.out.println("Unflagged");
                                s.setText("");
                            }
                            else{
                                s.setIsFlagged(true);
                                s.setText("F"); 
                            }
                            System.out.println("Flagged");
                        }
                    }
                });
                this.GUIboard.add(this.board[i][j]);
            }
        }
        
        this.add(this.GUIboard);
        
    }
    // Randomizes mines
    public void randomizeMines(){
        int currentMineCount = 0;
        Random rand = new Random();
        while(currentMineCount < this.numMines){
            int whichRow = rand.nextInt(this.rows);
            int whichCol = rand.nextInt(this.cols);
            if(this.board[whichRow][whichCol].isMine()==false){
                System.out.println("Mine added at board[" + whichRow + "][" + whichCol + "]"  );
                this.board[whichRow][whichCol].setIsMine(true);
                currentMineCount++;
            }
        }
    }
    // public void revealSquare(Square s){
    //     System.out.println(s.getXIndex() + " " + s.getYIndex());
    //     if(s.isMine()){
    //         s.setEnabled(false);
    //         s.setText("M");
            
    //     }
    //     else{
    //         recursiveExpansion(s.getXIndex(), s.getYIndex());
    //     }
    // }
    private void revealSquare(int row, int column) {
        // Check if the square is already revealed
        if (!this.board[row][column].isEnabled() || !this.board[row][column].getText().equals("")) {
            return;
        }
        if(!this.board[row][column].isMine()){
            // checks if square is a 0 square
            if(this.board[row][column].getNumAdjacentMines()==0){
                Border b = new LineBorder(Color.lightGray, 2);
                this.board[row][column].setBorder(b);
                this.board[row][column].setEnabled(false);
        
                // Recursively reveal neighbors
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (row + i >= 0 && row + i < this.rows && column + j >= 0 && column + j < this.cols) {
                            revealSquare(row + i, column + j);
                        }
                    }
                }
            }
            // if has a number then stop recursing. 
            else{
                this.board[row][column].setText("" + this.board[row][column].getNumAdjacentMines());
                
                if(this.board[row][column].getNumAdjacentMines()==1){
                    this.board[row][column].setForeground(Color.BLUE);
                }
                else if(this.board[row][column].getNumAdjacentMines()==2){
                    this.board[row][column].setForeground(Color.GREEN);
                }
                else if(this.board[row][column].getNumAdjacentMines()==3){
                    this.board[row][column].setForeground(Color.RED);
                }
                else if(this.board[row][column].getNumAdjacentMines()==4){
                    this.board[row][column].setForeground(Color.decode("#000435"));
                }
                else if(this.board[row][column].getNumAdjacentMines()==5){
                    this.board[row][column].setForeground(Color.decode("#800000"));
                }
                else if(this.board[row][column].getNumAdjacentMines()==6){
                    this.board[row][column].setForeground(Color.CYAN);
                }
                else if(this.board[row][column].getNumAdjacentMines()==7){
                    this.board[row][column].setForeground(Color.BLACK);
                }
                else if(this.board[row][column].getNumAdjacentMines()==8){
                    this.board[row][column].setForeground(Color.LIGHT_GRAY);
                }
                Border b = new LineBorder(Color.black, 2);
                this.board[row][column].setBorder(b);
                this.board[row][column].revalidate();
                this.board[row][column].repaint();
                this.GUIboard.revalidate();
                this.GUIboard.repaint();
            }
            
        }
        
        

        // if (this.board[row][column].getNumAdjacentMines() != -1) {
        //     if(this.board[row][column].getNumAdjacentMines()!=0){
        //         return;
        //     }
        //     else{
        //         // Regular square with adjacent mines
        //         this.board[row][column].setText("" + this.board[row][column].getNumAdjacentMines());
        //         this.board[row][column].setEnabled(false);
        
        //         // Recursively reveal neighbors
        //         for (int i = -1; i <= 1; i++) {
        //             for (int j = -1; j <= 1; j++) {
        //                 if (row + i >= 0 && row + i < this.rows && column + j >= 0 && column + j < this.cols) {
        //                     revealSquare(row + i, column + j);
        //                 }
        //             }
        //         }
        //     }
        // } 
        else {
            JOptionPane jop = new JOptionPane();
            JOptionPane.showMessageDialog(jop, "You lost");
            revealAllSquares();
        }
    }
    
    

            
            



    // private void recursiveExpansion(int xpos, int ypos){
    //     this.board[xpos][ypos].setText("" + this.board[xpos][ypos].getNumAdjacentMines());
    //     this.board[xpos][ypos].setEnabled(false);
    //     for (int i = -1; i <=1; i++){
    //         for (int j = -1; j <=1; j++){
    //             if (xpos + i >= 0 && xpos + i < rows && ypos + j >= 0 && ypos + j < ypos){
    //                 recursiveExpansion(xpos+i, ypos+j);
    //             }
    //         }
    //     }
        
        // if(xpos > 0 && ypos > 0 && this.board[xpos][ypos].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos-1, ypos-1);
        // }
        // else if(xpos > 0 && this.board[xpos-1][ypos].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos-1, ypos);
        // }
        // else if(ypos > 0 && this.board[xpos][ypos-1].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos, ypos-1);
        // }
        
        // else if(xpos < this.rows-1 && ypos < this.cols-1 && this.board[xpos+1][ypos+1].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos+1, ypos+1);
        // }
        // else if(xpos < this.rows-1 && this.board[xpos+1][ypos].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos+1, ypos);
        // }
        // else if(ypos < this.cols-1 && this.board[xpos][ypos+1].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos, ypos+1);
        // }
        

        // else if(xpos > 0 && ypos < this.cols-1 && this.board[xpos-1][ypos+1].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos-1, ypos+1);
        // }
        // else if(xpos < this.rows-1 && ypos > 0 && this.board[xpos+1][ypos-1].getNumAdjacentMines()!=-1){
        //     recursiveExpansion(xpos+1, ypos-1);
        // }
        
    //     System.out.println("Recursivem etho called");
    // }
    public int setUpMines(int x, int y){
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xx = x + i;
                int yy = y + j;

                if (xx >= 0 && xx < this.rows && yy >= 0 && yy < this.cols && board[xx][yy].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }
    private void revealAllSquares(){
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.cols; j++){
                if(!this.board[i][j].isMine()){
                    this.board[i][j].setEnabled(false);
                    Border b = new LineBorder(Color.lightGray, 2);
                    this.board[i][j].setBorder(b);
                    this.board[i][j].setText("" + this.board[i][j].getNumAdjacentMines());
                }
                else{
                    Border b = new LineBorder(Color.lightGray, 2);
                    this.board[i][j].setBorder(b);
                    this.board[i][j].setText("M");
                }
            }
        }
    }
}
