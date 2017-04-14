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
    
    private int h;
    private int w;
    LinkedList<Block> blocks = new LinkedList<>();

    public Tray(LinkedList<String> lines){
        boolean firstLine = true;
        for (String s: lines){
            if (firstLine){
                this.h = Integer.parseInt(s.substring(0, 1));
                this.w = Integer.parseInt(s.substring(1, 2));
            }
            else{
                int blockH = Integer.parseInt(s.substring(0, 1));
                int blockW = Integer.parseInt(s.substring(1, 2));
                int blockX = Integer.parseInt(s.substring(2, 3));
                int blockY = Integer.parseInt(s.substring(3, 4));
                placeBlock(new Block(blockH, blockW), blockX, blockY);
            }
                
        }
    }
    
    private void buildTray(){}
    
    public void move(int x, int y){
        
    }
    
    public void placeBlock(Block newBlock, int x, int y){
        
    }

    @Override
    public boolean equals(Object obj) {

        return true;
    }
    
    
}
