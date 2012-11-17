package ch.ethz.ir.dreamteam.naivebayesclassifier;
import java.io.File;

/**
 *
 * @author ilienert
 */
public class CrossValidate {
    private int n;
    private File[][] buckets;
    
    public CrossValidate(File[][] buckets, int n) {
        this.buckets = buckets;
        this.n = n;
    }
    
    public Parameters train() {
        return 0;
    }
    
    public void test() {
        
    }
}