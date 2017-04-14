/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

/**
 *
 * @author safar
 */
public class Block {
    
    int h;
    int w;
    int x;
    int y;
    
    public Block(int h, int w){
        this.h = h;
        this.w = w;
    }
    
    public void place(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}
