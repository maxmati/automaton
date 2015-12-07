package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 11/23/15.
 */
public class LangtonCellState implements CellState {
    public final BinaryState cellState;
    public final Integer antId;
    public final LangtonAntState antState;

    public LangtonCellState(BinaryState cellState) {
        this(cellState, null, null);
    }

    public LangtonCellState(BinaryState cellState, Integer antId, LangtonAntState antState) {
        this.cellState = cellState;
        this.antId = antId;
        this.antState = antState;
    }

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
