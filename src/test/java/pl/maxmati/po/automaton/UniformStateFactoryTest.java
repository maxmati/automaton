package pl.maxmati.po.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.factory.UniformStateFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by maxmati on 11/20/15
 */
public class UniformStateFactoryTest {

    @Test public void testAliveInitialState(){
        UniformStateFactory factory = new UniformStateFactory(BinaryState.ALIVE);

        for(int i = -10; i <= 10; ++i)
            for(int j = -10; j <= 10; ++j)
                assertEquals(String.format("Wrong state for [%d:%d]", i, j),
                        BinaryState.ALIVE, factory.initialState(new Cords2D(i, j)));
    }

    @Test public void testDeadInitialState(){
        UniformStateFactory factory = new UniformStateFactory(BinaryState.DEAD);

        for(int i = -10; i <= 10; ++i)
            for(int j = -10; j <= 10; ++j)
                assertEquals(String.format("Wrong state for [%d:%d]", i, j),
                        BinaryState.DEAD, factory.initialState(new Cords2D(i, j)));
    }

}