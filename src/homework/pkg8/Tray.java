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
    private LinkedList<Block> blocks = new LinkedList<>();
    //List of all blocks on the tray.
    private Move lastMove = null;

    /**
     * Builds Tray object based on a list of lines.
     * @param lines List of lines containing tray data
     * @see #buildTray(java.util.LinkedList) 
     */
    public Tray(LinkedList<String> lines){
        buildTray(lines);
    }
    
    /**
     * Builds a Tray object based on a previous tray object.
     * @param oldTray Object to copy data from
     */
    public Tray(Tray oldTray){
        h = oldTray.h;
        w = oldTray.w;
        for (Block b: oldTray.blocks)
            blocks.add(new Block(b));
        if (oldTray.lastMove != null)
            if (oldTray.lastMove.block != null)
                lastMove = new Move(oldTray.lastMove);
    }
    
    /**
     * Takes in a list of lines in order to construct a tray object.
     * @param lines List of lines containing tray data
     * @see #placeBlock(homework.pkg8.Block) 
     */
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
    
    /**
     * Returns height of Tray
     * @return Height
     */
    public int getH(){return this.h;}

    /**
     * Returns width of Tray
     * @return Width
     */
    public int getW(){return this.w;}
    
    /**
     * Returns a list of possible movements.
     * @return List of available moves
     * @see #blockCollision(homework.pkg8.Block, homework.pkg8.Block) 
     */
    public LinkedList<Move> getMoves(){
        LinkedList<Move> returnMoves = new LinkedList<>();
        for (Block b: blocks){
            Block tempBlock = new Block(b);
            
            //Adjust block position by 1. Check if it's a valid move.
            tempBlock.place(tempBlock.getX(), tempBlock.getY() + 1);
            if (!this.blockCollision(tempBlock, b))
                returnMoves.add(new Move(b, new Coordinates(0, 1)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX(), tempBlock.getY() - 1);
            if (!this.blockCollision(tempBlock, b))
                returnMoves.add(new Move(b, new Coordinates(0, -1)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX() + 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock, b))
                returnMoves.add(new Move(b, new Coordinates(1, 0)));
            
            tempBlock = new Block(b);
            tempBlock.place(tempBlock.getX() - 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock, b))
                returnMoves.add(new Move(b, new Coordinates(-1, 0)));
        }
        
        if (this.lastMove != null)
            for (Move m: returnMoves)
                m.prevMove = new Move(this.lastMove);
        
        return returnMoves;
    }
    
    /**
     * Preform a move action using a provided movement
     * @param move A move to be performed
     */
    public void addMove(Move move){
        if (move.block != null){ //If the move isn't valid
            if (this.lastMove != null){ //If we already have a previous move
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
    
    /**
     * Returns a list of movements that have been made on this tray.
     * @return List of performed moves
     */
    public Move prevMoves(){return this.lastMove;}
    
    /**
     * Places a block on the tray if it is a valid block. Does nothing if it 
     * would create an invalid board.
     * @param newBlock A new block to be placed on the tray
     */
    public void placeBlock(Block newBlock){
        if (this.blockCollision(newBlock))
            return;
        
        blocks.add(new Block(newBlock));
    }
    
    /**
     * Removes a block from the tray and returns it.
     * @param newBlock Block to be removed
     * @return Block removed
     */
    public Block removeBlock(Block newBlock){
        blocks.remove(newBlock);
        return newBlock;
    }
    
    private boolean blockCollision(Block newBlock, Block currentBlock){
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
            if (b.overlap(newBlock) && !b.equals(currentBlock))
                return true;
        }
        
        return false;
    }
    
    private boolean blockCollision(Block newBlock){
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
    
    /**
     * Returns a list of blocks on the tray
     * @return List of stored blocks
     */
    public LinkedList<Block> getBlocks(){
        return blocks;
    }
    
    /**
     * Check if the tray contains a given block
     * @param newBlock Block to check for
     * @return If the tray contains the given block
     */
    public boolean contains(Block newBlock){
        return blocks.contains(newBlock);
    }

    /**
     * Checks if the tray contains multiple blocks from a list
     * @param newBlocks Blocks to be checked for
     * @return If the tray contains the given blocks
     * @see #contains(homework.pkg8.Block) 
     */
    public boolean contains(LinkedList<Block> newBlocks){
        return blocks.containsAll(newBlocks);
    }
    
    /**
     * Returns a string containing the contents of the board
     * @return String containing the tray data
     */
    public String print(){
        String toReturn;
        toReturn = this.h + " " + this.w + "\n";
        for (Block b: blocks)
            toReturn += b.print() + "\n";
        
        return toReturn;
    }
    
    /**
     * Checks if the tray contains a valid configuration of blocks
     * @return If tray is OK
     */
    public boolean isOK(){
        for (Block b: blocks)
            if (this.blockCollision(b, b))
                return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int[] sum = new int[blocks.size()];
        int i = 0;
        hash = 23 * hash + this.h;
        hash = 23 * hash + this.w;
        for (Block b: blocks){
            sum[i] = b.hashCode();
        }
        Arrays.sort(sum);
        for (Integer j: sum){
            hash = 23 * hash + j;
        }
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
