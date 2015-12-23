package pl.maxmati.po.automaton.gui;

/**
 * Created by maxmati on 12/15/15.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.maxmati.po.automaton.automaton.GameOfLife;

public class Main extends Application {

    public static final String TITLE = "Automaton";
    CommandQueue queue = new CommandQueue();
    BoardAdapter adapter = null;
    Board board = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        adapter = new BoardAdapter(queue, 100, 100, new GameOfLife(100, 100));
        board = new Board(adapter);

        SplitPane sp = new SplitPane();

        StackPane sp2 = new StackPane();
        sp2.getChildren().add(new Button("Test@"));

        sp.getItems().addAll(board, sp2);

        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
