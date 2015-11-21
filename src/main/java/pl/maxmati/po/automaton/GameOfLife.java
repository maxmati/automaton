package pl.maxmati.po.automaton;

import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.MooreNeighborhood;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.Set;

/**
 * Created by maxmati on 11/20/15
 */
public class GameOfLife extends Automaton2Dim {

    public GameOfLife(int width, int height){
        this(new MooreNeighborhood(1), new UniformStateFactory(BinaryState.DEAD), width, height);

    }

    private GameOfLife(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory, width, height);
        for(Automaton.CellIterator iterator = this.cellIterator(); iterator.hasNext();){
            Cell cell = iterator.next();
            iterator.setState(stateFactory.initialState(cell.cords));
        }
    }

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new GameOfLife(neighborhood,stateFactory, this.width, this.height);
    }

    @Override
    protected CellState newCellState(CellState currentState, Set<Cell> neighborsCells) {
        int aliveCount = 0;
        for (Cell cell : neighborsCells) {
            if (cell.state instanceof BinaryState) {
                BinaryState state = (BinaryState) cell.state;
                if (state == BinaryState.ALIVE)
                    ++aliveCount;
            }
        }
        if (aliveCount == 3 || (aliveCount == 2 && currentState == BinaryState.ALIVE)){
            return BinaryState.ALIVE;
        } else {
            return BinaryState.DEAD;
        }
    }

    @Override
    public String toString() {
        return "GameOfLife\n" + super.toString();
    }
}
