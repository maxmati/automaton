package pl.maxmati.po.automaton;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;

import java.util.*;

/**
 * Created by maxmati on 11/20/15
 */
public abstract class Automaton implements Iterable<Cell>{
    private final Map<CellCoordinates, CellState> cells = new HashMap<>();
    private final CellNeighborhood neighborhoodStrategy;
    private final CellStateFactory stateFactory;

    Automaton(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory) {
        this.neighborhoodStrategy = neighborhoodStrategy;
        this.stateFactory = stateFactory;
    }

    public Automaton nextState(){
        Automaton newState = this.newInstance(stateFactory, neighborhoodStrategy);
        CellIterator newStateIterator = newState.cellIterator();

        while (newStateIterator.hasNext()){
            CellCoordinates newCellCords = newStateIterator.next().cords;

            Set<CellCoordinates> neighborsCords = this.neighborhoodStrategy.cellNeighbors(newCellCords);
            Set<Cell> neighborsCells = mapCoordinates(neighborsCords);

            CellState newCellState = newState.newCellState(new Cell(newCellCords, cells.get(newCellCords)), neighborsCells);
            newStateIterator.setState(newCellState);

        }

        return newState;
    }

    public void insertStructure(Map<? extends  CellCoordinates, ? extends CellState> structure){
        cells.putAll(structure);
    }

    @SuppressWarnings("WeakerAccess")
    public CellIterator cellIterator(){
        return new CellIterator();
    }

    @Override
    public Iterator<Cell> iterator() {
        return cellIterator();
    }

    protected abstract Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood);
    protected abstract boolean hasNextCoordinates(CellCoordinates coordinates);
    protected abstract CellCoordinates initialCoordinates(@SuppressWarnings("UnusedParameters") CellCoordinates coordinates);
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coordinates);
    protected abstract CellState newCellState(Cell currentState, Set<Cell> neighborsStates);
    protected abstract String stringifyCells();

    private Set<Cell> mapCoordinates(Set<CellCoordinates> cords){
        Set<Cell> cells = new HashSet<>(cords.size());
        for(CellCoordinates cord: cords){
            cells.add(new Cell(cord, this.mapCoordinate(cord)));
        }
        return cells;
    }

    private CellState mapCoordinate(CellCoordinates cord){
        return cells.get(cord);
    }

    private void setCellState(CellCoordinates cord, CellState state){
        this.cells.put(cord, state);
    }

    @Override
    public String toString() {
        return "Automaton\n" +
                "neighborhoodStrategy=" + neighborhoodStrategy + '\n' +
                "stateFactory=" + stateFactory + '\n' +
                stringifyCells();
    }

    public class CellIterator implements Iterator<Cell> {
        private CellCoordinates currentState;

        private CellIterator() {
            currentState = Automaton.this.initialCoordinates(currentState);
        }

        @Override
        public boolean hasNext() {
            return Automaton.this.hasNextCoordinates(currentState);
        }

        @Override
        public Cell next() {
            currentState = Automaton.this.nextCoordinates(currentState);
            return new Cell(currentState, Automaton.this.mapCoordinate(currentState));
        }

        @Override
        public void remove() {
            //TODO: implement
        }

        public void setState(CellState state){
            Automaton.this.setCellState(currentState, state);
        }
    }
}
