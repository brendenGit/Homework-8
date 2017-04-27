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
    
    public Block block;
    public Coordinates c;
    public Move prevMove = null;
    
    public Move(Block block, Coordinates c){
        this.block = new Block(block);
        this.c = new Coordinates(c);
    }
    public Move(Move newMove){
        this.block = new Block(newMove.block);
        this.c = new Coordinates(newMove.c);
        if (newMove.prevMove != null)
            this.prevMove = new Move(newMove.prevMove);
    }
    public Move(){}
    
    public String print(){
        String toReturn = "";
        if (prevMove != null)
            toReturn += prevMove.print();
        if (this.block != null)
            toReturn += (this.block.print() + " " + this.c.print() + "\n");
        
        return toReturn;
    }
    
    public LinkedList<Move> getMoves(){
        LinkedList<Move> returnMoves = new LinkedList<>();
        if (this.prevMove != null)
            returnMoves.addAll(this.prevMove.getMoves());
        
        returnMoves.add(this);
        
        return returnMoves;
    }
    
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
