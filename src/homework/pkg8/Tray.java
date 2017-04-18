/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author safar
 */
public class Tray {
    
    private int h;
    private int w;
    LinkedList<Block> blocks = new LinkedList<>();

    public Tray(LinkedList<String> lines){
        buildTray(lines);
    }
    
    private void buildTray(LinkedList<String> lines){
        
        String firstLine = lines.pop();
        Scanner in = new Scanner(firstLine).useDelimiter("[^0-9]+");
                //reads in the dimensions of the board.
                this.h = in.nextInt();
                this.w = in.nextInt();
        for (String s: lines){
            in = new Scanner(s).useDelimiter("[^0-9]+");
                //For all other lines, record the values from the line into
                //a block object.
                int blockH = in.nextInt();
                int blockW = in.nextInt();
                int blockY = in.nextInt();
                int blockX = in.nextInt();
                placeBlock(new Block(blockH, blockW, blockY, blockX));
        }
    }
    
    public void move(int x, int y){
        
    }
    
    public void placeBlock(Block newBlock){
        for (Block b: blocks){
            //if (b.overlap(newBlock))
                //return;
        }
        blocks.add(newBlock);
    }

    public boolean contains(Block newBlock){
        for (Block b: blocks){
            if (newBlock == b)
                return true;
        }
        
        return false;
    }
    
    public void print(){
        System.out.println(this.h + " " + this.w);
        for (Block b: blocks)
            b.print();
    }
}
