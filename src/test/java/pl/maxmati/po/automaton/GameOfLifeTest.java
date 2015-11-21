package pl.maxmati.po.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 11/20/15
 */
public class GameOfLifeTest {

    private static final int[][][] BEEHIVE = new int[][][]{
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            }
    };

    private static final int[][][] BLINKER = new int[][][]{
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            }, {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            }
    };
    private static final int[][] EMPTY = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };

    private static final int[][] RANDOM = {
            {1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0}
    };

    @Test
    public void testInitialization(){
        Automaton a = new GameOfLife(5,5);

        compareAutomatonWithStateArray(a, generateBinaryStateArray(EMPTY));
    }

    @Test
    public void testInsertStructure(){
        Automaton a = createGameOfLifeWithState(RANDOM);

        compareAutomatonWithStateArray(a, generateBinaryStateArray(RANDOM));
    }

    @Test
    public void testBeehive(){
        checkCycle(BEEHIVE);
    }

    @Test
    public void testBlinker(){
        checkCycle(BLINKER);
    }

    private void checkCycle(int[][][] cycle) {
        Automaton a = createGameOfLifeWithState(cycle[0]);

        for (int i = 1; i < cycle.length + 1; i++) {
            a = a.nextState();
            compareAutomatonWithStateArray(a, generateBinaryStateArray(cycle[i % cycle.length]));
        }
    }

    private Automaton createGameOfLifeWithState(int[][] state) {
        Automaton a = new GameOfLife(state[0].length, state.length);
        a.insertStructure(generateBinaryStateStruct(state));
        return a;
    }

    private BinaryState[][] generateBinaryStateArray(int[][] data){
        BinaryState[][] struct = new BinaryState[data.length][data[0].length];
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct[y][x] = BinaryState.valueOf(data[y][x]);
            }
        }
        return struct;
    }

    private Map<Cords2D, BinaryState> generateBinaryStateStruct(int[][] data){
        Map<Cords2D, BinaryState> struct = new HashMap<>();
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct.put(new Cords2D(x, y), BinaryState.valueOf(data[y][x]));
            }
        }
        return struct;
    }

    private void compareAutomatonWithStateArray(Automaton a, CellState[][] state){
        boolean hitArray[][] = new boolean[state.length][state[0].length];
        for(Cell c: a){
            if(!(c.cords instanceof Cords2D))
                fail("Cell cords should be instance of Cords2D");
            Cords2D cords = (Cords2D) c.cords;
            assertEquals(String.format("Wrong state for [%d,%d]",cords.x,cords.y), state[cords.y][cords.x], c.state);
            assertFalse(String.format("Double state for [%d,%d]",cords.x,cords.y),hitArray[cords.y][cords.x]);
            hitArray[cords.y][cords.x] = true;
        }

        for (int y = 0; y < hitArray.length; y++) {
            for (int x = 0; x < hitArray[y].length; x++) {
                assertTrue(String.format("Missing state for [%d,%d]",x,y), hitArray[y][x]);
            }
        }
    }

}