package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 11/22/15
 */
public enum WireWorldState implements CellState{
    VOID, WIRE, HEAD, TAIL;

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
