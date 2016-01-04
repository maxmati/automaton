package pl.maxmati.po.automaton.gui.commands;

import pl.maxmati.po.automaton.coordinates.Cords2D;

/**
 * Created by maxmati on 1/4/16.
 */
public class FinishInsertingStructureCommand extends BoardAdapterCommand {
    private final Cords2D position;

    public FinishInsertingStructureCommand(Cords2D cords2D) {
        this.position = cords2D;
    }

    @Override
    public void execute() {
//        Map<CellCoordinates, CellState> data = structure.getData().entrySet().stream().collect(Collectors.toMap(
//                (e) ->{
//                    Cords2D old = (Cords2D) e.getKey();
//                    return new Cords2D(old.x + position.x, old.y + position.y);
//                },
//                Map.Entry::getValue));
        adapter.finishInsertingStructure(position);
    }
}
