package ch.ethz.ir.dreamteam.naivebayesclassifier.classification;

import java.util.HashMap;

/**
 *
 * @author bengro
 */
public class Model {
    
    private double spamPrior;
    private double noSpamPrior;
    
    private HashMap<String, Classification> termProbabilities = new HashMap<String, Classification>();

    /**
     * @return the spamPrior
     */
    public double getSpamPrior() {
        return spamPrior;
    }

    /**
     * @param spamPrior the spamPrior to set
     */
    public void setSpamPrior(double spamPrior) {
        this.spamPrior = spamPrior;
    }

    /**
     * @return the noSpamPrior
     */
    public double getNoSpamPrior() {
        return noSpamPrior;
    }

    /**
     * @param noSpamPrior the noSpamPrior to set
     */
    public void setNoSpamPrior(double noSpamPrior) {
        this.noSpamPrior = noSpamPrior;
    }

    /**
     * @return the termProbabilities
     */
    public HashMap<String, Classification> getTermProbabilities() {
        return termProbabilities;
    }

    /**
     * @param termProbabilities the termProbabilities to set
     */
    public void setTermProbabilities(HashMap<String, Classification> termProbabilities) {
        this.termProbabilities = termProbabilities;
    }
    
}
