package pl.maxmati.po.automaton.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.LangtonCellState;

import java.util.HashMap;
import java.util.Map;

import static pl.maxmati.po.automaton.automaton.AutomatonTestingUtils.compareAutomatonWithStateArray;

/**
 * Created by maxmati on 11/23/15.
 */
public class LangtonAntTest {

    private static final String[][][] SINGLE_ANT_SINGLE_STEP = new String[][][]
    {
            {
                    {"D  ", "D  "},
                    {"D1N", "D  "}
            },
            {
                    {"D  ", "D  "},
                    {"A  ", "D1E"}
            }
    };

    private static final String[][][] SINGLE_ANT_200_STEPS = new String[][][]{
            {
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D1W", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "}
            },
            {
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "A  ", "A  ", "D  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "A  ", "A  ", "A  ", "A  ", "A  ", "A  ", "D  ", "D  "},
                    {"D  ", "D  ", "A  ", "D  ", "A  ", "D  ", "D  ", "A  ", "D  ", "A  ", "D  "},
                    {"D  ", "A  ", "D  ", "D  ", "A  ", "D  ", "A  ", "D  ", "D  ", "A  ", "D  "},
                    {"D  ", "A  ", "D  ", "D  ", "A  ", "A  ", "D  ", "A  ", "D  ", "A  ", "D  "},
                    {"D  ", "A  ", "D  ", "D  ", "A  ", "D  ", "D  ", "A  ", "D  ", "A  ", "D  "},
                    {"D  ", "A  ", "D  ", "A  ", "A  ", "A  ", "D  ", "A  ", "A  ", "D  ", "D  "},
                    {"D  ", "D  ", "A  ", "A  ", "A  ", "A  ", "A  ", "D  ", "A  ", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "A  ", "A  ", "D  ", "D  ", "D  ", "D1S", "D  ", "D  "},
                    {"D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  ", "D  "}
            }
    };

    private static final String[][][] ANT_ON_EDGE = new String[][][]{
            {
                    {"D1W", "D"},
                    {"D", "D"}
            },
            {
                    {"A", "D"},
                    {"D", "D"}
            }
    };

    @Test public void TestSingleAntOnEdge(){
        Automaton a = new LangtonAnt(2,2);
        a.insertStructure(generateBinaryStateStruct(ANT_ON_EDGE[0]));

        a = a.nextState();
        compareAutomatonWithStateArray(a, generateLangtonCellStatesArray(ANT_ON_EDGE[1]), a.toString());
    }

    @Test public void TestSingleAntFor200Tics(){
        Automaton a = new LangtonAnt(11,11);
        a.insertStructure(generateBinaryStateStruct(SINGLE_ANT_200_STEPS[0]));

        for (int i = 0; i < 199; i++) {
            a = a.nextState();
        }

        compareAutomatonWithStateArray(a, generateLangtonCellStatesArray(SINGLE_ANT_200_STEPS[1]), a.toString());
    }


    @Test public void TestSingleAntForSingleTic(){
        Automaton a = new LangtonAnt(2,2);
        a.insertStructure(generateBinaryStateStruct(SINGLE_ANT_SINGLE_STEP[0]));
        a = a.nextState();

        compareAutomatonWithStateArray(a, generateLangtonCellStatesArray(SINGLE_ANT_SINGLE_STEP[1]), a.toString());
    }

    private static Map<Cords2D, LangtonCellState> generateBinaryStateStruct(String[][] source){
        Map<Cords2D, LangtonCellState> struct = new HashMap<>();
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[y].length; x++) {
                struct.put(new Cords2D(x, y), LangtonCellState.fromString(source[y][x]));
            }
        }
        return struct;
    }
    private static LangtonCellState[][] generateLangtonCellStatesArray(String[][] source){
        LangtonCellState[][] result = new LangtonCellState[source.length][source[0].length];
        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[y].length; x++) {
                result[y][x] = LangtonCellState.fromString(source[y][x]);
            }
        }
        return result;
    }

}