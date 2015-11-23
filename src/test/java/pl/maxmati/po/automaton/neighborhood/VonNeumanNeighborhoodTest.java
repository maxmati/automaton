package pl.maxmati.po.automaton.neighborhood;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 11/23/15
 */
public class VonNeumanNeighborhoodTest {

    @Test
    public void testVonNeumanRadius1AndCenter2And3(){
        VonNeumanNeighborhood neighborhood = new VonNeumanNeighborhood(1);
        Set<CellCoordinates> neighbors = neighborhood.cellNeighbors(new Cords2D(2, 3));

        Set<CellCoordinates> expected = new HashSet<>();
        expected.add(new Cords2D(1,3));
        expected.add(new Cords2D(3,3));

        expected.add(new Cords2D(2,2));
        expected.add(new Cords2D(2,4));

        assertEquals(expected, neighbors);
    }

    @Test
    public void testVonNeumanRadius2AndCenter0And0(){
        VonNeumanNeighborhood neighborhood = new VonNeumanNeighborhood(2);
        Set<CellCoordinates> neighbors = neighborhood.cellNeighbors(new Cords2D(0,0));

        Set<CellCoordinates> expected = new HashSet<>();
        expected.add(new Cords2D(0, 2));

        expected.add(new Cords2D(-1, 1));
        expected.add(new Cords2D(0, 1));
        expected.add(new Cords2D(1, 1));

        expected.add(new Cords2D(-2, 0));
        expected.add(new Cords2D(-1, 0));
        expected.add(new Cords2D(1, 0));
        expected.add(new Cords2D(2, 0));

        expected.add(new Cords2D(-1, -1));
        expected.add(new Cords2D(0, -1));
        expected.add(new Cords2D(1, -1));

        expected.add(new Cords2D(0, -2));

        assertEquals(expected, neighbors);
    }

    @Test(expected = NotSupportedCellCoordinates.class)
    public void testNotSupportedCellCoordinatesException(){
        VonNeumanNeighborhood neighborhood = new VonNeumanNeighborhood(2);
        neighborhood.cellNeighbors(new CellCoordinates() {});
    }

}