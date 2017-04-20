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
     * Run config: -option1 testFile.txt testFile2.txt
     */
    public static void main(String[] args) {
        LinkedList<String> fileNames = new LinkedList<>();
        
        //Go through all args and filter out options from files
        for (String s: args){
            if (s.startsWith("-"))
                options(s.substring(1));
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
