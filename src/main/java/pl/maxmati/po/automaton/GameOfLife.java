package pl.maxmati.po.automaton;

import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.MooreNeighborhood;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maxmati on 11/20/15
 */
public class GameOfLife extends Automaton2Dim {

    private final Set<Integer> survive;
    private final Set<Integer> born;

    public GameOfLife(int width, int height) {
        this(width, height, false);
    }

    public GameOfLife(int width, int height, boolean wrap){
        this(width, height, wrap, new HashSet<>(Arrays.asList(2, 3)), new HashSet<>(Collections.singletonList(3)));
    }

    public GameOfLife(int width, int height, boolean wrap, Set<Integer> survive, Set<Integer> born){
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(BinaryState.DEAD), width, height, survive, born);
    }

    private GameOfLife(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height, Set<Integer> survive, Set<Integer> born) {
        super(neighborhoodStrategy, stateFactory, width, height);

        this.survive = survive;
        this.born = born;

        for(Automaton.CellIterator iterator = this.cellIterator(); iterator.hasNext();){
            Cell cell = iterator.next();
            iterator.setState(stateFactory.initialState(cell.cords));
        }
    }

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new GameOfLife(neighborhood,stateFactory, this.width, this.height, survive, born);
    }

    @Override
    protected CellState newCellState(Cell currentState, Set<Cell> neighborsCells) {
        int aliveCount = countAlive(neighborsCells);
        if ((currentState.state == BinaryState.ALIVE && survive.contains(aliveCount)) ||
                currentState.state == BinaryState.DEAD && born.contains(aliveCount))
        {
            return BinaryState.ALIVE;
        } else {
            return BinaryState.DEAD;
        }
    }

    private int countAlive(Set<Cell> neighborsCells) {
        int aliveCount = 0;
        for (Cell cell : neighborsCells) {
            if (cell.state instanceof BinaryState) {
                BinaryState state = (BinaryState) cell.state;
                if (state == BinaryState.ALIVE)
                    ++aliveCount;
            }
        }
        return aliveCount;
    }

    @Override
    public String toString() {
        return "GameOfLife\n" + super.toString();
    }
}
