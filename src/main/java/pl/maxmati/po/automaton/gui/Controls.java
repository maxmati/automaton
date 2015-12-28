package pl.maxmati.po.automaton.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.maxmati.po.automaton.gui.commands.TickCommand;

/**
 * Created by maxmati on 12/23/15.
 */
public class Controls {

    public static final String TICK_BUTTON_LABEL = "Tick";
    public static final String AUTO_TICK_START_LABEL = "Start";
    public static final String AUTO_TICK_STOP_LABEL = "Stop";
    public static final String CREATE_NEW_AUTOMATON_LABEL = "Create new Automaton";
    private final BoardAdapter adapter;
    private final Ticker ticker;
    VBox root = new VBox();


    public Controls(BoardAdapter adapter, Ticker ticker) {
        this.adapter = adapter;
        this.ticker = ticker;

        HBox tickSection = createTickControlSection(adapter, ticker);
        Button createAutomatonButton = new Button(CREATE_NEW_AUTOMATON_LABEL);

        root.getChildren().addAll(tickSection, createAutomatonButton);
    }

    public Node getRoot() {
        return root;
    }

    private HBox createTickControlSection(BoardAdapter adapter, Ticker ticker) {
        Button tickButton = createTickButton(adapter);
        Button autoTickButton = createAutoTickButton(ticker);
        Slider tickRateSlider = createTickRateSlider(ticker);

        return new HBox(tickButton, autoTickButton, tickRateSlider);
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
