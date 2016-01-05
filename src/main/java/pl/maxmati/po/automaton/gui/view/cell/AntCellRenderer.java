package pl.maxmati.po.automaton.gui.view.cell;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import pl.maxmati.po.automaton.coordinates.Cords2D;

/**
 * Created by maxmati on 12/30/15.
 */
public class AntCellRenderer implements CellRenderer {
    final Image ant;
    final CellRenderer backgroundRender;
    final int rotateAngle;

    public AntCellRenderer(CellRenderer backgroundRender, int rotateAngle, boolean whiteAnt) {
        this.backgroundRender = backgroundRender;
        this.rotateAngle = rotateAngle;
        if(whiteAnt)
            ant = new Image("img/antw.png");
        else
            ant = new Image("img/ant.png");
    }

    @Override
    public void render(GraphicsContext context, Cords2D position, double width, double height) {
        final double x = position.x * width;
        final double y = position.y * height;
        backgroundRender.render(context, position, width, height);

        if(rotateAngle == 90 || rotateAngle == 270){
            double tmp = width;
            //noinspection SuspiciousNameCombination
            width = height;
            height = tmp;
        }

        context.save();
        Rotate r = new Rotate(rotateAngle, x + width / 2, y + height / 2);
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        context.drawImage(ant, x, y, width, height);
        context.restore();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AntCellRenderer that = (AntCellRenderer) o;

        if (rotateAngle != that.rotateAngle) return false;
        return backgroundRender.equals(that.backgroundRender);

    }

    @Override
    public int hashCode() {
        int result = backgroundRender.hashCode();
        result = 31 * result + rotateAngle;
        return result;
    }
}
