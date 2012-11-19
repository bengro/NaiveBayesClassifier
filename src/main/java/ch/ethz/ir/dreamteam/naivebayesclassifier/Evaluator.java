/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ethz.ir.dreamteam.naivebayesclassifier;

import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.Fold;
import ch.ethz.ir.dreamteam.naivebayesclassifier.crossvalidation.Runner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bengro
 */
public class Evaluator {

    private ArrayList<Runner> runs; // ive got them :D joking
    
    public Evaluator(ArrayList<Runner> runs) {
        this.runs = runs;
    }
    
    /**
     * Output all statistics to a file.
     */
    public void writeToFile() {
        try{
            String path = App.outputPath.toString() + "results.txt";
            File file = new File(path);

            file.createNewFile();
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);
            String output = "Results\n-------\n";

            for (int i = 0; i < runs.size(); i++) {
                Runner r = runs.get(i);
                Fold fold = r.getFold();
                Integer trainingSize = fold.getTrainingDocuments().size();
                Integer markedAsSpam = r.getNumberOfTrainingSpams();
                Integer markedAsNoSpam = r.getNumberOfTrainingNoSpams();
                Double spamPrior = r.getModel().getSpamPrior();
                Double noSpamPrior = r.getModel().getNoSpamPrior();
                Integer tp = r.getTP();
                Integer fn = r.getFN();
                Integer fp = r.getFP();
                Integer tn = r.getTN();
                Double precision = (double) tp / (tp + fp);
                Double recall = (double) tp / (tp + fn);
                
                output = output.concat("\nRun " + i + "\n");
                output = output.concat("------\n");
                output = output.concat("Training set size: " + trainingSize + "(" + markedAsSpam + " spam, " + markedAsNoSpam + " correct)\n");
                output = output.concat("Spam prior: " + spamPrior + "\n");
                output = output.concat("Correct prior: " + noSpamPrior + "\n");
                output = output.concat("True positives: " + tp + "\n");
                output = output.concat("False negatives: " + fn + "\n");
                output = output.concat("False positives: " + fp + "\n");
                output = output.concat("True negatives: " + tn + "\n");
                output = output.concat("Precision: " + precision + "\n");
                output = output.concat("Recall: " + recall + "\n");
            }

            out.write(output);
            out.close();
            System.out.print(output);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the runs
     */
    public ArrayList<Runner> getRuns() {
        return runs;
    }

    /**
     * @param runs the runs to set
     */
    public void setRuns(ArrayList<Runner> runs) {
        this.runs = runs;
    }
    
}
