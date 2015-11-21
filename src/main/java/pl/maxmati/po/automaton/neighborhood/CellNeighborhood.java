package pl.maxmati.po.automaton.neighborhood;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;

import java.util.Set;

/**
 * Created by maxmati on 11/20/15
 */
public interface CellNeighborhood {
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
}
