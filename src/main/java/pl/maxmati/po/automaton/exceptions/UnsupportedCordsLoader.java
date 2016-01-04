package pl.maxmati.po.automaton.exceptions;

/**
 * Created by maxmati on 1/4/16.
 */
public class UnsupportedCordsLoader extends ArrayStoreException {
    public UnsupportedCordsLoader(String cordsType) {
        super("Didn't find cords loader for:" + cordsType);
    }
}
