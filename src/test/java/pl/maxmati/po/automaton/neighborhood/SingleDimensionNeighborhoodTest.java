package pl.maxmati.po.automaton.neighborhood;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 12/7/15.
 */
public class SingleDimensionNeighborhoodTest {

    @Test public void testRadius1Center5(){
        CellNeighborhood neighborhood = new SingleDimensionNeighborhood(1);
        Set<CellCoordinates> expected = new HashSet<CellCoordinates>(Arrays.asList(new Cords1D(4), new Cords1D(6)));

        assertEquals(expected, neighborhood.cellNeighbors(new Cords1D(5)));
    }

    @Test public void testRadius3Center3(){
        CellNeighborhood neighborhood = new SingleDimensionNeighborhood(3);
        Set<CellCoordinates> expected = new HashSet<CellCoordinates>(Arrays.asList(
                new Cords1D(0), new Cords1D(1), new Cords1D(2), new Cords1D(4), new Cords1D(5), new Cords1D(6)
        ));

        assertEquals(expected, neighborhood.cellNeighbors(new Cords1D(3)));
    }
    @Test(expected = NotSupportedCellCoordinates.class) public void testWrongCordType(){
        CellNeighborhood neighborhood = new SingleDimensionNeighborhood(2);
        neighborhood.cellNeighbors(new CellCoordinates() {});
    }

}