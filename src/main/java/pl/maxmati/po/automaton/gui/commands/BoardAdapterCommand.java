package pl.maxmati.po.automaton.gui.commands;

import pl.maxmati.po.automaton.gui.BoardAdapter;

/**
 * Created by maxmati on 12/23/15.
 */
public abstract class BoardAdapterCommand implements Command{
    protected BoardAdapter adapter;

    public void setAdapter(BoardAdapter adapter) {
        this.adapter = adapter;
    }
}
