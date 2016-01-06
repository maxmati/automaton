package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellState} for {@link pl.maxmati.po.automaton.automaton.WireWorld}.
 */
public enum WireWorldState implements CellState{
    VOID, WIRE, HEAD, TAIL;

    private static WireWorldState[] vals = values();

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

    /**
     * Creates WireWorldState from int.
     *
     * @param i Integer representation of WireWorldState.
     * @return Created WireWorldState.
     */
    public static WireWorldState valueOf(int i) {
        switch (i){
            case 0:
                return VOID;
            case 1:
                return WIRE;
            case 2:
                return HEAD;
            case 3:
                return TAIL;
        }
        return null;
    }
}
