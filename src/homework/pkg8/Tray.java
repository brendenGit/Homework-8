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
                //reads in the dimensions of the board.
                this.h = Integer.parseInt(firstLine.substring(0, 1));
                this.w = Integer.parseInt(firstLine.substring(1, 2));
        for (String s: lines){
                //For all other lines, record the values from the line into
                //a block object.
                int blockH = Integer.parseInt(s.substring(0, 1));
                int blockW = Integer.parseInt(s.substring(1, 2));
                int blockX = Integer.parseInt(s.substring(2, 3));
                int blockY = Integer.parseInt(s.substring(3, 4));
                placeBlock(new Block(blockH, blockW, blockX, blockY));
        }
    }
    
    public void move(int x, int y){
        
    }
    
    public void placeBlock(Block newBlock){
        for (Block b: blocks){
            if (b.overlap(newBlock))
                return;
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
    
    
    
    
}
