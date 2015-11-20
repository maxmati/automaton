package pl.maxmati.po.automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxmati on 11/20/15.
 */
public abstract class Automaton {
    private Map<CellCoordinates, CellState> cells = new HashMap<>();
    private CellNeighborhood neighborhoodStratedy;
    private CellStateFactory stateFactory;

    public Automaton nextState(){
        //TODO: implement
        return null;
    }

    public void insertStructure(Map<? extends  CellCoordinates, ? extends CellState> structure){
        //TODO: implement
    }

    public CellIterator cellIterator(){
        //TODO: implement
        return null;
    }

    protected abstract Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood);
    protected abstract boolean hasNextCoordinates(CellCoordinates coordinates);
    protected abstract CellCoordinates initialCoordinates(CellCoordinates coordinates);
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coordinates);
    protected abstract CellState newState(CellState currentState, Set<Cell> neighborsStates);

    static private Set<Cell> mapCoordinates(Set<CellCoordinates> coords){
        //TODO: implement
        return null;
    }

    private class CellIterator {
    }
}
