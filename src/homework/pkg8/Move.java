/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

import java.util.LinkedList;

/**
 * Homework 8
 * Authors: Tyler Allen, Brenden Arias
 * Move.java
 */
public class Move {
    
    /**
     * Block to be moved
     */
    public Block block;

    /**
     * Amount to ALTER blocks position
     */
    public Coordinates c;

    /**
     * Previously made move
     */
    public Move prevMove = null;
    
    /**
     * Constructs a Move object given a block and coordinate.
     * @param block Block to be moved
     * @param c Amount to DISPLACE the block
     */
    public Move(Block block, Coordinates c){
        this.block = new Block(block);
        this.c = new Coordinates(c);
    }

    /**
     * Constructs a copy of a given Move object.
     * @param newMove Object to be copied
     */
    public Move(Move newMove){
        this.block = new Block(newMove.block);
        this.c = new Coordinates(newMove.c);
        if (newMove.prevMove != null)
            this.prevMove = new Move(newMove.prevMove);
    }
    
    /**
     * Returns a string containing the movement data.
     * @return String representation of the move
     */
    public String print(){
        String toReturn = "";
        if (prevMove != null)
            toReturn += prevMove.print();
        if (this.block != null)
            toReturn += (this.block.getCoordinates().print() + " "
                    + (this.c.y + this.block.getCoordinates().y + " ")
                    + (this.c.x + this.block.getX()) + "\n");
        
        return toReturn;
    }
    
    /**
     * Returns all previous moves including this in order from oldest to newest
     * @return List of previous moves
     */
    public LinkedList<Move> getMoves(){
        LinkedList<Move> returnMoves = new LinkedList<>();
        if (this.prevMove != null)
            returnMoves.addAll(this.prevMove.getMoves());
        
        returnMoves.add(this);
        
        return returnMoves;
    }
    
    /**
     * Returns number of previous moves.
     * @return Number of performed moves
     */
    public int size(){
        int i = 0;
        Move tempMove = this;
        
        while (tempMove != null){
            tempMove = tempMove.prevMove;
            i++;
        }
        
        return i;
    }
    
}
