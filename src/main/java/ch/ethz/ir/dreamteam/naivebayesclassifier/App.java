package ch.ethz.ir.dreamteam.naivebayesclassifier;

import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.CrossValidator;
import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.Document;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.DocumentProcessor;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
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
    public static String documentDirectory = "./resources";
    public static ArrayList<String> customDirectories = new ArrayList();
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
                // just use the specified test set; each folder is a bucket
                if (customDirectories.isEmpty()) {
                    //CrossValidator cv = new CrossValidator();
                    File dir = new File(documentDirectory);
                    File[] subdirs = dir.listFiles();

                    for (File subdir : subdirs) {
                        if (subdir.isDirectory()) {
                            ArrayList<Document> bucket = DocumentProcessor.process(subdir.listFiles());
                            //cv.addBucket(bucket);
                        }
                    }
                } else {
                    // break things up evenly
                }
            }
        }
    }
}
