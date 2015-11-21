package pl.maxmati.po.automaton.state.factory;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.state.CellState;

/**
 * Created by maxmati on 11/20/15
 */
public interface CellStateFactory {
    CellState initialState(@SuppressWarnings("UnusedParameters") CellCoordinates cords);
}
