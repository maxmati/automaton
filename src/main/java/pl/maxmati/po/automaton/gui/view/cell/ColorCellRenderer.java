package pl.maxmati.po.automaton.gui.view.cell;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.coordinates.Cords2D;

/**
 * Created by maxmati on 12/30/15.
 */
public class ColorCellRenderer  implements CellRenderer {
    private final Color color;

    public ColorCellRenderer(Color color) {
        this.color = color;
    }

    @Override
    public void render(GraphicsContext context, Cords2D position, double width, double height) {
            context.setFill(color);
            final double x = position.x * width;
            final double y = position.y * height;
            context.fillRect(x, y, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorCellRenderer that = (ColorCellRenderer) o;

        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }
}
