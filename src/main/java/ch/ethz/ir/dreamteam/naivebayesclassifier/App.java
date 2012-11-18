package ch.ethz.ir.dreamteam.naivebayesclassifier;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static boolean STOPWORDS = false;
    public static boolean STEMMING = false;
    
    /* NETBEANS: To set the working dir, do Run > Set Project Configuration
     * > Customize > Run
     */
    public static String documentDirectory = "IR_Project3_Files";
    public static Path stopwordsPath;
         
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);
        boolean isRunning = true;
        while(isRunning) {
            String input = in.nextLine();
            
            if(input.equals("exit")) {
                isRunning = false;
            }
            
            // Specify subdirectory containing all the buckets
            else if(input.contains("path")) {
                // updatePaths(input);
            }
            
            // switches stopwords on and off
            else if(input.equals("stopwords")) {
                STOPWORDS = STOPWORDS ? false : true;
                System.out.println((STOPWORDS ? "Stopwords on." : "Stopwords off."));
            }
            
            // switches stemming on and off
            else if(input.equals("stemming")) {
                STEMMING = STEMMING ? false:true;
                System.out.println((STEMMING ? "Stemming on." : "Stemming off."));
            }
            
            // prints all set parameters
            else if(input.equals("info")) {
                System.out.println("Stopwords: " + (STOPWORDS ? "On" : "Off"));
                System.out.println("Stemming: " + (STEMMING ? "On" : "Off"));
            }
            
            else if(input.equals("run")) {
                File dir = new File(documentDirectory);
                System.out.println(dir.listFiles());
                for (File child : dir.listFiles()) {
                    // Print all subdirectories
                    System.out.println(child);
                }
            }
        }
    }
}
