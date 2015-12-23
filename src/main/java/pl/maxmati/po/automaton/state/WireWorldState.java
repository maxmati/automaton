package pl.maxmati.po.automaton.state;

/**
 * Created by maxmati on 11/22/15
 */
public enum WireWorldState implements CellState{
    VOID, WIRE, HEAD, TAIL;

    private static WireWorldState[] vals = values();

    @Override
    public CellState next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

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
