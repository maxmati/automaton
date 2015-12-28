package pl.maxmati.po.automaton.gui.commands;

/**
 * Created by maxmati on 12/23/15.
 */
public class TickCommand extends BoardAdapterCommand {
    @Override
    public void execute() {
        adapter.tickAutomaton();
    }
}
