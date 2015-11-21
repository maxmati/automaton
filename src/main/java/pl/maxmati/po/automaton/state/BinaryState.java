package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 11/20/15
 */
public enum BinaryState implements CellState {
    DEAD, ALIVE;

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
