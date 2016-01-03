package pl.maxmati.po.automaton.gui.view.cell;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by maxmati on 12/30/15.
 */
public interface CellRenderer {
    void render(GraphicsContext context, Point2D position, double width, double height);
}
