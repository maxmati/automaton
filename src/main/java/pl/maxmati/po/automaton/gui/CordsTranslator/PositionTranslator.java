package pl.maxmati.po.automaton.gui.CordsTranslator;

import javafx.geometry.Point2D;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;

/**
 * Created by maxmati on 12/18/15.
 */
public interface PositionTranslator {
    Point2D translate(CellCoordinates cords);
}
