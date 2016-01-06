package pl.maxmati.po.automaton.neighborhood;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;

import java.util.Set;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 */
public interface CellNeighborhood {
    /**
     * Calculates coordinates which belongs to neighborhood of specified {@link CellCoordinates}
     *
     * @param cell {@link CellCoordinates} for which we want to calculate neighbors
     * @return Set of {@link CellCoordinates} of neighbors.
     */
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
}
