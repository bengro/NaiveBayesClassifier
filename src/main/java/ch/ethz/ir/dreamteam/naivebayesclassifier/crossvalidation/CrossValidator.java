package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import ch.ethz.ir.dreamteam.naivebayesclassifier.Evaluator;
import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.Document;
import java.util.ArrayList;

/**
 * This class generates all combinations of the training and test buckets.
 * Executes training and test for each combination - in a so called "run".
 * @author bengro
 */
public class CrossValidator {
    
    private ArrayList<ArrayList<Document>> buckets = new ArrayList(); 
    private ArrayList<Fold> folds = new ArrayList();
    private ArrayList<Runner> runs = new ArrayList();
    
    /**
     * This method generates all possible combinations for training and test set.
     * The folds are stored in an array list.
     */
    public void generateFolds() {
        
        int sizeBuckets = buckets.size();
        
        for(int i = 0; i <= sizeBuckets; i++) {
           
            // create copy of all buckets
            ArrayList<ArrayList<Document>> bucketsCopy = buckets;
            
            // create fold object and take ith bucket as test bucket.
            Fold fold = new Fold();
            fold.setTestDocuments(bucketsCopy.get(i));
            
            // remove test set from list - only training buckets left.
            bucketsCopy.remove(i);
            
            // iterate over training buckets and store documents in aggregate.
            ArrayList<Document> trainingDocuments = new ArrayList<Document>();
            for(ArrayList<Document> docs : bucketsCopy) {
                trainingDocuments.addAll(docs);
            }
            fold.setTrainingDocuments(trainingDocuments);
            
            folds.add(fold);
            
        }
        
    }

    /**
     * Generates n-folds and runs the training / testing.
     */
    public void run() {
        generateFolds();  
        
        for(Fold fold : folds) {
            Runner run = new Runner(fold);
            runs.add(run);
        }
        
        // evaluation either right here or in another class.
        Evaluator evaluation = new Evaluator(runs);
    }
    
    /**
     * @return the buckets
     */
    public ArrayList<ArrayList<Document>> getBuckets() {
        return buckets;
    }

    /**
     * @param buckets the buckets to set
     */
    public void setBuckets(ArrayList<ArrayList<Document>> buckets) {
        this.buckets = buckets;
    }
    
    /**
     * @param bucket appends a bucket to buckets
     */
    public void addBucket(ArrayList<Document> bucket) {
        this.buckets.add(bucket);
    }
    
}
