package pl.maxmati.po.automaton.exceptions;

/**
 * Created by maxmati on 1/4/16.
 */
public class UnsupportedCellLoader extends AutomatonException {
    public UnsupportedCellLoader(String cellType) {
        super("Didn't find cell loader for:" + cellType);
    }
}
