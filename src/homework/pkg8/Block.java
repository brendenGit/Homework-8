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
    
    final int h; //Height
    final int w; //Width
    private int k; //key
    private int x; //X pos, right/left
    private int y; //Y pos, up/down
    //All variables for a block
    //Not sure if block should store position
    //or if tray should store position.
    
    //Constructor. Takes in height, width, and initial position
    public Block(int h, int w, int y, int x){
        this.h = h;
        this.w = w;
        this.place(x, y);
        //k = 0;
    }

    public Block(int k, int h, int w, int y, int x){
        this.h = h;
        this.w = w;
        this.place(x, y);
        this.k = k;
    }
    
    //Change the position of the block
    public void place(int x, int y){
        if (x >= 0) this.x = x;
        if (y >= 0) this.y = y;
    }
    
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void setKey(int newKey){k = newKey;}
    public int getKey(){return k;}
    
    public boolean overlap(Block newBlock){
        for (int i = newBlock.getX(); i < newBlock.getX() + newBlock.w; i++){
            if (i >= this.getX() && i < this.getX() + this.w - 1){
                for (int j = newBlock.getY(); i < newBlock.getY() + newBlock.h; i++){
                    if (j >= this.getY() && j < this.getY() + this.h - 1){
                        System.out.println("Overlap:");
                        this.print();
                        newBlock.print();
                        System.out.println("--------");
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public void print(){
        System.out.println(this.h + " " + this.w + " " + this.getY() + " " + this.getX());
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
