package pl.maxmati.po.automaton.gui.ColorTranslator;

import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;

import javafx.scene.paint.Color;

/**
 * Created by maxmati on 12/17/15.
 */
public class BinaryCellStateColorTranslator implements ColorTranslator {
    @Override
    public Color translate(CellState state) {
        BinaryState bs = (BinaryState) state;
        switch (bs){
            case ALIVE:
                return Color.BLACK;
            case DEAD:
                return Color.WHITE;
        }
        return null;
    }
}
