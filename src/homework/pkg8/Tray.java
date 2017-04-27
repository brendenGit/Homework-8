/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

import java.util.*;

/**
 *
 * @author safar
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
        blocks = oldTray.getBlocks();
        lastMove = oldTray.prevMoves();
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
        
        return returnMoves;
    }
    
    public void move(int key){
        //TODO logic to move a block on the board
        Block toMove = null;
        int i = 0;
        while (i < blocks.size() && toMove == null){
            if (blocks.get(i).getKey() == key){
                toMove = blocks.remove(i);
            }
            i++;
        }
    }
    
    public void move(Move move){
        //TODO logic to move a block on the board
        boolean found = false;
        int i = 0;
        while (i < blocks.size() && !found){
            if (blocks.get(i).equals(move.block)){
                blocks.remove(i);
                found = true;
            }
            i++;
        }
        if (found){
            move.block.place(new Coordinates(move.block.getX() + move.c.x, move.block.getY() + move.c.y));
            this.placeBlock(move.block);
            this.addMove(move);
        }
        else
            System.out.println("Invalid move, no block found");
    }
    
    public void addMove(Move move){
        if (this.lastMove != null)
            move.prevMove = lastMove;
        
        lastMove = move;
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
        if (!Objects.equals(this.blocks, other.blocks)) {
            return false;
        }
        return true;
    }
    
    
}
