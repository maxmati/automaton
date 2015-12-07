package pl.maxmati.po.automaton;

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
 * Created by maxmati on 11/23/15.
 */
public class LangtonAnt extends Automaton2Dim {

    public LangtonAnt(int width, int height){
        this(new VonNeumanNeighborhood(1), new UniformStateFactory(new LangtonCellState(BinaryState.DEAD)), width, height);
    }

    LangtonAnt(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory, width, height);
    }

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new LangtonAnt(neighborhood, stateFactory, width, height);
    }

    @Override
    protected CellState newCellState(Cell currentCell, Set<Cell> neighborsCells) {
        Set<Cell> ants = findAnts(neighborsCells);
        LangtonCellState currentState = (LangtonCellState) currentCell.state;
        Cords2D currentCords = (Cords2D) currentCell.cords;

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
        if(currentState.antId == null){
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
            case NORTH:
                return new Cords2D(cellCords.x, cellCords.y-1);
            case EAST:
                return new Cords2D(cellCords.x+1, cellCords.y);
            case SOUTH:
                return new Cords2D(cellCords.x, cellCords.y+1);
            case WEST:
                return new Cords2D(cellCords.x-1, cellCords.y);
            default:
                throw new RuntimeException();
        }

    }

    private static LangtonAntState nextAntState(Cell antCell) {
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
            if(state != null && state.antId != null){
                ants.add(cell);
            }
        }

        return ants;
    }
}
