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
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Implementation of Elementary Cellular Automaton with support for all 256 wolfram rules.
 *
 */
public class Automaton1Dim extends Automaton {
    private final int width;
    private final Rule rule;

    /**
     * Creates Automaton with specified width and rule without wrapping.
     *
     * @param width Width of Automaton
     * @param rule Instance of {@link Rule} which specifies one of Wolfram's rules.
     */
    public Automaton1Dim(int width, Rule rule){
        this(width, rule, false);
    }

    /**
     * Creates Automaton with specified width and wrapping if specified.
     *
     * @param width Width of Automaton
     * @param rule Instance of {@link Rule} which specifies one of Wolfram's rules.
     * @param wrap True if Automaton should wrap board false otherwise.
     */
    public Automaton1Dim(Integer width, Rule rule, Boolean wrap) {
        this(new SingleDimensionNeighborhood(1, wrap, width), new UniformStateFactory(BinaryState.DEAD), width, rule);
    }

    private Automaton1Dim(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, Rule rule) {
        super(neighborhoodStrategy, stateFactory);
        this.width = width;
        this.rule = rule;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new Automaton1Dim(neighborhood, stateFactory, width, rule);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasNextCoordinates(CellCoordinates coordinates) {
        if(!(coordinates instanceof Cords1D)) throw new NotSupportedCellCoordinates();
        Cords1D cords = (Cords1D) coordinates;
        return cords.x < width - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates initialCoordinates() {
        return new Cords1D(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coordinates) {
        if(!(coordinates instanceof Cords1D)) throw new NotSupportedCellCoordinates();
        Cords1D cords = (Cords1D) coordinates;
        return new Cords1D(cords.x+1);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    private void fillTrio(BinaryState[] trio) {
        for (int i = 0; i < 3; i++) {
            if(trio[i] == null)
                trio[i] = BinaryState.DEAD;
        }
    }

    /**
     * Representation of one of Wolfram's rules.
     */
    public static class Rule {
        Set<List<BinaryState>> aliveTrios = new HashSet<>();
        int rule;

        /**
         * Creates object which represents specified Wolfram's rule.
         *
         * @param rule Number of Wolfram's rule. Between 0 and 255.
         */
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

        /**
         * Check if "trio" match specified rule(Cell should be alive or dead).
         * @param trio List of 3 states in order(left, center, right).
         * @return True if cell should be alive false otherwise.
         */
        public boolean match(List<BinaryState> trio) {
            return aliveTrios.contains(trio);
        }

        @Override
        public String toString() {
            return String.valueOf(rule);
        }

        private int getNthBit(int rule, int i) {
            return rule >> i & 1;
        }
    }
}
