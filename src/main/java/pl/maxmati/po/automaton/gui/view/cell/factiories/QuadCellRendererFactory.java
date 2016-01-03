package pl.maxmati.po.automaton.gui.view.cell.factiories;

import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.ColorCellRenderer;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.QuadState;

import java.util.EnumMap;

/**
 * Created by maxmati on 1/2/16.
 */
public class QuadCellRendererFactory extends CellRendererFactory {
    EnumMap<QuadState, CellRenderer> renderers = new EnumMap<>(QuadState.class);

    public QuadCellRendererFactory() {
        renderers.put(QuadState.DEAD, new ColorCellRenderer(Color.WHITE));
        renderers.put(QuadState.BLUE, new ColorCellRenderer(Color.BLUE));
        renderers.put(QuadState.GREEN, new ColorCellRenderer(Color.GREEN));
        renderers.put(QuadState.RED, new ColorCellRenderer(Color.RED));
        renderers.put(QuadState.YELLOW, new ColorCellRenderer(Color.YELLOW));
    }

    @Override
    public CellRenderer create(CellState state) {
        //noinspection SuspiciousMethodCalls
        return renderers.get(state);
    }
}
