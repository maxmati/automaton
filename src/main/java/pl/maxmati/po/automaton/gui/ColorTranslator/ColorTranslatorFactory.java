package pl.maxmati.po.automaton.gui.ColorTranslator;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.GameOfLife;

/**
 * Created by maxmati on 12/17/15.
 */
public class ColorTranslatorFactory {
    public static ColorTranslator create(Automaton automaton) {
        if(automaton instanceof GameOfLife)
            return new BinaryCellStateColorTranslator();
        return null;
    }
}
