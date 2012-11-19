package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import ch.ethz.ir.dreamteam.naivebayesclassifier.classification.Classifier;
import ch.ethz.ir.dreamteam.naivebayesclassifier.classification.Model;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.Document;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * This class is responsible for executing training and testing for a given fold.
 * Collects all information for future plotting.
 * @author bengro
 */
public class Runner {
    
    /**
     * In case we need to know which documents were considered later - we should not.
     */
    private Fold fold;
    
    /**
     * Contains model trained during training.
     */
    private Model model;
    
    /**
     * True Positives
     */
    private int TP;
    
    /**
     * True Negatives
     */
    private int TN;
           
    /**
     * False Positives
     */
    private int FP;
    
    /**
     * False Negatives
     */
    private int FN;
    
    
    /**
     * Is called by the CrossValidator and is meant to execute training and testing.
     * @param fold 
     */
    public Runner(Fold fold) {
        this.fold = fold;
        
        runTraining(fold.getTrainingDocuments());
        runTesting(fold.getTestDocuments());
    }
    
    /**
     * Initiates the training, stores the model returned by classifier.
     * @param trainingDocs 
     */
    public final void runTraining(ArrayList<Document> trainingDocs) {
        Classifier classifier = new Classifier(trainingDocs);
        this.model = classifier.getModel();
    }
    
    /**
     * Runs the testing, applying the model to the test documents.
     * Counting TP, FP, TN, FN.
     * @param testDocs 
     */
    public final void runTesting(ArrayList<Document> testDocs) {
        
        for(Document testDoc : testDocs) {
            
            double posteriorSumSpam = 0.0;
            double posteriorSumNoSpam = 0.0;
            
            for(Entry<String, Integer> term : testDoc.getTermFrequencies().entrySet()) {
                
                // look up probabilty for term | spam
                double spamProbTerm = model.getTermProbabilities().get(term.getKey()).getSpamProbability();
                double logSpamProbTerm = Math.log(spamProbTerm) * term.getValue();
                posteriorSumSpam = posteriorSumSpam + logSpamProbTerm;
                
                // look up probabilty for term | nospam
                double noSpamProbTerm = model.getTermProbabilities().get(term.getKey()).getNoSpamProbability();
                double logNoSpamProbTerm = Math.log(noSpamProbTerm) * term.getValue();
                posteriorSumNoSpam = posteriorSumNoSpam + logNoSpamProbTerm;
                
             }
            
            // our prediction for P(spam|doc)
            double isSpamProbability = Math.log(model.getSpamPrior()) + posteriorSumSpam;
            
            // our prediction for P(noSpam|doc)
            double isNoSpamProbability = Math.log(model.getNoSpamPrior()) + posteriorSumNoSpam;
            
            // our final prediction
            boolean isSpamPrediction;
            if(isSpamProbability < isNoSpamProbability) {
                isSpamPrediction = false;
            } else {
                isSpamPrediction = true;
            }
            
            // effective class
            boolean isSpamTruth = testDoc.isSpam();
            
            /* add to TP, FP, TN, FN
            *Truth|Predi| 
            * ---------------
            *  0  |  0  | TN
            *  0  |  1  | FP
            *  1  |  0  | FN
            *  1  |  1  | TP
            */
            if(isSpamTruth == isSpamPrediction == false) {
                this.TN++;
            }
            if(isSpamTruth == false && isSpamPrediction == true) {
                this.FP++;
            }
            if(isSpamTruth == true && isSpamPrediction == false) {
                this.FN++;
            }
            if(isSpamTruth == isSpamPrediction == true) {
                this.TP++;
            }
            
        }

    }

    /**
     * @return the fold
     */
    public Fold getFold() {
        return fold;
    }

    /**
     * @return the model
     */
    public Model getModel() {
        return model;
    }

    /**
     * @return the TP
     */
    public int getTP() {
        return TP;
    }

    /**
     * @return the TN
     */
    public int getTN() {
        return TN;
    }

    /**
     * @return the FP
     */
    public int getFP() {
        return FP;
    }

    /**
     * @return the FN
     */
    public int getFN() {
        return FN;
    }
    
    
    
}
