package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellState} for {@link pl.maxmati.po.automaton.automaton.LangtonAnt}.
 */
public class LangtonCellState implements CellState {
    private static int nextAntId = 300;
    public final BinaryState cellState;
    public final Integer antId;
    public final LangtonAntState antState;

    /**
     * Creates new LangtonCellState without ant.
     *
     * @param cellState State of Cell.
     */
    public LangtonCellState(BinaryState cellState) {
        this(cellState, null, null);
    }

    /**
     * Creates new LangtonCellState with ant.
     *
     * @param cellState State of Cell.
     * @param antId ID of ant. Null if no ant.
     * @param antState Rotation of ant. Null if no ant.
     */
    public LangtonCellState(BinaryState cellState, Integer antId, LangtonAntState antState) {
        this.cellState = cellState;
        this.antId = antId;
        this.antState = antState;
    }

    /**
     * Creates new LangtonCellState from string.
     *
     * @param s String containing LangtonAntCellState
     */
    public static LangtonCellState fromString(String s) {
        BinaryState state;
        switch (s.charAt(0)){
            case 'D':
                state = BinaryState.DEAD;
                break;
            case 'A':
                state = BinaryState.ALIVE;
                break;
            default:
                throw new RuntimeException();
        }
        if(s.length() != 3 || s.charAt(1)==' '){
            return new LangtonCellState(state);
        } else {
            Integer antId = (int) s.charAt(1) - '0';
            LangtonAntState ant;
            switch (s.charAt(2)){
                case 'E':
                    ant = LangtonAntState.EAST;
                    break;
                case 'S':
                    ant = LangtonAntState.SOUTH;
                    break;
                case 'W':
                    ant = LangtonAntState.WEST;
                    break;
                case 'N':
                    ant = LangtonAntState.NORTH;
                    break;
                default:
                    throw new RuntimeException();
            }
            return new LangtonCellState(state, antId, ant);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next() {
        if(antState == LangtonAntState.WEST)
            return new LangtonCellState((BinaryState) cellState.next(), antId, null);
        else if ( antState == null)
            return new LangtonCellState(cellState, antId == null ? 1 : antId, LangtonAntState.NORTH);
        else
            return new LangtonCellState(cellState, antId, LangtonAntState.rotateAntRight(antState));
    }

    @Override
    public String toString() {
        return cellState.toString() + (antId != null ? antId.toString() + antState.toString(): "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangtonCellState cellState1 = (LangtonCellState) o;

        if (cellState != cellState1.cellState) return false;
        //noinspection SimplifiableIfStatement
        if (antId != null ? !antId.equals(cellState1.antId) : cellState1.antId != null) return false;
        return antState == cellState1.antState;

    }

    @Override
    public int hashCode() {
        int result = cellState.hashCode();
        result = 31 * result + (antId != null ? antId.hashCode() : 0);
        result = 31 * result + (antState != null ? antState.hashCode() : 0);
        return result;
    }
}
