package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellState;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.MooreNeighborhood;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.WireWorldState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.Set;

/**
 * Created by maxmati on 11/22/15
 */
public class WireWorld extends Automaton2Dim {

    public WireWorld(int width, int height){
        this(width, height, false);

    }
    public WireWorld(Integer width, Integer height, Boolean wrap) {
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(WireWorldState.VOID), width, height);
    }

    private WireWorld(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory, width, height);
    }


    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new WireWorld(neighborhood, stateFactory, width, height);
    }

    @Override
    protected CellState newCellState(Cell currentState, Set<Cell> neighborsStates) {
        if(!(currentState.state instanceof WireWorldState)){
            throw new NotSupportedCellState();
        }
        WireWorldState state = (WireWorldState) currentState.state;
        switch (state){
            case VOID:
                return WireWorldState.VOID;
            case TAIL:
                return WireWorldState.WIRE;
            case HEAD:
                return WireWorldState.TAIL;
            case WIRE:
                int heads = countCellsInState(neighborsStates, WireWorldState.HEAD);
                if(heads == 1 || heads == 2) {
                    return WireWorldState.HEAD;
                } else {
                    return WireWorldState.WIRE;
                }
            default:
                throw new NotSupportedCellState();
        }
    }

    private int countCellsInState(Set<Cell> cells, WireWorldState state) {
        int count = 0;

        for (Cell cell: cells)
            if(cell.state == state)
                ++count;

        return count;
    }
}
