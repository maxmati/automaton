package pl.maxmati.po.automaton.gui.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.maxmati.po.automaton.gui.commands.CreateAutomatonCommand;
import pl.maxmati.po.automaton.gui.commands.TickCommand;
import pl.maxmati.po.automaton.gui.controller.BoardAdapter;
import pl.maxmati.po.automaton.gui.controller.Ticker;

import java.util.Map;
import java.util.Optional;

/**
 * Created by maxmati on 12/23/15.
 */
public class Controls {

    private static final String TICK_BUTTON_LABEL = "Tick";
    private static final String AUTO_TICK_START_LABEL = "Start";
    private static final String AUTO_TICK_STOP_LABEL = "Stop";
    private static final String CREATE_NEW_AUTOMATON_LABEL = "Create new Automaton";
    private static final String TICK_RATE_LABEL = "Tick rate";

    VBox root = new VBox();


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

        root.getChildren().addAll(tickSection, createAutomatonButton);

        root.setAlignment(Pos.TOP_CENTER);
        root.setMinWidth(340);
        root.setMaxWidth(400);
        root.setSpacing(10);
    }

    public Node getRoot() {
        return root;
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
        Slider tickRateSlider = new Slider(1, 2000, ticker.getRate());
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
