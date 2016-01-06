package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.exceptions.WrongRuleException;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.neighborhood.MooreNeighborhood;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Implementation of Conway's Game of Life Automaton.
 *
 */
public class GameOfLife extends Automaton2Dim {
    private final Rule rule;

    /**
     * Creates Automaton with specified width without wrapping and default rule (23/3).
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     */
    public GameOfLife(int width, int height) {
        this(width, height, false);
    }

    /**
     * Creates Automaton with specified width, wrapping and default rule (23/3).
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     * @param wrap True if should wrap board, false otherwise.
     */
    public GameOfLife(int width, int height, boolean wrap){
        this(width, height, wrap, new Rule("23/3"));
    }

    /**
     * Creates Automaton with specified width, wrapping and rule.
     *
     * @param width Width of Automaton
     * @param height Height of Automaton
     * @param wrap True if should wrap board, false otherwise.
     * @param rule Instance of {@link Rule} representing custom Game of Life rule.
     */
    public GameOfLife(int width, int height, boolean wrap, Rule rule){
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(BinaryState.DEAD), width, height, rule);
    }

    private GameOfLife(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height, Rule rule) {
        super(neighborhoodStrategy, stateFactory, width, height);

        this.rule = rule;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new GameOfLife(neighborhood,stateFactory, this.width, this.height, rule);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState newCellState(Cell cell, Set<Cell> neighborsCells) {
        int aliveCount = countAlive(neighborsCells);
        if ((cell.state == BinaryState.ALIVE && rule.getSurvive().contains(aliveCount)) ||
                cell.state == BinaryState.DEAD && rule.getBorn().contains(aliveCount))
        {
            return BinaryState.ALIVE;
        } else {
            return BinaryState.DEAD;
        }
    }

    @Override
    public String toString() {
        return "GameOfLife\n" + super.toString();
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

    /**
     * Representation of one of Game of Life rules.
     */
    public static class Rule {
        private final Set<Integer> survive = new HashSet<>();
        private final Set<Integer> born = new HashSet<>();

        /**
         * Creates object which represents specified Game of Life rule.
         *
         * @param rule Rule in standard format (SURVIVE/BORN)
         */
        public Rule(String rule) {
            if(!rule.contains("/")) throw new WrongRuleException();
            String[] rules = rule.split("/");
            if(rules.length > 2) throw new WrongRuleException();

            ruleInStringToSet(rules[0], survive);
            if(rules.length > 1)
                ruleInStringToSet(rules[1], born);

        }

        /**
         * Returns numbers of alive cells required for cell to survive.
         *
         * @return Set of possible number of alive cells.
         */
        public Set<Integer> getSurvive() {
            return survive;
        }

        /**
         * Returns numbers of alive cells required for cell to born.
         *
         * @return Set of possible number of alive cells.
         */
        public Set<Integer> getBorn() {
            return born;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            born.forEach(builder::append);
            builder.append('/');
            survive.forEach(builder::append);

            return builder.toString();
        }

        private void ruleInStringToSet(String rule, Set<Integer> set) {
            for (int i = 0; i < rule.length(); ++i){
                set.add( Character.getNumericValue( rule.charAt(i) ) );
            }
        }

    }
}
