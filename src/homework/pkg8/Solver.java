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
     * This program takes in a number of -options and two filenames containing
     * tray data. The first file should contain valid tray configuration code
     * and the second file should contain a valid solution goal.
     * <p>
     * If the program is given the -h option, it will output the available options
     * //LIST OTHER OPTIONS HERE
     * <p>
     * If the program is able to find a solution, 
     * @param args the command line arguments
     * Run config: -option1 file1 file2
     */
    public static void main(String[] args) {
        String noticeMessage = "Note: current solution method is inefficient for sufficiently "
                + "large or difficult trays. Execution time on signifigantly difficult problems "
                + "could be in excess of 30 mins.";
        //System.out.println(noticeMessage);
                
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
        
//        testing(tray, goal);
        
        Move solutionMoves = solve(tray, goal);
        if (solutionMoves != null){
            System.out.println(solutionMoves.print());
            System.out.println("Number of Moves: " + solutionMoves.size());
        }
        
    }
    
    /**
     *
     * @param tray
     * @param goal
     * @return
     */
    public static Move solve(Tray tray, Tray goal){
        
        LinkedList<Move> moves = new LinkedList<>(); //List of all possible moves
        LinkedList<Integer> prevTrays = new LinkedList<>(); //List of attempted trays

        Tray tempTray; //COULD REPLACE LIST OF MOVES WITH LIST OF TRAYS
        
        int count = 0;
        
        //Initialize the move list with the initial moves
        moves.addAll(tray.getMoves());
        
        while (!moves.isEmpty()){
            count++;
            tempTray = new Tray(tray); //Make a copy of the base tray
            tempTray.addMove(moves.pop()); //Move the tray with one of the possible moves
            if (!checkContains(prevTrays, tempTray)){ //If we've already tried this tray, ignore it
                if (tempTray.contains(goal.getBlocks())){ //If it's the solution, return the move
                    System.out.println("Attempts: " + count);
                    return tempTray.prevMoves();
                }
                else{
                    prevTrays.add(tempTray.hashCode()); //Otherwise, add this to the attempt list
                    moves.addAll(tempTray.getMoves()); //And add it's moves to the stack
                }
            }
        }
        
        //System.exit(1);
        System.out.println("No solution Found");
        System.out.println("Attempts: " + count);
        return null;
    }
    
    /**
     *
     * @param trays
     * @param tray
     * @return
     */
    public static boolean checkContains(LinkedList<Integer> trays, Tray tray){
        return trays.contains(tray.hashCode());
    }
    
//    public static Move solve(Tray tray, Tray goal){
//        
//        LinkedList<Move> moves = new LinkedList<>(); //List of all possible moves
//        LinkedList<Tray> prevTrays = new LinkedList<>(); //List of attempted trays
//
//        Tray tempTray;
//        int count = 0;
//        
//        //Initialize the move list with the initial moves
//        moves.addAll(tray.getMoves());
//        
//        while (!moves.isEmpty()){
//            count++;
//            tempTray = new Tray(tray); //Make a copy of the base tray
//            tempTray.addMove(moves.pop()); //Move the tray with one of the possible moves
//            if (!checkContains(prevTrays, tempTray)){ //If we've already tried this tray, ignore it
//                if (tempTray.contains(goal.getBlocks())) //If it's the solution, return the move
//                    return tempTray.prevMoves();
//                else{
//                    prevTrays.add(new Tray(tempTray)); //Otherwise, add this to the attempt list
//                    moves.addAll(tempTray.getMoves()); //And add it's moves to the stack
//                }
//            }
//        }
//        
//        //System.exit(1);
//        System.out.println("No solution Found");
//        return null;
//    }
//    
//
//    public static boolean checkContains(LinkedList<Tray> trays, Tray tray){
//        for (Tray t: trays) //Check if tray is contained in list
//            if (tray.equals(t))
//                return true;
//        
//        return false;
//    }
    
    public static void testing(Tray tray, Tray goal){
        Tray tray2 = new Tray(tray);
        int hash1 = tray.hashCode();
        Block block1 = tray2.removeBlock(0);
        LinkedList<Block> blocks = new LinkedList<>();
        blocks.add(block1);
        tray2.addBlocks(blocks);
        int hash2 = tray2.hashCode();
    }
    
    /**
     *
     * @param arg
     */
    public static void options(String arg){
        //Debug print out inputted options.
        //TODO add logic for outputting options.
        System.out.println( "-option " + arg);
    }
    
    //Opens a file and returns the contents as a linked list.

    /**
     *
     * @param fileName
     * @return
     */
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
