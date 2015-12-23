package pl.maxmati.po.automaton.gui.ColorTranslator;

import pl.maxmati.po.automaton.state.CellState;

import javafx.scene.paint.Color;

/**
 * Created by maxmati on 12/17/15.
 */
public interface ColorTranslator {
    Color translate(CellState next);
}
