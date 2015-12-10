package pl.maxmati.po.automaton.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.state.BinaryState;

import java.util.HashMap;
import java.util.Map;

import static pl.maxmati.po.automaton.automaton.AutomatonTestingUtils.compareAutomatonWithStateArray;

/**
 * Created by maxmati on 12/9/15.
 */
public class Automaton1DimTest {

    int[][] RULE30_SINGLE_TICK = new int[][]{
            {0, 1, 0},
            {1, 1, 1}
    };

    int[][] RULE30_3_TICKS = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    int[][] RULE30_15_TICKS = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1},
    };

    int[][] RULE110_15_TICKS = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    @Test
    public void testRule30SingleTick(){
        Automaton a = createInitializedAutomaton1D(RULE30_SINGLE_TICK[0], 30);

        a = a.nextState();
        compareAutomatonWithStateArray(a, generateBinaryStateArray(RULE30_SINGLE_TICK[1]),a.toString());
    }

    @Test
    public void testRule30Detailed3Ticks(){
        Automaton a = createInitializedAutomaton1D(RULE30_3_TICKS[0], 30);

        for (int i = 1; i < RULE30_3_TICKS.length; i++) {
            a = a.nextState();
            compareAutomatonWithStateArray(a, generateBinaryStateArray(RULE30_3_TICKS[i]), a.toString());
        }
    }

    @Test
    public void testRule30_15Ticks(){
        Automaton a = createInitializedAutomaton1D(RULE30_15_TICKS[0], 30);

        for (int i = 0; i < 15; i++) {
            a = a.nextState();
        }

        compareAutomatonWithStateArray(a, generateBinaryStateArray(RULE30_15_TICKS[1]), a.toString());
    }
    @Test
    public void testRule110_15Ticks(){
        Automaton a = createInitializedAutomaton1D(RULE110_15_TICKS[0], 110);

        for (int i = 0; i < 15; i++) {
            a = a.nextState();
        }

        compareAutomatonWithStateArray(a, generateBinaryStateArray(RULE110_15_TICKS[1]), a.toString());
    }

    private Automaton createInitializedAutomaton1D(int[] data, int rule) {
        Automaton a = new Automaton1Dim(data.length, new Automaton1Dim.Rule(rule));
        a.insertStructure(generateBinaryStateStruct(data));
        return a;
    }

    private BinaryState[] generateBinaryStateArray(int[] data){
        BinaryState[] struct = new BinaryState[data.length];
        for (int x = 0; x < data.length; x++) {
            struct[x] = BinaryState.valueOf(data[x]);
        }
        return struct;
    }

    private Map<Cords1D, BinaryState> generateBinaryStateStruct(int[] data){
        Map<Cords1D, BinaryState> struct = new HashMap<>();
        for (int x = 0; x < data.length; x++) {
            struct.put(new Cords1D(x), BinaryState.valueOf(data[x]));
        }
        return struct;
    }

}