package pl.maxmati.po.automaton.exceptions;

import pl.maxmati.po.automaton.automaton.Automaton;

/**
 * Created by maxmati on 1/3/16.
 */
public class NoCellRendererFoundForGivenAutomaton extends AutomatonException {
    public NoCellRendererFoundForGivenAutomaton(Automaton automaton) {
        super("Didn't found cell renderer for automaton class:" + automaton.getClass().getName());
    }
}
