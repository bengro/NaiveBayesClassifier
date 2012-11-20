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
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
    public void evaluate() {
        try{
            String stemming = App.STEMMING ? "_stemming" : "";
            String stopwords = App.STOPWORDS ? "_stopwords" : "";
            String path = App.outputPath.toString() + "results" + stemming + stopwords + ".txt";
            File file = new File(path);

            String graphTitle = "ROC Curve for all Tests";
            if (App.STOPWORDS && !App.STEMMING) {
                graphTitle += " (Stopwords)";
            } else if (App.STOPWORDS && App.STEMMING) {
                graphTitle += " (Stopwords, Stemming)";
            } else if (!App.STOPWORDS && App.STEMMING) {
                graphTitle += " (Stemming)";
            }
            
            XYSeries series = new XYSeries("ROC Curve for all Tests");
            
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
                
                // buffer to write to file/stdout
                output = output.concat("\nRun " + (i + 1) + "\n");
                output = output.concat("------\n");
                output = output.concat("Training set size: " + trainingSize + " (" + markedAsSpam + " spam, " + markedAsNoSpam + " correct)\n");
                output = output.concat("Spam prior: " + spamPrior + "\n");
                output = output.concat("Correct prior: " + noSpamPrior + "\n");
                output = output.concat("True positives: " + tp + "\n");
                output = output.concat("False negatives: " + fn + "\n");
                output = output.concat("False positives: " + fp + "\n");
                output = output.concat("True negatives: " + tn + "\n");
                output = output.concat("Precision: " + precision + "\n");
                output = output.concat("Recall: " + recall + "\n");
                
                // add plot points: (false positive rate, true positive rate)
                series.add((double) fp / (tn + fn), (double) tp / (tp + fp));
            }

            out.write(output);
            out.close();
            System.out.print(output);
            System.out.println("Results output to " + path);
            
            // draw the graph            
            final XYChart xyChart = new XYChart(series, graphTitle);
            xyChart.pack();
            xyChart.setVisible(true);
            xyChart.toFront();
            xyChart.requestFocus();
            xyChart.setAlwaysOnTop(true);
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

class XYChart extends JFrame {
    
    public XYChart(XYSeries series, String title) {
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        final JFreeChart chart = ChartFactory.createXYLineChart(
            title,      // chart title
            "False Positive Rate",                      // x axis label
            "True Positive Rate",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            false,                     // include legend
            false,                     // tooltips
            false                     // urls
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }
}