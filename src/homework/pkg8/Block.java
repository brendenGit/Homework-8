/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

/**
 *
 * @author Tyler
 */
public class Block {
    
    final int h;
    final int w;
    private int x;
    private int y;
    //All variables for a block
    //Not sure if block should store position
    //or if tray should store position.
    
    //Constructor. Takes in height, width, and initial position
    public Block(int h, int w, int x, int y){
        this.h = h;
        this.w = w;
        this.place(x, y);
    }
    
    //Change the position of the block
    public void place(int x, int y){
        if (x >= 0)
            this.x = x;
        if (y >= 0)
            this.y = y;
    }
    
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    
    public boolean overlap(Block newBlock){
        for (int i = newBlock.getX(); i < newBlock.getX() + newBlock.w; i++){
            if (i > this.getX() && i < this.getX() + this.w)
                return true;
        }
        for (int i = newBlock.getY(); i < newBlock.getY() + newBlock.h; i++){
            if (i > this.getY() && i < this.getY() + this.h)
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        if (this.h != other.h) {
            return false;
        }
        if (this.w != other.w) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
