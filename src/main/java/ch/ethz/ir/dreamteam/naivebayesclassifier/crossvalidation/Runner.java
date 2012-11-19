package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import ch.ethz.ir.dreamteam.naivebayesclassifier.classification.Classifier;
import ch.ethz.ir.dreamteam.naivebayesclassifier.classification.Model;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.Document;
import java.util.ArrayList;

/**
 * This class is responsible for executing training and testing for a given fold.
 * Collects all information for future plotting.
 * @author bengro
 */
public class Runner {
    
    /**
     * In case we need to know which documents were considered later - we should not.
     */
    Fold fold;
    
    /**
     * Contains model trained during training.
     */
    Model model;
    
    /**
     * True Positives
     */
    int TP;
    
    /**
     * True Negatives
     */
    int TN;
           
    /**
     * False Positives
     */
    int FP;
    
    /**
     * False Negatives
     */
    int FN;
    
    
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
            
            // our prediction for P(spam|doc)
            
            // our prediction for P(noSpam|doc)
            
            // our final prediction
            
            // effective class
        
            // add to TP, FP, TN, FN
            
        }

    }
    
}
