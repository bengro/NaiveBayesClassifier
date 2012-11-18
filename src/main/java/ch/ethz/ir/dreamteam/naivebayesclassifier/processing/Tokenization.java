package ch.ethz.ir.dreamteam.naivebayesclassifier.processing;

import ch.ethz.ir.dreamteam.naivebayesclassifier.App;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author bengro
 */
public class Tokenization {
    
    private HashSet<String> stopwords;
    private Stemmer stemmer;
    
    public Tokenization() {
        // instantiate stopwords
        initStopwords();
        
        // stemmer
        stemmer = new Stemmer();
    }
    
    /**
     * returns tokens given a line.
     * @param line
     * @return 
     */
    public HashMap<String, Integer> tokenizeLine(String line) {
        
        HashMap<String, Integer> terms = new HashMap<String, Integer>();
        
        String token = new String();

        for (int i = 0; i <= line.length(); i++) {
            
            Character character = ' ';
            
            if (i != line.length()) {
                character = line.charAt(i);
            }

            boolean separator = 
                character.equals('\n') || 
                character.equals('\t') || 
                character.equals('\'') ||
                character.equals('\\') ||
                character.equals(' ') ||
                character.equals('.') ||
                character.equals(',') || 
                character.equals(':') ||
                character.equals(';') ||
                character.equals('!') ||
                character.equals('(') ||
                character.equals(')') ||
                character.equals('{') ||
                character.equals('}') ||
                character.equals('[') ||
                character.equals(']') ||
                character.equals('"') ||
                character.equals('&') ||
                character.equals('?') ||
                character.equals('#') ||
                character.equals('$') ||
                character.equals('�') ||
                character.equals('0') ||
                character.equals('1') ||
                character.equals('2') ||
                character.equals('3') ||
                character.equals('4') ||
                character.equals('5') ||
                character.equals('6') ||
                character.equals('7') ||
                character.equals('8') ||
                character.equals('9') ||
                character.equals('*') ||
                character.equals('/') ||
                character.equals('+') ||
                character.equals('-');

            if (separator || i == line.length()) {
                if (!token.isEmpty()) {
                    token = token.toLowerCase();

                    if (!(App.STOPWORDS && stopwords.contains(token))) {

                        if (App.STEMMING) {
                            stemmer.add(token.toCharArray(), token.length());
                            stemmer.stem();
                            token = stemmer.toString();
                        }

                        if (terms.containsKey(token)) {
                            // update existing term
                            int incrementedFrequency = terms.get(token).intValue() + 1;
                            terms.put(token, incrementedFrequency);
                        } else {
                            // create new term entry in this local inverted index
                            terms.put(token, 1);
                        }

                    }
                    token = new String();
                }
            } else {
                token = token.concat(character.toString());
            }

        } // for loop
        
        return terms;
    }
    
    /**
     * Parse and add stopwords from text file.
     */
    private void initStopwords() {
        
        stopwords = new HashSet<String>();

        Charset charset = Charset.forName("US-ASCII");
        try {
            BufferedReader reader = Files.newBufferedReader(App.stopwordsPath, charset);
            String line = null;

            // read stop words line by line
            while ((line = reader.readLine()) != null) {
                stopwords.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    
}
