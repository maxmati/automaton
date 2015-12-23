package pl.maxmati.po.automaton.gui.CordsTranslator;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.GameOfLife;

/**
 * Created by maxmati on 12/18/15.
 */
public class PositionTranslatorFactory {
    public static PositionTranslator create(Automaton automaton) {
        if(automaton instanceof GameOfLife)
            return new Cords2DPositionTranslator();
        return null;
    }
}
