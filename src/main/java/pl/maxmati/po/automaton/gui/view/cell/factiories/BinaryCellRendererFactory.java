package pl.maxmati.po.automaton.gui.view.cell.factiories;

import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.ColorCellRenderer;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;

import java.util.EnumMap;

/**
 * Created by maxmati on 12/17/15.
 */
public class BinaryCellRendererFactory extends CellRendererFactory {
    EnumMap<BinaryState, CellRenderer> renderers = new EnumMap<>(BinaryState.class);

    public BinaryCellRendererFactory() {
        renderers.put(BinaryState.ALIVE, new ColorCellRenderer(Color.BLACK));
        renderers.put(BinaryState.DEAD, new ColorCellRenderer(Color.WHITE));
    }

    @Override
    public CellRenderer create(CellState state) {
        //noinspection SuspiciousMethodCalls
        return renderers.get(state);
    }
}
