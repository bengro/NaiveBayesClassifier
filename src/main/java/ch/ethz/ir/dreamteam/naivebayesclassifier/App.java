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
    public static ArrayList<Path> customDirectories = new ArrayList();
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
                if (customDirectories.isEmpty()) {
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
                    ArrayList<ArrayList<Document>> buckets = new ArrayList(8);
                    
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
                        System.out.println(ix);
                        buckets.get(ix).add(doc);
                        ix = ++ix % 8;
                    }
                }
                
                // TODO: Initiate CrossValidation!
                cv.run();
            }
        }
    }
    
    /**
     * sets the directories for queries and relevancy list.
     * TODO: Walk the custom directory
     * @param input 
     */
    public static void updatePaths(String input) {
        System.out.println("input: " + input);

        Pattern p = Pattern.compile("(\\\"(.*?)\\\")");
        Matcher m = p.matcher(input);

        ArrayList<String> pathArgs = new ArrayList();
        
        /*int i = 0;
        while(m.find()) {
            pathArgs;
            i++;
        }
        
        while(m.find()) {
            customDirectories.add(Paths.get(m.group(1).toString()));
        }

        if(pathArgs[1] == null) {
            System.out.println("Wrong input pattern. paths \"query/path\" \"relevancy/directory\"");
            return;
        }

        pathArgs[0] = pathArgs[0].replaceAll("\"", "");
        pathArgs[1] = pathArgs[1].replaceAll("\"", "");
        
        // set query directory / query file
        File file = new File(pathArgs[0]);
        boolean isFile = file.isFile();

        if(isFile) {
            System.out.println("query file path set to " + pathArgs[0]);
            App.queryFile = pathArgs[0];
        } 
        // input path is a directory.
        else {
            try {
            System.out.println("query path set to " + pathArgs[0]);
            App.queryFile = null;
            App.queryDirectory = pathArgs[0];
            sampleQueryReader.setQueryDirectory(App.queryDirectory);
            } catch(ArrayIndexOutOfBoundsException ex) {
                System.out.println("Could not read input. Make sure you use quotes.");
            }
        }

        sampleQueryReader.readSampleQueries();
        queryQueue = sampleQueryReader.getQueries();

        // set relevancy list
        relevancyListPath = Paths.get(pathArgs[1]);
        System.out.println("Relevancy path set to " + pathArgs[1]);
        sampleQueryReader.setRelevancyListPath(relevancyListPath);
        */
    }
    
}
