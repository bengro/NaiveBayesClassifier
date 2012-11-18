package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import java.util.ArrayList;

/**
 *
 * @author bengro
 */
public class Document {
    
    private ArrayList<String> terms = new ArrayList<String>();
    private boolean isSpam;

    /**
     * @return the terms
     */
    public ArrayList<String> getTerms() {
        return terms;
    }

    /**
     * @param terms the terms to set
     */
    public void setTerms(ArrayList<String> terms) {
        this.terms = terms;
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
