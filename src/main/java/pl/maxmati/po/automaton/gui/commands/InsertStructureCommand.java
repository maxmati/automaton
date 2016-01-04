package pl.maxmati.po.automaton.gui.commands;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.structures.AutomatonStructure;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by maxmati on 1/4/16.
 */
public class InsertStructureCommand extends BoardAdapterCommand {
    private final AutomatonStructure structure;
    private final Cords2D position;

    public InsertStructureCommand(AutomatonStructure insertingStructure, Cords2D cords2D) {
        this.structure = insertingStructure;
        this.position = cords2D;
    }

    @Override
    public void execute() {
        Map<CellCoordinates, CellState> data = structure.getData().entrySet().stream().collect(Collectors.toMap(
                (e) ->{
                    Cords2D old = (Cords2D) e.getKey();
                    return new Cords2D(old.x + position.x, old.y + position.y);
                },
                Map.Entry::getValue));
        adapter.insertStructure(data);
    }
}
