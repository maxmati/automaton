package pl.maxmati.po.automaton.structures.cellLoader;

import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;

/**
 * Created by maxmati on 1/4/16.
 */
public class BinaryStateCellLoader implements CellLoader {

    @Override
    public CellState getCell(String next) {
        return BinaryState.valueOf(Integer.parseInt(next));
    }
}
