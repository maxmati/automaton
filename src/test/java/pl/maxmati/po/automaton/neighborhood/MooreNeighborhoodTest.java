package pl.maxmati.po.automaton.neighborhood;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by maxmati on 11/20/15
 */
public class MooreNeighborhoodTest {

    @Test(expected = NotSupportedCellCoordinates.class)
    public void testUnknownCellCoordinatesType(){
        MooreNeighborhood mooreNeighborhood = new MooreNeighborhood(1);

        mooreNeighborhood.cellNeighbors(new CellCoordinates() { });
    }

    @Test
    public void testRadius1Center0And2Size4by3Wrap(){
        MooreNeighborhood mooreNeighborhood = new MooreNeighborhood(1, true, 4, 3);
        Set<CellCoordinates> neighbors = mooreNeighborhood.cellNeighbors(new Cords2D(0,2));

        Set<CellCoordinates> expected = new HashSet<>();
        expected.add(new Cords2D(3, 1));
        expected.add(new Cords2D(0, 1));
        expected.add(new Cords2D(1, 1));

        expected.add(new Cords2D(3, 2));
        expected.add(new Cords2D(1, 2));

        expected.add(new Cords2D(3, 0));
        expected.add(new Cords2D(0, 0));
        expected.add(new Cords2D(1, 0));

        assertEquals(expected, neighbors);
    }

    @Test
    public void testRadius1Center3And5(){
        MooreNeighborhood mooreNeighborhood = new MooreNeighborhood(1);

        Set<CellCoordinates> neighborhood = mooreNeighborhood.cellNeighbors(new Cords2D(3, 5));

        Set<CellCoordinates> expected = new HashSet<>();
        expected.add(new Cords2D(2,4));
        expected.add(new Cords2D(3,4));
        expected.add(new Cords2D(4,4));
        expected.add(new Cords2D(2,5));
        expected.add(new Cords2D(4,5));
        expected.add(new Cords2D(2,6));
        expected.add(new Cords2D(3,6));
        expected.add(new Cords2D(4,6));

        assertEquals(expected, neighborhood);
    }

    @Test public void testRadius2Center0And0(){
        MooreNeighborhood mooreNeighborhood = new MooreNeighborhood(2);

        Set<CellCoordinates> neighborhood = mooreNeighborhood.cellNeighbors(new Cords2D(0, 0));

        Set<CellCoordinates> expected = new HashSet<>();
        expected.add(new Cords2D(-2,-2));
        expected.add(new Cords2D(-1,-2));
        expected.add(new Cords2D( 0,-2));
        expected.add(new Cords2D( 1,-2));
        expected.add(new Cords2D( 2,-2));

        expected.add(new Cords2D(-2,-1));
        expected.add(new Cords2D(-1,-1));
        expected.add(new Cords2D( 0,-1));
        expected.add(new Cords2D( 1,-1));
        expected.add(new Cords2D( 2,-1));

        expected.add(new Cords2D(-2, 0));
        expected.add(new Cords2D(-1, 0));
        expected.add(new Cords2D( 1, 0));
        expected.add(new Cords2D( 2, 0));

        expected.add(new Cords2D(-2, 1));
        expected.add(new Cords2D(-1, 1));
        expected.add(new Cords2D( 0, 1));
        expected.add(new Cords2D( 1, 1));
        expected.add(new Cords2D( 2, 1));

        expected.add(new Cords2D(-2, 2));
        expected.add(new Cords2D(-1, 2));
        expected.add(new Cords2D( 0, 2));
        expected.add(new Cords2D( 1, 2));
        expected.add(new Cords2D( 2, 2));

        assertEquals(expected, neighborhood);

    }

    @Test
    public void testToString() throws Exception {
        MooreNeighborhood neighborhood = new MooreNeighborhood(3);

        assertEquals("MooreNeighborhood{3}", neighborhood.toString());
    }
}