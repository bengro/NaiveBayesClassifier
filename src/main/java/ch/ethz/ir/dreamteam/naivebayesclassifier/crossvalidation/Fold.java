/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation;

import ch.ethz.ir.dreamteam.naivebayesclassifier.processing.Document;
import java.util.ArrayList;

/**
 * This class contains the documents for training and testing for a run.
 * @author bengro
 */
public class Fold {
    
    private ArrayList<Document> trainingDocuments = new ArrayList(); 
    private ArrayList<Document> testDocuments = new ArrayList(); 

    /**
     * @return the trainingDocuments
     */
    public ArrayList<Document> getTrainingDocuments() {
        return trainingDocuments;
    }

    /**
     * @param trainingDocuments the trainingDocuments to set
     */
    public void setTrainingDocuments(ArrayList<Document> trainingDocuments) {
        this.trainingDocuments = trainingDocuments;
    }

    /**
     * @return the testDocuments
     */
    public ArrayList<Document> getTestDocuments() {
        return testDocuments;
    }

    /**
     * @param testDocuments the testDocuments to set
     */
    public void setTestDocuments(ArrayList<Document> testDocuments) {
        this.testDocuments = testDocuments;
    }
    
    
}
