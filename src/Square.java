import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
public class Square extends JButton{
    // -1 is a mine, 0 is no mines around, positive integers are number of mines around.
    // private int numMines;
    private boolean isMine;
    private int adjacentMines;
    private int x;
    private int y;
    private boolean isFlagged;
    public Square(int xpos, int ypos){
        this.setForeground(Color.black);
        Border b = new LineBorder(Color.black, 4);
        this.setBackground(Color.gray);
        this.setBorder(b);
        this.setFont(new Font("Courier New", Font.BOLD, 22));
        this.setText("");
        this.isMine = false;
        this.x = xpos;
        this.y = ypos;
        this.isFlagged= false;
    }
    public void setBackgroundColor(Color c){
        this.setBackground(c);
    }
    

    public int getXIndex(){
        return this.x;
    }
    public int getYIndex(){
        return this.y;
    }
    // public int getValue(){
    //     return this.numMines;
    // }
    public boolean isMine(){
        return this.isMine;
    }
    public void setIsMine(boolean bool){
        this.isMine = bool;
    }
    public void setNumAdjacentMines(int num){
        this.adjacentMines = num;
    }
    public int getNumAdjacentMines(){
        if(this.isMine==true){
            return -1;
        }
        return this.adjacentMines;
    }
    public boolean getIsFlagged(){
        return this.isFlagged;
    }
    public void setIsFlagged(boolean x){
        this.isFlagged = x;
    }
}

