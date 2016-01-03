package pl.maxmati.po.automaton.gui;

/**
 * Created by maxmati on 12/15/15.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import pl.maxmati.po.automaton.automaton.GameOfLife;

public class Main extends Application {

    public static final String TITLE = "Automaton";
    CommandQueue queue = new CommandQueue();
    BoardAdapter adapter = new BoardAdapter(queue, 10, 5, new GameOfLife(5, 10));
    Ticker ticker = new Ticker(adapter);
    Board board = new Board(adapter);
    Controls controls = new Controls(adapter, ticker);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle(TITLE);

        SplitPane sp = new SplitPane();
        sp.getItems().addAll(board, controls.getRoot());

        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
