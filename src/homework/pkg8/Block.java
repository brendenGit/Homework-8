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
    private int k; //key
    private Coordinates c; //Block position
    //All variables for a block
    //Not sure if block should store position
    //or if tray should store position.
    //Also don't know if keys are necessary.
    
    //Constructor. Takes in height, width, and initial position
    public Block(int h, int w, int y, int x){
        this.h = h;
        this.w = w;
        c = new Coordinates();
        this.place(x, y);
        //k = 0;
    }
    
    public Block(Block newBlock){
        this.h = newBlock.h;
        this.w = newBlock.w;
        c = new Coordinates(newBlock.getCoordinates());
        //this.k = newBlock.k;
    }
    
    //Change the position of the block
    public void place(int x, int y){
        if (x >= 0) this.c.x = x;
        if (y >= 0) this.c.y = y;
    }
    
    public void place(Coordinates c){
        if (c.x < 0 || c.y < 0)
            return;
        this.c = new Coordinates(c);
    }
    
    public int getX(){return this.c.x;}
    public int getY(){return this.c.y;}
    public Coordinates getCoordinates(){return this.c;}
    public void setKey(int newKey){k = newKey;}
    public int getKey(){return k;}
    
    public boolean overlap(Block newBlock){
        for (int i = newBlock.getX(); i < (newBlock.getX() + newBlock.w); i++){
            for (int j = newBlock.getY(); j < (newBlock.getY() + newBlock.h); j++){
                if (this.contains(new Coordinates(i, j)))
                    return true;
            }
        }
        
        return false;
    }
    
    public boolean contains(Coordinates newC){
        if ((newC.x >= this.getX()) && (newC.x <= this.getX() + this.w - 1))
            if ((newC.y >= this.getY()) && (newC.y <= this.getY() + this.h - 1))
                return true;
        
        return false;
    }
    
    public String print(){
        return this.h + " " + this.w + " " + this.getY() + " " + this.getX();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.h;
        hash = 67 * hash + this.w;
        hash = 67 * hash + Objects.hashCode(this.c);
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
