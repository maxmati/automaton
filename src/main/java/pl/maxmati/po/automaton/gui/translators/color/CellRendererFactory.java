package pl.maxmati.po.automaton.gui.translators.color;

import pl.maxmati.po.automaton.automaton.*;
import pl.maxmati.po.automaton.gui.CellRenderer;
import pl.maxmati.po.automaton.state.CellState;

/**
 * Created by maxmati on 12/17/15.
 */
public abstract class CellRendererFactory {
    public static CellRendererFactory createFactory(Automaton automaton) {
        if(automaton instanceof GameOfLife)
            return new BinaryCellRendererFactory();
        else if(automaton instanceof WireWorld)
            return new WireWorldCellRendererFactory();
        else if(automaton instanceof LangtonAnt)
            return new LangtonAntCellRendererFactory();
        else if(automaton instanceof QuadLife)
            return new QuadCellRendererFactory();
        return null;
    }
    public abstract CellRenderer create(CellState state);

}
