package ch.ethz.ir.dreamteam.naivebayesclassifier;

import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.CrossValidator;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.Document;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.DocumentProcessor;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App 
{
    public static boolean STOPWORDS = false;
    public static boolean STEMMING = false;

    
    // NETBEANS: To set the working dir, do Run > Set Project Configuration > Customize > Run
    public static Path documentDirectory = Paths.get("./resources/IR_Project3_Files");
    public static Path stopwordsPath = Paths.get("./resources/stopwords.txt");
    public static Path outputPath = Paths.get("./output/");
         
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
                updatePaths(input);
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
                CrossValidator cv = new CrossValidator();
                
                // just use the specified test set; each folder is a bucket
                if (documentDirectory.equals(Paths.get("./resources/IR_Project3_Files"))) {
                    File dir = new File(documentDirectory.toString());
                    File[] subdirs = dir.listFiles();

                    for (File subdir : subdirs) {
                        if (subdir.isDirectory()) {
                            ArrayList<Document> bucket = DocumentProcessor.process(subdir.listFiles());
                            cv.addBucket(bucket);
                        }
                    }

                // insert documents in a round-robin fashion to the buckets
                } else {
                    ArrayList<Document> allDocs = new ArrayList(); // a big list of all documents in the corpus
                    ArrayList<ArrayList<Document>> buckets = new ArrayList<ArrayList<Document>>(8);
                    
                    // instantiate all the buckets
                    for (int i = 0; i < 8; i++) {
                        buckets.add(new ArrayList<Document>());
                    }
                    
                    File dir = new File(documentDirectory.toString());
                    File[] subdirs = dir.listFiles();

                    // populate allDocs
                    for (File subdir : subdirs) {
                        if (subdir.isDirectory()) {
                            allDocs.addAll(DocumentProcessor.process(subdir.listFiles()));
                        }
                    }
                    
                    int ix = 0;
                    for (Document doc : allDocs) {
                        buckets.get(ix).add(doc);
                        ix = ++ix % 8;
                    }
                    
                    for (ArrayList<Document> bucket : buckets) {
                        cv.addBucket(bucket);
                    }
                }
                
                // TODO: Initiate CrossValidation!
                System.out.println("Model training and testing in progress...");
                cv.run();
            }
        }
    }
    
    /**
     * sets the directories for queries and relevancy list.
     * @param input 
     */
    public static void updatePaths(String input) {
        System.out.println("input: " + input);

        Pattern p = Pattern.compile("(\\\"(.*?)\\\")");
        Matcher m = p.matcher(input);

        ArrayList<String> pathArgs = new ArrayList();
        
        if (m.find()) {
            documentDirectory = Paths.get(m.group().replace("\"", ""));
        }
    }
}
