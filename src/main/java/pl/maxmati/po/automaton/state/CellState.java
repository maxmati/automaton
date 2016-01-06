package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 *
 * Created by maxmati on 11/20/15
 *
 * Represents state for different {@link pl.maxmati.po.automaton.automaton.Automaton}
 */
public interface CellState {
    /**
     * Get next possible state. Used to circle through states
     *
     * @return Next possible state.
     */
    CellState next();
}
