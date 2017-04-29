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
    
    public static boolean[] ops = {false,false,false,false, false};
    public static long maxMem = 0;
    public static int totalTrays = 0;
    //Options: h, runtime, memory usage, trays checked, movecount

    /**
     * This program takes in a number of -options and two filenames containing
     * tray data. The first file should contain valid tray configuration code
     * and the second file should contain a valid solution goal.
     * <p>
     * If the program is given the -h option, it will output the available options
     * //LIST OTHER OPTIONS HERE
     * <p>
     * If the program is able to find a solution, it will list the series of 
     * moves necessary, otherwise it will exit.
     * @param args the command line arguments
     * Run example: solver -option1 file1 file2
     * @see Solver
     * @see #options(java.lang.String) 
     * @see #openFile(java.lang.String) 
     */
    public static void main(String[] args) {
//        Note: current solution method is inefficient for sufficiently
//              large or difficult trays. Execution time on signifigantly 
//              difficult problems could be in excess of 30 mins.

        long startTime = System.currentTimeMillis();
        LinkedList<String> fileNames = new LinkedList<>();
        
        //Go through all args and filter out options from files
        for (String s: args){
            if (s.startsWith("-"))
                options(s.substring(1).toLowerCase());
            else
                fileNames.add(s);
        }
        
        if (ops[0]){
            System.out.println("Available options:\n-h: Will list available commands.\n"
                    + "-oruntime: Will print the runtime of the code at completion.\n"
                    + "-omaxmemory: Will print the max memory usage at completion.\n"
                    + "-ototaltrayschecked: Will print the total trays checked at completion.\n"
                    + "-onumberofmoves: Will print final move count.\n");
            System.exit(0);
        }
        
        //If we don't have at least 2 files, exit
        if (fileNames.size() < 2){System.out.println("Not enough file arguments found"); return;}
        
        Tray tray = new Tray(openFile(fileNames.pop()));

        LinkedList<String> solutionLines = openFile(fileNames.pop());
        solutionLines.addFirst(tray.getH() + " " + tray.getW());
        Tray goal = new Tray(solutionLines);
        
        System.out.println("Tray");
        System.out.println(tray.print()); //Debug output tray and solution goal
        System.out.println("\nGoal Configuration");
        System.out.println(goal.print());
        System.out.println("---------------------\n");
        
        Move solutionMoves = solve(tray, goal);
        long stopTime = System.currentTimeMillis();
        if (solutionMoves == null)
            System.exit(1);
        
        System.out.println(solutionMoves.print());
        
        if (ops[1]) System.out.println("Elapsed Time: " + (stopTime - startTime)/1000 + " Seconds");
        if (ops[2]) System.out.println("Max Memory Used: " + maxMem/1000000 + " MB");
        if (ops[3]) System.out.println("Total Trays Checked: " + totalTrays);
        if (ops[4]) System.out.println("Number of Moves: " + solutionMoves.size());
        
    }
    
    /**
     * Returns a possible Move object that contains a series of moves that would
     * solve the tray, if one exists. <p>
     * This function takes in a tray containing the initial configuration and a
     * tray containing the blocks that constitute a goal configuration.
     * @param tray Initial tray configuration
     * @param goal Tray containing the goal configuration
     * @return A Move containing the moves required to reach the goal configuration
     * @see #checkContains(java.util.LinkedList, homework.pkg8.Tray) 
     */
    public static Move solve(Tray tray, Tray goal){
        
        LinkedList<Move> moves = new LinkedList<>(); //List of all possible moves
        LinkedList<Tray> prevTrays = new LinkedList<>(); //List of attempted trays

        Tray tempTray;
        
        //Initialize the move list with the initial moves
        moves.addAll(tray.getMoves());
        
        while (!moves.isEmpty()){
            totalTrays++;
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
            if (ops[2]){
                Runtime runtime = Runtime.getRuntime();
                long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
                if (memoryUsed > maxMem)
                    maxMem = memoryUsed;
            }
        }

//        System.out.println("No solution Found");
        return null;
    }
    
    /**
     * Returns a boolean value which is true if the list of trays contains the
     * specified tray, or false if it does not.
     * @param trays List of trays to be checked
     * @param tray Tray to be checked for
     * @return Whether or not the tray was found
     * @see #solve(homework.pkg8.Tray, homework.pkg8.Tray) 
     */
    public static boolean checkContains(LinkedList<Tray> trays, Tray tray){
        for (Tray t: trays) //Check if tray is contained in list
            if (tray.equals(t))
                return true;
        
        return false;
    }
    
    /**
     * Function for processing the options included at runtime
     * @param arg List of options to be checked
     */
    public static void options(String arg){
        switch (arg){
            case "h": ops[0] = true;
                break;
            case "oruntime": ops[1] = true;
                break;
            case "omaxmemory": ops[2] = true;
                break;
            case "ototaltrayschecked": ops[3] = true;
                break;
            case "onumberofmoves": ops[4] = true;
                break;
            
        }
        //Debug print out inputted options.
        //TODO add logic for outputting options.
        System.out.println( "-option " + arg);
    }
    
    //Opens a file and returns the contents as a linked list.

    /**
     * Opens a specified file and returns a list of strings representing that
     * file.
     * @param fileName File to be read
     * @return LinkedList of Strings representing the contents of the file.
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
