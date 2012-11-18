package ch.ethz.ir.dreamteam.naivebayesclassifier.classification;

/**
 * This class describes how probable it is for a term to belong to either of the classes.
 * @author bengro
 */
public class Classification {
    
    private double spamProbability;
    private double noSpamProbability;
    
    public double isSpam() {
        return this.getSpamProbability();
    }
    
    public double isNoSpam() {
        return this.getNoSpamProbability();
    }

    /**
     * @return the spamProbability
     */
    public double getSpamProbability() {
        return spamProbability;
    }

    /**
     * @param spamProbability the spamProbability to set
     */
    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }

    /**
     * @return the noSpamProbability
     */
    public double getNoSpamProbability() {
        return noSpamProbability;
    }

    /**
     * @param noSpamProbability the noSpamProbability to set
     */
    public void setNoSpamProbability(double noSpamProbability) {
        this.noSpamProbability = noSpamProbability;
    }
    
    
    
}
