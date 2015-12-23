package pl.maxmati.po.automaton.gui.commands;

import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.BoardAdapter;

/**
 * Created by maxmati on 12/23/15.
 */
public class SwitchCellCommand extends BoardAdapterCommand {
    private Cords2D cord;

    public SwitchCellCommand(Cords2D cords2D) {
        cord = cords2D;
    }

    @Override
    public void execute() {
        adapter.switchCell(cord);
    }
}
