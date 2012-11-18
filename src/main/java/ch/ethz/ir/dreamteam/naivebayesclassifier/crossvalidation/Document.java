package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import java.util.HashMap;

/**
 *
 * @author bengro
 */
public class Document {
    
    private HashMap<String,Integer> termFrequencies = new HashMap<String,Integer>();
    private boolean isSpam;

    /**
     * @return the terms
     */
    public HashMap<String,Integer> getTermFrequencies() {
        return termFrequencies;
    }

    /**
     * @param terms the terms to set
     */
    public void setTermFrequencies(HashMap<String,Integer> freqs) {
        this.termFrequencies = freqs;
    }

    /**
     * @return the isSpam
     */
    public boolean isIsSpam() {
        return isSpam;
    }

    /**
     * @param isSpam the isSpam to set
     */
    public void setIsSpam(boolean isSpam) {
        this.isSpam = isSpam;
    }

}
