package pl.maxmati.po.automaton.structures.cellLoader;

import pl.maxmati.po.automaton.state.CellState;

/**
 * Created by maxmati on 1/4/16.
 */
public interface CellLoader {
    CellState getCell(String next);
}
