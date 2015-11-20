package pl.maxmati.po.automaton;

import java.util.Set;

/**
 * Created by maxmati on 11/20/15.
 */
public interface CellNeighborhood {
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
}
