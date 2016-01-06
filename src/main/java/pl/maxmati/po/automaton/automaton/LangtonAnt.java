package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.Utils;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.VonNeumanNeighborhood;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.LangtonAntState;
import pl.maxmati.po.automaton.state.LangtonCellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Implementation of Langton Automaton.
 *
 */
public class LangtonAnt extends Automaton2Dim {
    final boolean wrap;

    /**
     * Creates Automaton with specified width and without wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     */
    public LangtonAnt(int width, int height){
        this(width, height, false);
    }

    /**
     * Creates Automaton with specified width and wrapping.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     * @param wrap True if should wrap board, false otherwise.
     */
    public LangtonAnt(int width, int height, boolean wrap){
        this(new VonNeumanNeighborhood(1, wrap, width, height), new UniformStateFactory(new LangtonCellState(BinaryState.DEAD)), width, height, wrap);
    }

    private LangtonAnt(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height, boolean wrap) {
        super(neighborhoodStrategy, stateFactory, width, height);
        this.wrap = wrap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new LangtonAnt(neighborhood, stateFactory, width, height, wrap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState newCellState(Cell cell, Set<Cell> neighborsCells) {
        Set<Cell> ants = findAnts(neighborsCells);
        LangtonCellState currentState = (LangtonCellState) cell.state;
        Cords2D currentCords = (Cords2D) cell.cords;

        BinaryState newCellState = getNewCellState(currentState);

        if(ants.size() == 0) {
            return new LangtonCellState(newCellState);
        } else {
            LangtonCellState newState = getCellStateIfAntMovesAt(currentCords, ants, newCellState);
            if(newState == null)
                return new LangtonCellState(newCellState);
            else
                return newState;
        }

    }

    private BinaryState getNewCellState(LangtonCellState currentState) {
        BinaryState newCellState;
        if(currentState.antState == null){
            newCellState = currentState.cellState;
        } else {
            switch (currentState.cellState){
                case DEAD:
                    newCellState = BinaryState.ALIVE;
                    break;
                case ALIVE:
                    newCellState = BinaryState.DEAD;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        return newCellState;
    }

    private LangtonCellState getCellStateIfAntMovesAt(Cords2D currentCords, Set<Cell> antsCells, BinaryState newCellState) {
        for (Cell cell: antsCells){
            LangtonCellState state = (LangtonCellState) cell.state;
            if (nextAntCords(cell).equals( currentCords))
                return new LangtonCellState(newCellState, state.antId, nextAntState(cell));
        }
        return null;
    }

    private Cords2D nextAntCords(Cell antCell) {
        Cords2D cellCords = (Cords2D) antCell.cords;

        switch (nextAntState(antCell)){
            case NORTH: {
                int ny = cellCords.y - 1;
                ny = wrap ? Utils.mod(ny, height) : ny;
                return new Cords2D(cellCords.x, ny);
            }
            case EAST: {
                int nx = cellCords.x + 1;
                nx = wrap ? Utils.mod(nx, width) : nx;
                return new Cords2D(nx, cellCords.y);
            }
            case SOUTH: {
                int ny = cellCords.y + 1;
                ny = wrap ? Utils.mod(ny, height) : ny;
                return new Cords2D(cellCords.x, ny);
            }
            case WEST: {
                int nx = cellCords.x - 1;
                nx = wrap ? Utils.mod(nx, width) : nx;
                return new Cords2D(nx, cellCords.y);
            }
            default:
                throw new RuntimeException();
        }

    }

    private LangtonAntState nextAntState(Cell antCell) {
        LangtonCellState state = (LangtonCellState) antCell.state;
        BinaryState cellState = state.cellState;
        switch (cellState){
            case DEAD:
                return LangtonAntState.rotateAntRight(state.antState);
            case ALIVE:
                return LangtonAntState.rotateAntLeft(state.antState);
            default:
                throw new RuntimeException();
        }
    }


    private Set<Cell> findAnts(Set<Cell> neighborsSCells) {
        Set<Cell> ants = new HashSet<>();

        for (Cell cell: neighborsSCells){
            LangtonCellState state = (LangtonCellState) cell.state;
            if(state != null && state.antState != null){
                ants.add(cell);
            }
        }

        return ants;
    }
}
