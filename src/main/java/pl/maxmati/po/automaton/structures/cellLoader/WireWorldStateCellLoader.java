package pl.maxmati.po.automaton.structures.cellLoader;

import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.WireWorldState;

/**
 * Created by maxmati on 1/5/16.
 */
public class WireWorldStateCellLoader implements CellLoader {
    @Override
    public CellState getCell(String state) {
        return WireWorldState.valueOf(Integer.parseInt(state));
    }
}
