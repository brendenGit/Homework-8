/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;
import java.io.*;
import java.util.*;

/**
 * Homework 8
 * Authors: Tyler Allen, Brenden Arias
 * Solver.java
 */
public class Solver {

    /**
     * @param args the command line arguments
     * Run config: -option1 file1 file2
     */
    public static void main(String[] args) {
        LinkedList<String> fileNames = new LinkedList<>();
        
        //Go through all args and filter out options from files
        for (String s: args){
            if (s.startsWith("-"))
                options(s.substring(2));
            else
                fileNames.add(s);
        }
        
        //If we don't have at least 2 files, exit
        if (fileNames.size() < 2){System.out.println("Not enough file arguments found"); return;}
        
        Tray tray = new Tray(openFile(fileNames.pop()));

        LinkedList<String> solutionLines = openFile(fileNames.pop());
        solutionLines.addFirst(tray.getH() + " " + tray.getW());
        Tray goal = new Tray(solutionLines);
        
        System.out.println("Tray");
        tray.print(); //Debug output tray and solution goal
        System.out.println("\nGoal Configuration");
        goal.print();
        System.out.println("---------------------\n");
        
        Move solutionMoves = solve(tray, goal);
        if (solutionMoves != null){
            System.out.println(solutionMoves.print());
            System.out.println("Number of Moves: " + solutionMoves.size());
        }
        
    }
    
    public static Move solve(Tray tray, Tray goal){
        
        LinkedList<Move> moves = new LinkedList<>(); //List of all possible moves
        LinkedList<Tray> prevTrays = new LinkedList<>(); //List of attempted trays

        Tray tempTray;
        
        //Initialize the move list with the initial moves
        moves.addAll(tray.getMoves());
        
        while (!moves.isEmpty()){
            tempTray = new Tray(tray); //Make a copy of the base tray
            tempTray.addMove(moves.pop()); //Move the tray with one of the possible moves
            if (!checkContains(prevTrays, tempTray)){ //If we've already tried this tray, ignore it
                if (tempTray.contains(goal.getBlocks())) //If it's the solution, return the move
                    return tempTray.prevMoves();
                else{
                    prevTrays.add(new Tray(tempTray)); //Otherwise, add this to the attempt list
                    moves.addAll(tempTray.getMoves()); //And add it's moves to the stack
                }
            }
        }
        
        //System.exit(1);
        System.out.println("No solution Found");
        return null;
    }
    
    public static boolean checkContains(LinkedList<Tray> trays, Tray tray){
        for (Tray t: trays) //Check if tray is contained in list
            if (tray.equals(t))
                return true;
        
        return false;
    }
    
    
    public static void options(String arg){
        //Debug print out inputted options.
        //TODO add logic for outputting options.
        System.out.println( "-option " + arg);
    }
    
    //Opens a file and returns the contents as a linked list.
    public static LinkedList<String> openFile(String fileName){
        LinkedList<String> outList = new LinkedList<>();
        try {
            Scanner scan = new Scanner(new File (fileName));
            
            while ((scan.hasNextInt())) {
                    outList.add(scan.nextLine());
            }
        } catch (IOException e) {
            System.out.println(e + "\nCould not open file: " + fileName);
            return null;
        }
        return outList;
    }
}
