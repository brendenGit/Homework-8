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
        String fileName = null;
        LinkedList<String> inputList = new LinkedList<>();
        for (String s: args){
            if (s.startsWith("-"))
                options(s.substring(1));
            else
                fileName = s;
        }
        
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                    inputList.add(line);
            }
            fileReader.close();
            } catch (IOException e) {
                return;
            }
        
        Tray tray = new Tray(inputList);
        // TODO code application logic here
    }
    
    public static void options(String arg){
        System.out.println(arg);
    }
    
}
