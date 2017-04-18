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
public class Homework8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedList<String> fileNames = new LinkedList<>();
        
        for (String s: args){
            if (s.startsWith("-"))
                options(s.substring(1));
            else
                fileNames.add(s);
        }

        Tray tray = new Tray(openFile(fileNames.pop()));

        Tray solution = new Tray(openFile(fileNames.pop()));
        // TODO code application logic here
    }
    
    public static void options(String arg){
        System.out.println(arg);
    }
    
    public static LinkedList<String> openFile(String fileName){
        LinkedList<String> outList = new LinkedList<>();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuffer = new StringBuilder();
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
