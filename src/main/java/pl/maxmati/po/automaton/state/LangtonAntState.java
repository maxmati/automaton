package pl.maxmati.po.automaton.state;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Represents rotation of ant in {@link LangtonCellState}
 */
public enum LangtonAntState {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Returns new ant rotation after rotating it left.
     *
     * @param antState Current ant rotation.
     * @return New ant rotation.
     */
    public static LangtonAntState rotateAntLeft(LangtonAntState antState) {
        switch (antState){
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Returns new ant rotation after rotating it left.
     *
     * @param antState Current ant rotation.
     * @return New ant rotation.
     */
    public static LangtonAntState rotateAntRight(LangtonAntState antState) {
        switch (antState){
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new RuntimeException();
        }
    }
}
