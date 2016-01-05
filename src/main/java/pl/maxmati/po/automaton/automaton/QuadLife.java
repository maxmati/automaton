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
 * Created by maxmati on 12/7/15.
 */
public class QuadLife extends Automaton2Dim {

    private static final Set<Integer> survive = new HashSet<>(Arrays.asList(2, 3));
    private static final Set<Integer> born = new HashSet<>(Collections.singletonList(3));

    public QuadLife(int width, int height){
        this(width, height, false);
    }

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

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new QuadLife(neighborhood, stateFactory, width, height);
    }

    @Override
    protected CellState newCellState(Cell currentState, Set<Cell> neighborCells) {
        Map<QuadState, Integer> counter =  countStates(neighborCells);
        int aliveCount = counter.get(QuadState.RED) + counter.get(QuadState.GREEN)
                + counter.get(QuadState.BLUE) + counter.get(QuadState.YELLOW);

        if(QuadState.isAlive(currentState.state) && survive.contains(aliveCount)) {
            return currentState.state;
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
