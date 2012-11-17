package ch.ethz.ir.dreamteam.naivebayesclassifier;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author ilienert
 */
public class MLEEstimator {
    private HashMap<File,String> fileToClass;
    
    public MLEEstimator (File[] files) {
        for (File f : files) {
            String name = f.getName();
            System.out.println(name);
        }
    }
}