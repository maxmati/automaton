package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellState} for {@link pl.maxmati.po.automaton.automaton.QuadLife}.
 */
public enum QuadState implements CellState{
    DEAD, RED, YELLOW, BLUE, GREEN;

    private static QuadState[] vals = values();

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

    /**
     * Check if state should be counted as alive.
     *
     * @param state State to check
     * @return True if should be counted as alive, false otherwise.
     */
    public static boolean isAlive(CellState state) {
        return state != DEAD;
    }

    /**
     * Creates QuadState from int.
     *
     * @param i Integer representation of QuadState.
     * @return Created QuadState.
     */
    public static QuadState valueOf(int i) {
        switch (i){
            case 0:
                return DEAD;
            case 1:
                return RED;
            case 2:
                return YELLOW;
            case 3:
                return BLUE;
            case 4:
                return GREEN;
        }
        return null;
    }
}
