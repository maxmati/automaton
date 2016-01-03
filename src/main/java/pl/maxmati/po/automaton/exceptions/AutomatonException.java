package pl.maxmati.po.automaton.exceptions;

/**
 * Created by maxmati on 11/20/15
 */
@SuppressWarnings("WeakerAccess")
public class AutomatonException extends RuntimeException {
    public AutomatonException() {
        super();
    }

    public AutomatonException(String s) {
        super(s);
    }

    public AutomatonException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AutomatonException(Throwable throwable) {
        super(throwable);
    }

    protected AutomatonException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
