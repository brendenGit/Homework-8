/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;

/**
 * Homework 8
 * Authors: Tyler Allen, Brenden Arias
 * Coordinates.java
 */
public class Coordinates {

    /**
     * X coordinate (column)
     */
    public int x;

    /**
     * Y coordinate (row)
     */
    public int y;
    
    /**
     * Constructs a Coordinates object at the given location
     * @param x X position (column)
     * @param y Y position (row)
     */
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructs a copy of another Coordinates object
     * @param c Coordinate to be copied
     */
    public Coordinates(Coordinates c){
        this.x = c.x;
        this.y = c.y;
    }
    
    /**
     * Default constructor (0,0)
     */
    public Coordinates(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
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
    
    /**
     * Returns a string representation of this coordinate.
     * @return String representation of Coordinate object
     */
    public String print(){
        return this.y + " " + this.x;
    }
    
}
