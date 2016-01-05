package pl.maxmati.po.automaton.structures;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.exceptions.AutomatonNotFoundException;
import pl.maxmati.po.automaton.exceptions.UnsupportedCellLoader;
import pl.maxmati.po.automaton.exceptions.UnsupportedCordsLoader;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.structures.cellLoader.BinaryStateCellLoader;
import pl.maxmati.po.automaton.structures.cellLoader.CellLoader;
import pl.maxmati.po.automaton.structures.cellLoader.QuadStateCellLoader;
import pl.maxmati.po.automaton.structures.cellLoader.WireWorldStateCellLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by maxmati on 1/4/16.
 */
public class StructureLoader {
    private static Map<String, String> automatonFolderMap = new HashMap<>();

    static {
        automatonFolderMap.put("Game of Life", "gol");
        automatonFolderMap.put("Wire World", "wire_world");
        automatonFolderMap.put("Langton Ant", "langton_ant");
        automatonFolderMap.put("Quad Life", "quad_life");
        automatonFolderMap.put("Single dimension Automaton", "1dim");
    }

    public static List<AutomatonStructure> getAvailableStructures(String automatonName){
        String automatonDirectoryName = automatonFolderMap.get(automatonName);
        if(automatonDirectoryName == null)
            throw new AutomatonNotFoundException(automatonName);
        final String automatonDirectoryPath = "structures/" + automatonDirectoryName + "/";
        final String infoFilePath = automatonDirectoryPath + "list.info";
        InputStream infoFileStream = StructureLoader.class.getClassLoader().getResourceAsStream(infoFilePath);
        if(infoFileStream == null)
            throw new AutomatonNotFoundException(automatonName);

        List<AutomatonStructure> structures = new ArrayList<>();


        try(BufferedReader infoFileReader = new BufferedReader(new InputStreamReader(infoFileStream))) {
            String line;
            while (( line = infoFileReader.readLine()) != null){
                String data[] = line.split(" ");
                final String name = data[0];
                final String path = automatonDirectoryPath + data[1];
                structures.add(new AutomatonStructure(name, path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return structures;
    }

    static Map<CellCoordinates, CellState> loadStructure(String path){
        Map<CellCoordinates,CellState> structure = new HashMap<>();

        InputStream stream = StructureLoader.class.getClassLoader().getResourceAsStream(path);
        if(stream == null) throw new RuntimeException();
        Scanner scanner = new Scanner(stream);
        String cordsType = scanner.next();
        String cellType = scanner.next();

        CordsLoader cordsLoader = createCordsLoader(cordsType);
        CellLoader cellLoader = createCellLoader(cellType);

        cordsLoader.initSize(scanner);
        while (cordsLoader.hasNext()){
            structure.put(cordsLoader.next(), cellLoader.getCell(scanner.next()));
        }

        return structure;
    }

    private static CellLoader createCellLoader(String cellType) {
        if(cellType.equals("BinaryState"))
            return new BinaryStateCellLoader();
        if(cellType.equals("WireWorldState"))
            return new WireWorldStateCellLoader();
        if(cellType.equals("QuadState"))
            return new QuadStateCellLoader();
        throw new UnsupportedCellLoader(cellType);
    }

    private static CordsLoader createCordsLoader(String cordsType) {
        if(cordsType.equals("Cords2D"))
            return new Cords2DLoader();

        throw new UnsupportedCordsLoader(cordsType);
    }
}
