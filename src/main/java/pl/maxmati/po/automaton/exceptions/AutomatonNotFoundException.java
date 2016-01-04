package pl.maxmati.po.automaton.exceptions;


/**
 * Created by maxmati on 12/28/15.
 */
public class AutomatonNotFoundException extends AutomatonException {
    public AutomatonNotFoundException(String name) {
        super(getMessage(name));
    }

    public AutomatonNotFoundException(String name, Throwable e) {
        super(getMessage(name), e);
    }

    private static String getMessage(String name) {
        return "Unable to find automaton: " + name;
    }
}
