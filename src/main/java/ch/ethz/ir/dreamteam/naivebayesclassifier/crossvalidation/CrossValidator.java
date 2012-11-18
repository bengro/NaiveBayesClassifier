package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import java.util.ArrayList;

/**
 *
 * @author bengro
 */
public class CrossValidator {
    private ArrayList<ArrayList<Document>> buckets = new ArrayList();
    private ArrayList<Document> trainingSet;
    private ArrayList<Document> testSet;
    
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
