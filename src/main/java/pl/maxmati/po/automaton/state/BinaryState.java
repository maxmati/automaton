package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellState} for {@link pl.maxmati.po.automaton.automaton.GameOfLife}.
 */
public enum BinaryState implements CellState {
    DEAD, ALIVE;

    private static BinaryState[] vals = values();

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public static BinaryState valueOf(int i) {
        switch (i){
            case 0:
                return DEAD;
            case 1:
                return ALIVE;
        }
        return null;
    }

}
