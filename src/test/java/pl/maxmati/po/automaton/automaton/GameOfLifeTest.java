package pl.maxmati.po.automaton.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.BinaryState;

import java.util.HashMap;
import java.util.Map;

import static pl.maxmati.po.automaton.automaton.AutomatonTestingUtils.compareAutomatonWithStateArray;

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

    private static final int[][][] WRAPPED_BLINKER = new int[][][]{
            {
                    {1, 1, 0, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0},
                    {1, 0, 0, 0}
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


    private static final int[][][] B3678S34678RULE_TESTDATA = {
            {
                    {1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1},
                    {0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 1, 0}
            },
            {
                    {0, 1, 0, 1, 0, 0},
                    {0, 0, 1, 0, 1, 0},
                    {0, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0, 0}
            }
    };

    @Test public void testB3678S34678Rule(){
        Automaton a = new GameOfLife(6, 4, false, new GameOfLife.Rule("34678/3678"));
        a.insertStructure(generateBinaryStateStruct(B3678S34678RULE_TESTDATA[0]));

        a = a.nextState();

        compareAutomatonWithStateArray(a, generateBinaryStateArray(B3678S34678RULE_TESTDATA[1]), a.toString());
    }

    @Test
    public void testInitialization(){
        Automaton a = new GameOfLife(5,5);

        compareAutomatonWithStateArray(a, generateBinaryStateArray(EMPTY),"");
    }

    @Test
    public void testInsertStructure(){
        Automaton a = createGameOfLifeWithState(RANDOM, false);

        compareAutomatonWithStateArray(a, generateBinaryStateArray(RANDOM),"");
    }

    @Test
    public void testWrappedBlinker(){
        checkCycle(WRAPPED_BLINKER, true);
    }

    @Test
    public void testBeehive(){
        checkCycle(BEEHIVE, false);
    }

    @Test
    public void testBlinker(){
        checkCycle(BLINKER, false);
    }

    private void checkCycle(int[][][] cycle, boolean wrapping) {
        Automaton a = createGameOfLifeWithState(cycle[0], wrapping);

        for (int i = 1; i < cycle.length + 1; i++) {
            a = a.nextState();
            compareAutomatonWithStateArray(
                    a,
                    generateBinaryStateArray(cycle[i % cycle.length]),
                    String.format("on tick %d", i)
            );
        }
    }

    private Automaton createGameOfLifeWithState(int[][] state, boolean wrapping) {
        Automaton a = new GameOfLife(state[0].length, state.length, wrapping);
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

}