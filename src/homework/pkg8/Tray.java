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

    public Tray(LinkedList<String> lines){
        buildTray(lines);
    }
    
    public Tray(Tray oldTray){
        h = oldTray.h;
        w = oldTray.w;
        blocks = oldTray.getBlocks();
    }
    
    //Reads through the given list of lines and converts
    //into blocks and dimensions.
    private void buildTray(LinkedList<String> lines){
        
        String firstLine = lines.pop();
        Scanner in = new Scanner(firstLine);
                //reads in the dimensions of the board.
                this.h = in.nextInt();
                this.w = in.nextInt();
        for (String s: lines){
            in = new Scanner(s);
                //For all other lines, record the values from the line into
                //a block object.
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
            Block tempBlock = b;
            
            tempBlock.place(tempBlock.getX(), tempBlock.getY() + 1);
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, tempBlock.getCoordinates()));
            
            tempBlock.place(tempBlock.getX(), tempBlock.getY() - 1);
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, tempBlock.getCoordinates()));
            
            tempBlock.place(tempBlock.getX() + 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, tempBlock.getCoordinates()));
            
            tempBlock.place(tempBlock.getX() - 1, tempBlock.getY());
            if (!this.blockCollision(tempBlock))
                returnMoves.add(new Move(b, tempBlock.getCoordinates()));
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
    
    //Add a block to the board
    public void placeBlock(Block newBlock){
        if (this.blockCollision(newBlock))
            return;
        
        newBlock.setKey(currentKey++);
        blocks.add(newBlock);
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
    
    //Outputs the contents of the board
    public void print(){
        System.out.println(this.h + " " + this.w);
        for (Block b: blocks)
            b.print();
    }
}
