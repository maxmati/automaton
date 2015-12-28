package pl.maxmati.po.automaton.gui;

/**
 * Created by maxmati on 12/15/15.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import pl.maxmati.po.automaton.automaton.GameOfLife;

public class Main extends Application {

    public static final String TITLE = "Automaton";
    CommandQueue queue = new CommandQueue();
    Ticker ticker = null;
    BoardAdapter adapter = null;
    Board board = null;
    Controls controls = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        adapter = new BoardAdapter(queue, 100, 100, new GameOfLife(100, 100));
        ticker = new Ticker(adapter);
        board = new Board(adapter);
        controls = new Controls(adapter, ticker);

        SplitPane sp = new SplitPane();
        sp.getItems().addAll(board, controls.getRoot());

        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
