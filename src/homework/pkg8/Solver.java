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
     * Run config: -ooption1 testFile.txt testFile2.txt
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
        if (fileNames.size() < 2)
            return;
        
        
        Tray tray = new Tray(openFile(fileNames.pop()));

        Block solution = readSolution(openFile(fileNames.pop()));
        
        System.out.println("Tray");
        tray.print(); //Debug output tray and solution goal
        System.out.println("\nSolution");
        solution.print();
    }
    
    public static void options(String arg){
        //Debug print out inputted options.
        //TODO add logic for outputting options.
        System.out.println( "-option " + arg);
    }
    
    //Takes in a string that holds 4 ints and creats a block
    //that represents the solution goal
    public static Block readSolution(LinkedList<String> input){
        Scanner in = new Scanner(input.pop()).useDelimiter("[^0-9]+");
        int blockH = in.nextInt();
        int blockW = in.nextInt();
        int blockY = in.nextInt();
        int blockX = in.nextInt();
        
        return new Block(blockH, blockW, blockY, blockX);
    }
    
    //Opens a file and returns the contents as a linked list.
    public static LinkedList<String> openFile(String fileName){
        LinkedList<String> outList = new LinkedList<>();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                    outList.add(line);
            }
            fileReader.close();
            } catch (IOException e) {
                return null;
            }
        return outList;
    }
}
