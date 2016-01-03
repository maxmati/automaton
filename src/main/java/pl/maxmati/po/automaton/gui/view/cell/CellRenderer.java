package pl.maxmati.po.automaton.gui.view.cell;

import javafx.scene.canvas.GraphicsContext;
import pl.maxmati.po.automaton.coordinates.Cords2D;

/**
 * Created by maxmati on 12/30/15.
 */
public interface CellRenderer {
    void render(GraphicsContext context, Cords2D position, double width, double height);
}
