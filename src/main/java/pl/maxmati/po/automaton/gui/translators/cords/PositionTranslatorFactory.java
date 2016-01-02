package pl.maxmati.po.automaton.gui.translators.cords;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.Automaton2Dim;

/**
 * Created by maxmati on 12/18/15.
 */
public class PositionTranslatorFactory {
    public static PositionTranslator create(Automaton automaton) {
        if(automaton instanceof Automaton2Dim)
            return new Cords2DPositionTranslator();
        return null;
    }
}
