package pl.maxmati.po.automaton.gui.CordsTranslator;

import javafx.geometry.Point2D;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;

/**
 * Created by maxmati on 12/18/15.
 */
public class Cords2DPositionTranslator implements PositionTranslator {
    @Override
    public Point2D translate(CellCoordinates cords) {
        Cords2D c = (Cords2D) cords;
        return new Point2D(c.x,c.y);
    }
}
