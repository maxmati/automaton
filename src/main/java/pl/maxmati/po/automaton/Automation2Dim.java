package pl.maxmati.po.automaton;

/**
 * Created by maxmati on 11/20/15.
 */
public abstract class Automation2Dim extends Automaton {
    private int width;
    private int height;

    @Override
    protected boolean hasNextCoordinates(CellCoordinates coordinates) {
        //TODO: implement
        return false;
    }

    @Override
    protected CellCoordinates initialCoordinates(CellCoordinates coordinates) {
        //TODO: implement
        return null;
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coordinates) {
        //TODO: implement
        return null;
    }
}
