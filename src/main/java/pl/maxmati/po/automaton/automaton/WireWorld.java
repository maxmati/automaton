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
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Implementation of Wire World Automaton.
 *
 */
public class WireWorld extends Automaton2Dim {

    /**
     * Creates Automaton with specified width and without wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     */
    public WireWorld(int width, int height){
        this(width, height, false);

    }

    /**
     * Creates Automaton with specified width and wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     * @param wrap True if should wrap board, false otherwise.
     */
    public WireWorld(Integer width, Integer height, Boolean wrap) {
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(WireWorldState.VOID), width, height);
    }

    private WireWorld(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new WireWorld(neighborhood, stateFactory, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState newCellState(Cell cell, Set<Cell> neighborsStates) {
        if(!(cell.state instanceof WireWorldState)){
            throw new NotSupportedCellState();
        }
        WireWorldState state = (WireWorldState) cell.state;
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
