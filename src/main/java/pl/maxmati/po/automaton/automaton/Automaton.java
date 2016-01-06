package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Main class of application every Automaton have to extends.
 *
 */
public abstract class Automaton implements Iterable<Cell>{
    private final Map<CellCoordinates, CellState> cells = new HashMap<>();
    private final CellNeighborhood neighborhoodStrategy;
    private final CellStateFactory stateFactory;

    Automaton(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory) {
        this.neighborhoodStrategy = neighborhoodStrategy;
        this.stateFactory = stateFactory;
    }

    /**
     * Creates new Automaton advanced by single tick.
     *
     * @return NEW Automaton advanced by single tick.
     */
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

    /**
     * Set specified cell in current Automaton.
     *
     * @param structure Map of CellCoordinates and corresponding CellStates.
     * @see CellCoordinates
     * @see CellState
     */
    public void insertStructure(Map<? extends  CellCoordinates, ? extends CellState> structure){
        cells.putAll(structure);
    }

    /**
     * @return {@link CellIterator} which iterates through all {@link Cell} in Automaton
     */
    @SuppressWarnings("WeakerAccess")
    public CellIterator cellIterator(){
        return new CellIterator();
    }

    /**
     * @return Iterator which iterates through all {@link Cell} in Automaton
     */
    @Override
    public Iterator<Cell> iterator() {
        return cellIterator();
    }

    /**
     * Creates new object of concrete Automaton.
     *
     * @param stateFactory old {@link CellStateFactory} object to reuse.
     * @param neighborhood old {@link CellNeighborhood} object to reuse.
     * @return new instance of Automaton
     */
    protected abstract Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood);

    /**
     * Used by {@link CellIterator} to check if it reached last cell.
     *
     * @param coordinates Current coordinates.
     * @return True if next coordinates is valid False otherwise.
     */
    protected abstract boolean hasNextCoordinates(CellCoordinates coordinates);

    /**
     * Used by {@link CellIterator} to get coordinates before first.
     *
     * @return CellCoordinates just before first one.
     */
    protected abstract CellCoordinates initialCoordinates();

    /**
     * Used by {@link CellIterator} to get {@link CellCoordinates} fallowing specified one.
     *
     * @param coordinates Current coordinates.
     * @return CellCoordinates fallowing specified coordinates.
     */
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coordinates);

    /**
     * Calculate state of cell in next tick based on current cell and their neighbours.
     *
     * @param cell Current cell.
     * @param neighborsStates Set of mapped neighbours.
     * @return CellState of current cell advanced by single tick.
     */
    protected abstract CellState newCellState(Cell cell, Set<Cell> neighborsStates);

    /**
     * Convert all cells of Automaton into string.
     *
     * @return String representing all cells;
     */
    protected abstract String stringifyCells();

    /**
     * Merge set of {@link CellCoordinates} with corresponding {@link CellState} into set of {@link Cell}.
     *
     * @param cords Set of {@link CellCoordinates} to map
     * @return Set of merged {@link Cell}
     */
    private Set<Cell> mapCoordinates(Set<CellCoordinates> cords){
        return cords.stream()
                .map(this::mapCoordinate)
                .collect(Collectors.toSet());
    }

    /**
     * Merge {@link CellCoordinates} with corresponding {@link CellState} into {@link Cell}.
     *
     * @param cord {@link CellCoordinates} to map.
     * @return Merged {@link Cell}
     */
    protected Cell mapCoordinate(CellCoordinates cord){
        return new Cell(cord, getState(cord));
    }

    /**
     * Get {@link CellState} corresponding {@link CellCoordinates}.
     * If CellCoordinates don't exists in map is generated by {@link CellStateFactory}.
     *
     * @param cord {@link CellCoordinates} to get {@link CellState} for.
     * @return {@link CellState} at specified {@link CellCoordinates}.
     */
    protected CellState getState(CellCoordinates cord){
        CellState state = cells.get(cord);
        if(state == null){
            state = stateFactory.initialState(cord);
            cells.put(cord, state);
        }
        return state;
    }

    /**
     * Set given {@link CellState} at given {@link CellCoordinates}
     *
     * @param cord {@link CellCoordinates} to set {@link CellState} at.
     * @param state {@link CellState} to set.
     */
    protected void setCellState(CellCoordinates cord, CellState state){
        this.cells.put(cord, state);
    }

    /**
     * Convert Automaton to String.
     *
     * @return String representing Automaton.
     */
    @Override
    public String toString() {
        return "Automaton\n" +
                "neighborhoodStrategy=" + neighborhoodStrategy + '\n' +
                "stateFactory=" + stateFactory + '\n' +
                stringifyCells();
    }

    /**
     * Switch {@link CellState} at {@link CellCoordinates} cords to next state.
     *
     * @param cords {@link CellCoordinates} to switch at.
     */
    public void switchCell(CellCoordinates cords){
        setCellState(cords, getState(cords).next());
    }

    /**
     * Creates new empty instance of current {@link Automaton}.
     *
     * @return Empty {@link Automaton}.
     */
    public Automaton createNewEmpty(){
        return newInstance(stateFactory, neighborhoodStrategy);
    }


    /**
     * Class of iterator which iterates through all {@link Automaton} cells;
     */
    public class CellIterator implements Iterator<Cell> {
        private CellCoordinates currentState;

        private CellIterator() {
            currentState = Automaton.this.initialCoordinates();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return Automaton.this.hasNextCoordinates(currentState);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Cell next() {
            currentState = Automaton.this.nextCoordinates(currentState);
            return Automaton.this.mapCoordinate(currentState);
        }

        /**
         * Set state of cell on which iterator is pointing
         *
         * @param state {@link CellState} state to which set cell.
         */
        public void setState(CellState state){
            Automaton.this.setCellState(currentState, state);
        }
    }
}
