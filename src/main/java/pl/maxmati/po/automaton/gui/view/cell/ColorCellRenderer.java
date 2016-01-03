package pl.maxmati.po.automaton.gui.view.cell;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by maxmati on 12/30/15.
 */
public class ColorCellRenderer  implements CellRenderer {
    private final Color color;

    public ColorCellRenderer(Color color) {
        this.color = color;
    }

    @Override
    public void render(GraphicsContext context, Point2D position, double width, double height) {
            context.setFill(color);
            final double x = position.getX() * width;
            final double y = position.getY() * height;
            context.fillRect(x, y, width, height);
    }
}
