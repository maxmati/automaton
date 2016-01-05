package pl.maxmati.po.automaton.structures.cellLoader;

import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.QuadState;

/**
 * Created by maxmati on 1/5/16.
 */
public class QuadStateCellLoader implements CellLoader {
    @Override
    public CellState getCell(String next) {
        return QuadState.valueOf(Integer.valueOf(next));
    }
}
