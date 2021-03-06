package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;
import pl.maxmati.po.automaton.state.factory.CellStateFactory;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Base for implementation of All 2D Automatons
 *
 */
public abstract class Automaton2Dim extends Automaton {
    final int width;
    final int height;


    /**
     * Createts 2D Automaton with specified {@link CellNeighborhood}, {@link CellStateFactory}, width and height
     *
     * @param neighborhoodStrategy {@link CellNeighborhood} to use.
     * @param stateFactory {@link CellStateFactory} to use.
     * @param width Width of Automaton.
     * @param height Height of Automaton.
     */
    protected Automaton2Dim(CellNeighborhood neighborhoodStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighborhoodStrategy, stateFactory);
        this.width = width;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasNextCoordinates(CellCoordinates coordinates) {
        if(coordinates instanceof Cords2D){
            Cords2D cords = calculateNextCords( (Cords2D) coordinates );
            return areCordsValid(cords);
        }else {
            throw new NotSupportedCellCoordinates();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates initialCoordinates() {
        return new Cords2D(-1,0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coordinates) {
        if(coordinates instanceof Cords2D){
            Cords2D cords = calculateNextCords( (Cords2D) coordinates );
            if(!areCordsValid(cords)){
                return null;
            }
            return cords;

        }else {
            throw new NotSupportedCellCoordinates();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String stringifyCells() {
        StringBuilder out = new StringBuilder();

        for(Cell c : this){
            if(!(c.cords instanceof Cords2D))
                throw new NotSupportedCellCoordinates();
            Cords2D cords = (Cords2D) c.cords;
            out.append(c.state);
            if(cords.x < width - 1){
                out.append(' ');
            } else {
                out.append('\n');
            }
        }

        return out.toString();
    }

    private boolean areCordsValid(Cords2D cords) {
        return cords.x >= 0 && cords.x < width && cords.y >= 0 && cords.y < height;
    }

    private Cords2D calculateNextCords(Cords2D cords) {
        int x = cords.x + 1;
        int y = cords.y;
        if(x >= width) {
            x = 0;
            ++y;
        }

        return new Cords2D(x,y);
    }

    @Override
    public String toString() {
        return "Automaton2Dim\n" +
                "width=" + width + " height=" + height + '\n' +
                super.toString();
    }
}
