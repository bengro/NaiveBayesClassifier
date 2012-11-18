package ch.ethz.ir.dreamteam.naivebayesclassifier.processing;

import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bengro
 */
public class DocumentProcessor {
    
    /**
     * Converts an array of File objects into a corresponding ArrayList of
     * bag-of-word, classified Documents.
     * @param files
     * @return 
     */
    public static ArrayList<Document> process(File[] files) {
        ArrayList<Document> processedDocuments = new ArrayList();

        for (File file : files) {
            Document d = new Document();
            HashMap<String, Integer> termFrequencies = new HashMap();
            
            String filename = file.getName();
            Pattern p = Pattern.compile("^spmsg");
            Matcher m = p.matcher(filename);
            
            // set spam flag true if filename starts with "spmsg"
            d.setIsSpam(m.find());
            
            // tokenize and set term frequencies of the document
            Tokenization t = new Tokenization();
            if (file.isFile()) {
                try {
                    Charset charset = Charset.forName("US-ASCII");
                    BufferedReader reader = Files.newBufferedReader(file.toPath(), charset);
                    String line;

                    while ((line = reader.readLine()) != null) {
                        termFrequencies.putAll(t.tokenizeLine(line));
                    }
                } catch(IOException ex) {
                    System.err.format("IOException: %s%n", ex);
                }
            }
            
            d.setTermFrequencies(termFrequencies);
            processedDocuments.add(d);
        }
        
        return processedDocuments;
    }
}
