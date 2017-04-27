/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

import java.util.*;

/**
 * Homework 8
 * Authors: Tyler Allen, Brenden Arias
 * Tray.java
 */
public class Tray {
    
    private int h; //Height
    private int w; //Width
    private int currentKey = 0;
    private LinkedList<Block> blocks = new LinkedList<>();
    //List of all blocks on the tray.
    private Move lastMove = new Move();

    public Tray(LinkedList<String> lines){
        buildTray(lines);
    }
    
    public Tray(Tray oldTray){
        h = oldTray.h;
        w = oldTray.w;
        for (Block b: oldTray.blocks)
            blocks.add(new Block(b));
        if (oldTray.lastMove != null)
            if (oldTray.lastMove.block != null)
                lastMove = new Move(oldTray.lastMove);
    }
    
    //Reads through the given list of lines and converts
    //into blocks and dimensions.
    private void buildTray(LinkedList<String> lines){
        
        String firstLine = lines.pop();
        Scanner in = new Scanner(firstLine);
                this.h = in.nextInt();
                this.w = in.nextInt();
        for (String s: lines){
            in = new Scanner(s);
            int blockH = in.nextInt();
            int blockW = in.nextInt();
            int blockY = in.nextInt();
            int blockX = in.nextInt();
            placeBlock(new Block(blockH, blockW, blockY, blockX));
        }
    }
    
    public int getH(){return this.h;}
    public int getW(){return this.w;}
    
    public LinkedList<Move> getMoves(){
        LinkedList<Move> returnMoves = new LinkedList<>();
        for (Block b: blocks){
            Block tempBlock = new Block(b);
            
            tempBlock.place(tempBlock.getX(), tempBlock.getY() + 1);
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, new Coordinates(0, 1)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX(), tempBlock.getY() - 1);
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, new Coordinates(0, -1)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX() + 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, new Coordinates(1, 0)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX() - 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, new Coordinates(-1, 0)));
        }
        
        if (this.lastMove.block != null)
            for (Move m: returnMoves)
                m.prevMove = new Move(this.lastMove);
        
        return returnMoves;
    }
    
    private void move(Move move){        
        boolean found = false;
        int i = 0;
        while (i < blocks.size() && !found){ //Check if the block exists
            if (blocks.get(i).equals(move.block)){
                blocks.remove(i); //If so, remove it from the list
                found = true;
            }
            i++;
        }
        if (found){ //If we've found the block, move it and place it
            Block tempBlock = new Block(move.block);
            tempBlock.place(new Coordinates(move.block.getX() + move.c.x, move.block.getY() + move.c.y));
            this.placeBlock(tempBlock);
        }
        else
            System.out.println("Invalid move, no block found");
    }
    
    public void addMove(Move move){
        if (move.block != null){ //If the move isn't valid
            if (this.lastMove.block != null){ //If we already have a previous move
                if (move.prevMove != null) //If the previous move has a previous move
                    this.addMove(move.prevMove); //Then do that move first
                Move tempMove = new Move(move); //Copy the new move
                tempMove.prevMove = new Move(this.lastMove); //And set it as our last move
                this.lastMove = tempMove; //Set our last move as this new one
            }
            else{ //If we don't have a previous move
                if (move.prevMove != null) //If this move has a previous move
                    this.addMove(move.prevMove); //Do that one first
                this.lastMove = new Move(move); //Set our last move as this new move
            }
            
            this.move(this.lastMove); //Execute the move action
        }
    }
    
    public Move prevMoves(){return this.lastMove;}
    
    //Add a block to the board
    public void placeBlock(Block newBlock){
        if (this.blockCollision(newBlock))
            return;
        
        newBlock.setKey(currentKey++);
        blocks.add(new Block(newBlock));
    }
    
    public Block removeBlock(Block newBlock){
        blocks.remove(newBlock);
        return newBlock;
    }
    
    public Block removeBlock(int key){
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i).getKey() == key)
                return blocks.remove(i);
        return null;
    }
    
    public boolean blockCollision(Block newBlock){
        //Check if the block is outside the bounds of the board
        if (newBlock.getX() + newBlock.w > this.w)
            return true;
        if (newBlock.getY() + newBlock.h > this.h)
            return true;
        if (newBlock.getX() < 0)
            return true;
        if (newBlock.getY() < 0)
            return true;
        
        //Check if a block already occupies the space
        for (Block b: blocks){
            if (b.overlap(newBlock))
                return true;
        }
        
        return false;
    }
    
    public LinkedList<Block> getBlocks(){
        return blocks;
    }
    
    public void addBlocks(LinkedList<Block> toAdd){
        for (Block b: toAdd)
            placeBlock(b);
    }

    //Return if the board contains a particular block (solution)
    public boolean contains(Block newBlock){
        return blocks.contains(newBlock);
    }
    public boolean contains(LinkedList<Block> newBlocks){
        return blocks.containsAll(newBlocks);
    }
    
    //Outputs the contents of the board
    public void print(){
        System.out.println(this.h + " " + this.w);
        for (Block b: blocks)
            System.out.println(b.print());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.h;
        hash = 17 * hash + this.w;
        hash = 17 * hash + Objects.hashCode(this.blocks);
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
        final Tray other = (Tray) obj;
        if (this.h != other.h) {
            return false;
        }
        if (this.w != other.w) {
            return false;
        }
        for (int i = 0; i < this.blocks.size(); i++){
            if (!this.blocks.contains(other.blocks.get(i)))
                return false;
            if (!other.blocks.contains(this.blocks.get(i)))
                return false;
        }
        
        return true;
    }
    
    
}
