/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

import java.util.Objects;

/**
 * Homework 8
 * Authors: Tyler Allen, Brenden Arias
 * Block.java
 */
public class Block {
    
    final int h; //Height
    final int w; //Width
    private Coordinates c; //Block position
    
    /**
     * Constructs a block based on the given values
     * @param h Height of the block
     * @param w Width of the block
     * @param y Y position of the block (row)
     * @param x X position of the block (column)
     */
    public Block(int h, int w, int y, int x){
        this.h = h;
        this.w = w;
        c = new Coordinates();
        this.place(x, y);
        //k = 0;
    }
    
    /**
     * Constructs a block based on a previous block
     * @param newBlock The block to be copied
     */
    public Block(Block newBlock){
        this.h = newBlock.h;
        this.w = newBlock.w;
        c = new Coordinates(newBlock.getCoordinates());
        //this.k = newBlock.k;
    }
    
    //Change the position of the block

    /**
     * Sets the location of the block.
     * @param x X position (column)
     * @param y Y position (row)
     */
    public void place(int x, int y){
        if (x >= 0) this.c.x = x;
        if (y >= 0) this.c.y = y;
    }
    
    /**
     * Sets the location of a block.
     * @param c Coordinates of where to place the block
     */
    public void place(Coordinates c){
        if (c.x < 0 || c.y < 0)
            return;
        this.c = new Coordinates(c);
    }
    
    /**
     * Returns the X position of the block.
     * @return X position (column)
     */
    public int getX(){return this.c.x;}

    /**
     * Returns the Y position of the block.
     * @return X position (row)
     */
    public int getY(){return this.c.y;}

    /**
     * Returns the coordinates of the block.
     * @return Coordinates object for the block
     */
    public Coordinates getCoordinates(){return new Coordinates(this.c);}
    
    /**
     * Checks if the given block overlaps with this block.
     * @param newBlock Block to be checked for overlap
     * @return If the blocks overlap
     */
    public boolean overlap(Block newBlock){
        for (int i = newBlock.getX(); i < (newBlock.getX() + newBlock.w); i++){
            for (int j = newBlock.getY(); j < (newBlock.getY() + newBlock.h); j++){
                if (this.contains(new Coordinates(i, j)))
                    return true;
            }
        }
        
        return false;
    }
    
    private boolean contains(Coordinates newC){
        if ((newC.x >= this.getX()) && (newC.x <= this.getX() + this.w - 1))
            if ((newC.y >= this.getY()) && (newC.y <= this.getY() + this.h - 1))
                return true;
        
        return false;
    }
    
    /**
     * Returns the block as a string
     * @return String representation of the block
     */
    public String print(){
        return this.h + " " + this.w + " " + this.getY() + " " + this.getX();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.h;
        hash = 17 * hash + this.w;
        hash = 17 * hash + this.c.x;
        hash = 17 * hash + this.c.y;
        return hash;
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
        if (!this.c.equals(other.c)) {
            return false;
        }
        return true;
    }
}
