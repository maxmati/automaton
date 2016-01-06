package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.MooreNeighborhood;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.QuadState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.*;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Implementation of Quad Life Automaton.
 *
 */
public class QuadLife extends Automaton2Dim {
    private static final Set<Integer> survive = new HashSet<>(Arrays.asList(2, 3));
    private static final Set<Integer> born = new HashSet<>(Collections.singletonList(3));

    /**
     * Creates Automaton with specified width and without wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     */
    public QuadLife(int width, int height){
        this(width, height, false);
    }

    /**
     * Creates Automaton with specified width and wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     * @param wrap True if should wrap board, false otherwise.
     */
    public QuadLife(int width, int height, Boolean wrap){
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(QuadState.DEAD), width, height);
    }

    private QuadLife(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory, width, height);

        for(Automaton.CellIterator iterator = this.cellIterator(); iterator.hasNext();){
            Cell cell = iterator.next();
            iterator.setState(stateFactory.initialState(cell.cords));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new QuadLife(neighborhood, stateFactory, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState newCellState(Cell cell, Set<Cell> neighborCells) {
        Map<QuadState, Integer> counter =  countStates(neighborCells);
        int aliveCount = counter.get(QuadState.RED) + counter.get(QuadState.GREEN)
                + counter.get(QuadState.BLUE) + counter.get(QuadState.YELLOW);

        if(QuadState.isAlive(cell.state) && survive.contains(aliveCount)) {
            return cell.state;
        } else if(born.contains(aliveCount)){
            return getMajorityOrMissing(counter);
        } else {
            return QuadState.DEAD;
        }
    }

    private CellState getMajorityOrMissing(Map<QuadState, Integer> counter) {
        QuadState nextState = null;
        for (Map.Entry<QuadState, Integer> entry: counter.entrySet()){
            if(QuadState.isAlive(entry.getKey())){
                if(entry.getValue() == 0)
                    nextState = entry.getKey();
                if(entry.getValue() >= 2)
                    return entry.getKey();
            }

        }
        return nextState;
    }

    private Map<QuadState, Integer> countStates(Set<Cell> neighborCells){
        Map<QuadState, Integer> counter = new EnumMap<>(QuadState.class);
        for(QuadState value : QuadState.values())
            counter.put(value, 0);

        for (Cell cell: neighborCells){
            if(cell.state == null)
                continue;

            QuadState state = (QuadState) cell.state;
            counter.put(state, counter.get(state)+1);
        }
        return counter;
    }
}
