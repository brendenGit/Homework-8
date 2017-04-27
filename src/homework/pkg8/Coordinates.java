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
public class Coordinates {
    public int x;
    public int y;
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
        public Coordinates(Coordinates c){
        this.x = c.x;
        this.y = c.y;
    }
    
    public Coordinates(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
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
        final Coordinates other = (Coordinates) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    public String print(){
        return this.y + " " + this.x;
    }
    
}
