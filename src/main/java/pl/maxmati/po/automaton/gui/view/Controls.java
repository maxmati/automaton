package pl.maxmati.po.automaton.gui.view;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.maxmati.po.automaton.gui.commands.CancelInsertingStructureCommand;
import pl.maxmati.po.automaton.gui.commands.CreateAutomatonCommand;
import pl.maxmati.po.automaton.gui.commands.StartInsertingStructureCommand;
import pl.maxmati.po.automaton.gui.commands.TickCommand;
import pl.maxmati.po.automaton.gui.controller.BoardAdapter;
import pl.maxmati.po.automaton.gui.controller.Ticker;
import pl.maxmati.po.automaton.structures.AutomatonStructure;
import pl.maxmati.po.automaton.structures.StructureLoader;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * GUI element responsible for rendering all controls for Automaton
 *
 */
public class Controls {

    private static final String TICK_BUTTON_LABEL = "Tick";
    private static final String AUTO_TICK_START_LABEL = "Start";
    private static final String AUTO_TICK_STOP_LABEL = "Stop";
    private static final String CREATE_NEW_AUTOMATON_LABEL = "Create new Automaton";
    private static final String TICK_RATE_LABEL = "Tick rate";
    private static final String INSERT_BUTTON_LABEL = "Insert";
    private static final String INSET_BUTTON_DONE_LABEL = "Done";

    private boolean insertingStructure = false;
    private String currentAutomatonName;

    VBox root = new VBox();

    /**
     * Creates new control panel.
     *
     * @param adapter {@link BoardAdapter} which is controlled.
     * @param ticker {@link Ticker} used for automatic ticking.
     */
    public Controls(BoardAdapter adapter, Ticker ticker) {
        HBox tickSection = createTickControlSection(adapter, ticker);
        Button createAutomatonButton = new Button(CREATE_NEW_AUTOMATON_LABEL);

        createAutomatonButton.setOnAction(actionEvent -> {
            Optional<Map<String, Object>> response = new CreateAutomatonDialog().show();

            response.ifPresent(params -> {
                final String type = (String) params.get(CreateAutomatonDialog.AUTOMATON_TYPE_KEY);
                params.remove(CreateAutomatonDialog.AUTOMATON_TYPE_KEY);
                adapter.dispatchCommand(new CreateAutomatonCommand(type, params));
            });

        });

        HBox insertStructureSection = createInsertStructureSection(adapter);

        root.getChildren().addAll(tickSection, createAutomatonButton, insertStructureSection);

        root.setAlignment(Pos.TOP_CENTER);
        root.setMinWidth(340);
        root.setMaxWidth(400);
        root.setSpacing(10);
    }

    public Node getRoot() {
        return root;
    }

    private HBox createInsertStructureSection(BoardAdapter adapter) {
        HBox insertStructureSection = new HBox();
        insertStructureSection.setSpacing(20);
        insertStructureSection.setAlignment(Pos.CENTER);

        ComboBox<AutomatonStructure> structureSelectionComboBox = createInsertStructureCombobox(adapter);
        Button insertStructureButton = createInsertStructureButton(adapter, structureSelectionComboBox);

        insertStructureSection.getChildren().addAll(structureSelectionComboBox, insertStructureButton);
        return insertStructureSection;
    }

    private Button createInsertStructureButton(BoardAdapter adapter, ComboBox<AutomatonStructure> structureSelectionComboBox) {
        Button insertStructureButton = new Button(INSERT_BUTTON_LABEL);
        insertStructureButton.setOnAction(actionEvent -> {
            if(insertingStructure) {
                adapter.dispatchCommand(new CancelInsertingStructureCommand());
                insertingStructure = false;
                insertStructureButton.textProperty().setValue(INSERT_BUTTON_LABEL);
            }
            else {
                AutomatonStructure selectedStructure = structureSelectionComboBox.getValue();
                if(selectedStructure == null) return;
                adapter.dispatchCommand(new StartInsertingStructureCommand(selectedStructure));
                insertingStructure = true;
                insertStructureButton.textProperty().setValue(INSET_BUTTON_DONE_LABEL);
            }
            structureSelectionComboBox.disableProperty().setValue(insertingStructure);
        });
        return insertStructureButton;
    }

    private ComboBox<AutomatonStructure> createInsertStructureCombobox(BoardAdapter adapter) {
        currentAutomatonName = adapter.getAutomatonName();
        ComboBox<AutomatonStructure> structureSelectionComboBox = new ComboBox<>();
        updateStructureSelectionCombobox(structureSelectionComboBox);

        adapter.addObserver((o, arg) -> {
            if(currentAutomatonName.equals(adapter.getAutomatonName())) return;

            currentAutomatonName = adapter.getAutomatonName();
            updateStructureSelectionCombobox(structureSelectionComboBox);
        });
        return structureSelectionComboBox;
    }

    private void updateStructureSelectionCombobox(ComboBox<AutomatonStructure> structureSelectionComboBox) {
        final List<AutomatonStructure> availableStructures = StructureLoader.getAvailableStructures(currentAutomatonName);
        structureSelectionComboBox.setItems(
                FXCollections.observableArrayList(
                        availableStructures
                ));
        if(availableStructures.size() > 0)
            structureSelectionComboBox.setValue(availableStructures.get(0));
    }


    private HBox createTickControlSection(BoardAdapter adapter, Ticker ticker) {
        Button tickButton = createTickButton(adapter);
        Button autoTickButton = createAutoTickButton(ticker);
        Slider tickRateSlider = createTickRateSlider(ticker);
        HBox hbox = new HBox(tickButton, autoTickButton, new Label(TICK_RATE_LABEL), tickRateSlider);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }

    private Slider createTickRateSlider(Ticker ticker) {
        Slider tickRateSlider = new Slider(10, 2000, ticker.getRate());
        tickRateSlider.setMajorTickUnit(1);
        tickRateSlider.setBlockIncrement(1);
        tickRateSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> ticker.setRate(newValue.intValue()));
        return tickRateSlider;
    }

    private Button createAutoTickButton(Ticker ticker) {
        Button autoTickButton  = new Button(AUTO_TICK_START_LABEL);
        autoTickButton.setOnAction(actionEvent -> {
            if(ticker.isActive()){
                ticker.stop();
                autoTickButton.textProperty().setValue(AUTO_TICK_START_LABEL);
            } else {
                ticker.start();
                autoTickButton.textProperty().setValue(AUTO_TICK_STOP_LABEL);
            }
        });
        return autoTickButton;
    }

    private Button createTickButton(BoardAdapter adapter) {
        Button tickButton = new Button(TICK_BUTTON_LABEL);
        tickButton.setOnAction(actionEvent -> adapter.dispatchCommand(new TickCommand()));
        return tickButton;
    }

}
