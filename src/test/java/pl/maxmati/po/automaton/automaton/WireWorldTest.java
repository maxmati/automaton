package pl.maxmati.po.automaton.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.WireWorldState;

import java.util.HashMap;
import java.util.Map;

import static pl.maxmati.po.automaton.automaton.AutomatonTestingUtils.compareAutomatonWithStateArray;

/**
 * Created by maxmati on 11/22/15
 */
public class WireWorldTest {

    @Test
    public void testWire(){
        Automaton a = createWireWorldWithState(WireWorldTestData.WIRE[0]);

        for(int i = 1; i < WireWorldTestData.WIRE.length; ++i){
            a = a.nextState();
            compareAutomatonWithStateArray(a, generateWireWorldStateArray(WireWorldTestData.WIRE[i]), String.format("on tick %d.", i));
        }
    }

    @Test
    public void testDiodes(){
        Automaton a = createWireWorldWithState(WireWorldTestData.DIODES[0]);

        for (int i = 1; i < WireWorldTestData.DIODES.length; i++) {
            a = a.nextState();
            compareAutomatonWithStateArray(a, generateWireWorldStateArray(WireWorldTestData.DIODES[i]), String.format("on tick %d", i));
        }
    }

    private Automaton createWireWorldWithState(int[][] state) {
        Automaton a = new WireWorld(state[0].length, state.length);
        a.insertStructure(generateWireWorldStateStruct(state));
        return a;
    }

    private WireWorldState[][] generateWireWorldStateArray(int[][] data){
        WireWorldState[][] struct = new WireWorldState[data.length][data[0].length];
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct[y][x] = WireWorldState.valueOf(data[y][x]);
            }
        }
        return struct;
    }

    private Map<Cords2D, WireWorldState> generateWireWorldStateStruct(int[][] data){
        Map<Cords2D, WireWorldState> struct = new HashMap<>();
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct.put(new Cords2D(x, y), WireWorldState.valueOf(data[y][x]));
            }
        }
        return struct;
    }

}