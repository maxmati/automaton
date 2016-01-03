package pl.maxmati.po.automaton.gui.view.cell.factiories;

import pl.maxmati.po.automaton.gui.view.cell.AntCellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.state.BinaryState;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.state.LangtonAntState;
import pl.maxmati.po.automaton.state.LangtonCellState;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by maxmati on 12/30/15.
 */
public class LangtonAntCellRendererFactory extends CellRendererFactory {
    EnumMap<BinaryState, HashMap<LangtonAntState, CellRenderer> > renderers = new EnumMap<>(BinaryState.class);

    public LangtonAntCellRendererFactory() {

        BinaryCellRendererFactory binaryCellRendererFactory = new BinaryCellRendererFactory();

        for (BinaryState bs : BinaryState.values()){
            renderers.put(bs , new HashMap<>());
            CellRenderer backgroundRenderer = binaryCellRendererFactory.create(bs);
            renderers.get(bs).put(null, backgroundRenderer);
            for (LangtonAntState as: LangtonAntState.values()){
                int rotationAngle = getRotationAngle(as);
                renderers.get(bs).put(as, new AntCellRenderer(backgroundRenderer, rotationAngle));
            }
        }

    }

    private int getRotationAngle(LangtonAntState as) {
        int rotationAngle = 0;
        switch (as){
            case NORTH:
                rotationAngle = 0;
                break;
            case EAST:
                rotationAngle = 90;
                break;
            case SOUTH:
                rotationAngle = 180;
                break;
            case WEST:
                rotationAngle = 270;
                break;
        }
        return rotationAngle;
    }

    @Override
    public CellRenderer create(CellState next) {
        LangtonCellState lcs = (LangtonCellState) next;

        return renderers.get(lcs.cellState).get(lcs.antState);
    }
}
