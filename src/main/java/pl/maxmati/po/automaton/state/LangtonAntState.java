package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 11/23/15.
 */
public enum LangtonAntState {
    NORTH, EAST, SOUTH, WEST;

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

    public LangtonAntState next() {
        return rotateAntRight(this);
    }
}
