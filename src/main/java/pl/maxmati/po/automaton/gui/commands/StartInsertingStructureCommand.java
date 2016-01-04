package pl.maxmati.po.automaton.gui.commands;

import pl.maxmati.po.automaton.structures.AutomatonStructure;

/**
 * Created by maxmati on 1/4/16.
 */
public class StartInsertingStructureCommand extends BoardAdapterCommand {
    AutomatonStructure structure;

    public StartInsertingStructureCommand(AutomatonStructure selectedStructure) {
        structure = selectedStructure;
    }

    @Override
    public void execute() {
        adapter.startInsertingStructure(structure);
    }
}
