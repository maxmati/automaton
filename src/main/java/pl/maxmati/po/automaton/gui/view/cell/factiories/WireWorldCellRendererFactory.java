package pl.maxmati.po.automaton.gui.view.cell.factiories;

import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.ColorCellRenderer;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.WireWorldState;

import java.util.EnumMap;

/**
 * Created by maxmati on 12/30/15.
 */
public class WireWorldCellRendererFactory extends CellRendererFactory{
    EnumMap<WireWorldState, CellRenderer> renderers = new EnumMap<>(WireWorldState.class);

    public WireWorldCellRendererFactory() {
        renderers.put(WireWorldState.VOID, new ColorCellRenderer(Color.BLACK));
        renderers.put(WireWorldState.WIRE, new ColorCellRenderer(Color.YELLOW));
        renderers.put(WireWorldState.HEAD, new ColorCellRenderer(Color.BLUE));
        renderers.put(WireWorldState.TAIL, new ColorCellRenderer(Color.RED));
    }

    @Override
    public CellRenderer create(CellState state) {
        //noinspection SuspiciousMethodCalls
        return renderers.get(state);
    }
}
