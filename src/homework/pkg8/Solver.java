/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg8;
import java.io.*;
import java.util.*;

/**
 *
 * @author safar
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
        
//        Block block1 = tray.removeBlock(0);
//        block1.print();
//        Block block2 = goal.removeBlock(0);
//        block2.print();
//        
//        System.out.println(block1.getCoordinates().equals(block2.getCoordinates()));
//        System.out.println(block1.equals(block2));
        
        Move solutionMoves = solve(tray, goal);
        if (solutionMoves != null){
            System.out.println(solutionMoves.print());
            System.out.println("Number of Moves: " + solutionMoves.size());
        }
//        LinkedList<Move> moves = tray.getMoves();
//        for (Move m: moves){
//            System.out.println(m.print());
//        }
        
        //Block testBlock = new Block(1, 1, 139, 139);
        //System.out.println(tray.blockCollision(testBlock));
        
    }
    
    public static Move solve(Tray tray, Tray goal){
        boolean found = false;
        
        LinkedList<Tray> trays = new LinkedList<>();
        LinkedList<Tray> prevTrays = new LinkedList<>();
        LinkedList<Move> newMoves;
        Tray tempTray;
        
        trays.add(tray);
        
        //Replace with list of moves, in tray, recurslively apply previous moves
        while (!found && !trays.isEmpty()){
            tempTray = trays.pop();
            prevTrays.add(new Tray(tempTray));
            if (tempTray.contains(goal.getBlocks()))
                return tempTray.prevMoves();
            else{
                newMoves = tempTray.getMoves();
                for (Move m: newMoves){
                    Tray tempTray2 = new Tray(tempTray);
                    tempTray2.move(m);
                    //if (!trays.contains(tempTray2) && !prevTrays.contains(tempTray2))
                        trays.add(new Tray(tempTray2));
                }  
            }
        }
        
        //System.exit(1);
        return null;
    }
    
//        public static Move solve(Tray tray, Tray goal){
//        boolean found = false;
//        
//        if (tray.contains(goal.getBlocks()))
//            return new Move();
//        
//        LinkedList<Move> moves = tray.getMoves();
//        LinkedList<Move> newMoves;
//        Tray tempTray;
//        Move currentMove;
//        
//        while (!found && !moves.isEmpty()){
//            tempTray = tray;
//            currentMove = moves.pop();
//            tempTray.move(currentMove);
//            if (tempTray.contains(goal.getBlocks()))
//                return currentMove;
//            else{
//                newMoves = tempTray.getMoves();
//                for (Move m: newMoves)
//                    m.prevMove = currentMove;
//                moves.addAll(newMoves);
//            }
//        }
//        
//        System.exit(1);
//        return null;
//    }
    
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
