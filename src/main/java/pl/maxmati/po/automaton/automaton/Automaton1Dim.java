package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.SingleDimensionNeighborhood;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maxmati on 12/9/15.
 */
public class Automaton1Dim extends Automaton {
    private final int width;
    private final Rule rule;

    public Automaton1Dim(int width, Rule rule){
        this(new SingleDimensionNeighborhood(1), new UniformStateFactory(BinaryState.DEAD), width, rule);
    }

    Automaton1Dim(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, Rule rule) {
        super(neighborhoodStrategy, stateFactory);
        this.width = width;
        this.rule = rule;
    }

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new Automaton1Dim(neighborhood, stateFactory, width, rule);
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates coordinates) {
        if(!(coordinates instanceof Cords1D)) throw new NotSupportedCellCoordinates();
        Cords1D cords = (Cords1D) coordinates;
        return cords.x < width - 1;
    }

    @Override
    protected CellCoordinates initialCoordinates(@SuppressWarnings("UnusedParameters") CellCoordinates coordinates) {
        return new Cords1D(-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coordinates) {
        if(!(coordinates instanceof Cords1D)) throw new NotSupportedCellCoordinates();
        Cords1D cords = (Cords1D) coordinates;
        return new Cords1D(cords.x+1);
    }

    @Override
    protected CellState newCellState(Cell currentState, Set<Cell> neighbors) {
        BinaryState trio[] = new BinaryState[3];
        trio[1] = (BinaryState) currentState.state;

        Cords1D currentCords = (Cords1D) currentState.cords;
        for(Cell cell: neighbors){
            Cords1D cellCords = (Cords1D) cell.cords;
            trio[cellCords.x - currentCords.x + 1] = (BinaryState) cell.state;
        }

        fillTrio(trio);

        if(rule.match(Arrays.asList(trio)))
            return BinaryState.ALIVE;
        else
            return BinaryState.DEAD;

    }

    private void fillTrio(BinaryState[] trio) {
        for (int i = 0; i < 3; i++) {
            if(trio[i] == null)
                trio[i] = BinaryState.DEAD;
        }
    }

    @Override
    protected String stringifyCells() {
        StringBuilder out = new StringBuilder();

        for(Cell c : this){
            if(!(c.cords instanceof Cords1D))
                throw new NotSupportedCellCoordinates();

            out.append(c.state);
            if(hasNextCoordinates(c.cords)){
                out.append(' ');
            }
        }

        return out.toString();
    }

    public static class Rule {
        Set<List<BinaryState>> aliveTrios = new HashSet<>();
        int rule;

        public Rule(int rule) {
            this.rule = rule;
            this.aliveTrios = new HashSet<>();

            for(int i = 0; i < 8; ++i)
                if(getNthBit(rule, i) == 1)
                    aliveTrios.add(Arrays.asList(
                            BinaryState.valueOf(getNthBit(i,2)),
                            BinaryState.valueOf(getNthBit(i,1)),
                            BinaryState.valueOf(getNthBit(i,0))
                    ));
        }

        private int getNthBit(int rule, int i) {
            return rule >> i & 1;
        }

        public boolean match(List<BinaryState> trio) {
            return aliveTrios.contains(trio);
        }

        @Override
        public String toString() {
            return String.valueOf(rule);
        }
    }
}
