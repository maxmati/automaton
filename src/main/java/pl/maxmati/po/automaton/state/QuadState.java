package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 12/7/15.
 */
public enum QuadState implements CellState{
    DEAD, RED, YELLOW, BLUE, GREEN;

    private static QuadState[] vals = values();

    @Override
    public CellState next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public static boolean isAlive(CellState state) {
        return state != DEAD;
    }

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
