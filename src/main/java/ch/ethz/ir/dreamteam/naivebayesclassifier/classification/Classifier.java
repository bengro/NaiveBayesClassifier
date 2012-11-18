package ch.ethz.ir.dreamteam.naivebayesclassifier.classification;

import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author bengro
 */
public class Classifier {
    
    /**
     * An array containing all the training documents. This is given as an input.
     */
    ArrayList<Document> trainingDocuments = new ArrayList<Document>();
    
    /**
     * Contains the mapping term to posterior probabilty with respect to spam / no-spam class.
     */
    HashMap<String, Classification> termProbabilities = new HashMap<String, Classification>();
    
    /**
     * Stores terms and their frequency collected in no-spam documents.
     */
    HashMap<String, Integer> noSpamTerms = new HashMap<String, Integer>();
    
    /**
     * Stores terms and their frequency collected in spam documents.
     */
    HashMap<String, Integer> spamTerms = new HashMap<String, Integer>();
    
    /**
     * Prior for spam documents. D_spam / D_all
     */
    double spamPrior;
    
    /**
     * Prior for no-spam documents. D_nospam / D_all, or 1 - spam Prior.
     */
    double noSpamPrior;
    
    /**
     * Number of training documents. Is double to avoid double-int rounding errors.
     */
    double numberOfTrainingDocs;
    
    /**
     * Number of spam documents. Is double to avoid double-int rounding errors.
     */
    double numberOfSpamDocs = 0.0;
    
    /**
     * Number of no-spam documents. Is double to avoid double-int rounding errors.
     */
    double numberOfNoSpamDocs = 0.0;
    
    /**
     * Size of vocabulary.
     */
    double sizeOfVocabulary = 0.0;
    
    /**
     * Total Number of tokens in spam class.
     */
    double totalTokensSpam = 0.0;
    
    /**
     * Total number of tokens in non-spam class.
     */
    double totalTokensNoSpam = 0.0;
    
    /**
     * The model containing the term probabilities and priors.
     */
    private Model model;
    
    /**
     * Builds the index.
     * @param docs 
     */
    public Classifier(ArrayList<Document> docs) {
        this.trainingDocuments = docs;
        
        /**
         * create index by iterating through all terms in all documents. 
         * Creates two hashmaps containing terms for both classes.
         */
        for(Document doc : trainingDocuments) {
            
            // increase spam / no-spam document count
            if(doc.isSpam()) {
                numberOfSpamDocs++;
            } else {
                numberOfNoSpamDocs++;
            }
            
            for(Entry<String, Integer> term : doc.getTermFrequencies().entrySet()) {
                
                // create entry in global index
                termProbabilities.put(term.getKey(), new Classification());
                
                // track the number of occurences for each term in a class
                if(doc.isSpam()) {
                    if(spamTerms.containsKey(term.getKey())) {
                        int termFrequency = spamTerms.get(term.getKey()).intValue();
                        spamTerms.put(term.getKey(), term.getValue() + termFrequency);    
                    } else {
                        spamTerms.put(term.getKey(), term.getValue());
                    }
                } else {
                    if(noSpamTerms.containsKey(term.getKey())) {
                        int termFrequency = noSpamTerms.get(term.getKey()).intValue();
                        noSpamTerms.put(term.getKey(), term.getValue() + termFrequency);    
                    } else {
                        noSpamTerms.put(term.getKey(), term.getValue());
                    }
                }
            }
            
            trainModel();
        }
        
    }

    /**
     * Iterates through all terms and computes priors.
     */
    public void trainModel() {
        
        // computer size of vocabulary
        sizeOfVocabulary = termProbabilities.size();
        
        // compute N - number of all documents.
        numberOfTrainingDocs = (double) trainingDocuments.size();
        
        // priors
        spamPrior = this.numberOfSpamDocs / this.numberOfTrainingDocs;
        noSpamPrior = this.numberOfNoSpamDocs / this.numberOfTrainingDocs;
        
        // number of tokens in spam, no-spam class respectively.
        for(Integer termFrequency : spamTerms.values()) {
            this.totalTokensSpam = totalTokensSpam + termFrequency;
        }
        for(Integer termFrequency : noSpamTerms.values()) {
            this.totalTokensNoSpam = totalTokensNoSpam + termFrequency;
        }
        
        // iterate through all terms and compute corresponding posterior probability.
        for(Entry<String, Classification> term : termProbabilities.entrySet()) {
            Classification updatedClassifation = computePosterior(term);
            term.setValue(updatedClassifation);
        }
        
        model = new Model();
        model.setNoSpamPrior(noSpamPrior);
        model.setSpamPrior(spamPrior);
        model.setTermProbabilities(termProbabilities);
    }
    
    /**
     * Computes the posterior for a given term in a given class. Also applies Laplace Smoothing.
     * @param term
     * @param isSpam
     * @return 
     */
    public Classification computePosterior(Entry<String, Classification> term) {
        
        Classification predictedClassification = term.getValue();
        double enumerator;
                
        // posterior P(term | C = spam) = T_spam/term + 1 / sum_t(T_spam/term) + vocabSize
        // it can be that th term is not in the class vocabulary, therefore this check:
        if(spamTerms.containsKey(term.getKey())) {
            enumerator = spamTerms.get(term.getKey());
        } else {
            enumerator = 0.0;
        }
        double spamPosterior = (enumerator + 1) / (this.totalTokensSpam + this.sizeOfVocabulary);
        predictedClassification.setSpamProbability(spamPosterior);
        
        // posterior P(term | C = noSpam)
        if(noSpamTerms.containsKey(term.getKey())) {
            enumerator = noSpamTerms.get(term.getKey());
        } else {
            enumerator = 0.0;
        }
        double noSpamPosterior = (enumerator + 1) / (this.totalTokensNoSpam + this.sizeOfVocabulary);
        predictedClassification.setSpamProbability(noSpamPosterior);
        
        return predictedClassification;
    }
        
    /**
     * @return the model
     */
    public Model getModel() {
        return model;
    }
    
    /**
     * For debugging purposes.
     */
    public void outputStats() {
        System.out.println("Number of Documents: " + this.numberOfTrainingDocs);
        System.out.println("Size of Vocabulary: " + this.sizeOfVocabulary);
        System.out.println("Number of Spam documents: " + this.numberOfSpamDocs);
        System.out.println("Number of No-spam documents: " + this.numberOfNoSpamDocs);
        System.out.println("Number of tokens in spam class: " + this.totalTokensSpam);
        System.out.println("Number of tokens in no-spam class: " + this.totalTokensNoSpam);
        System.out.println("Spam prior: " + this.spamPrior);
        System.out.println("No-spam prior: " + this.noSpamPrior);
    }

}
