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
 * Created by maxmati on 11/20/15
 */
public class GameOfLife extends Automaton2Dim {
    private final Rule rule;

    public GameOfLife(int width, int height) {
        this(width, height, false);
    }

    public GameOfLife(int width, int height, boolean wrap){
        this(width, height, wrap, new Rule("3/23"));
    }

    public GameOfLife(int width, int height, boolean wrap, Rule rule){
        this(new MooreNeighborhood(1, wrap, width, height), new UniformStateFactory(BinaryState.DEAD), width, height, rule);
    }

    private GameOfLife(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height, Rule rule) {
        super(neighborhoodStrategy, stateFactory, width, height);

        this.rule = rule;

        for(Automaton.CellIterator iterator = this.cellIterator(); iterator.hasNext();){
            Cell cell = iterator.next();
            iterator.setState(stateFactory.initialState(cell.cords));
        }
    }

    @Override
    protected Automaton newInstance(CellStateFactory stateFactory, CellNeighborhood neighborhood) {
        return new GameOfLife(neighborhood,stateFactory, this.width, this.height, rule);
    }

    @Override
    protected CellState newCellState(Cell currentState, Set<Cell> neighborsCells) {
        int aliveCount = countAlive(neighborsCells);
        if ((currentState.state == BinaryState.ALIVE && rule.getSurvive().contains(aliveCount)) ||
                currentState.state == BinaryState.DEAD && rule.getBorn().contains(aliveCount))
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

    public static class Rule {
        private final Set<Integer> survive = new HashSet<>();
        private final Set<Integer> born = new HashSet<>();

        public Rule(String rule) {
            String[] rules = rule.split("/");
            if(rules.length != 2) throw new WrongRuleException();

            ruleInStringToSet(rules[0], born);
            ruleInStringToSet(rules[1], survive);

        }

        public Set<Integer> getSurvive() {
            return survive;
        }

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
                set.add(Character.getNumericValue( rule.charAt(i) ) );
            }
        }

    }
}
