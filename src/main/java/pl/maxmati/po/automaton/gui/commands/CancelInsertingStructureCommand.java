package pl.maxmati.po.automaton.gui.commands;

/**
 * Created by maxmati on 1/4/16.
 */
public class CancelInsertingStructureCommand extends BoardAdapterCommand {
    @Override
    public void execute() {
        adapter.cancelInsertingStructure();
    }
}
