package pl.maxmati.po.automaton.gui;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * Created by maxmati on 12/30/15.
 */
public class AntCellRenderer implements CellRenderer {
    Image ant = new Image("img/ant.png");
    CellRenderer backgroundRender;
    int rotateAngle;

    public AntCellRenderer(CellRenderer backgroundRender, int rotateAngle) {
        this.backgroundRender = backgroundRender;
        this.rotateAngle = rotateAngle;
    }

    @Override
    public void render(GraphicsContext context, Point2D position, double width, double height) {
        final double x = position.getX() * width;
        final double y = position.getY() * height;
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
}
