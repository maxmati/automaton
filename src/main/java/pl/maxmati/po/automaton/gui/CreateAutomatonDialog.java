package pl.maxmati.po.automaton.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pl.maxmati.po.automaton.automaton.Automaton1Dim;
import pl.maxmati.po.automaton.automaton.GameOfLife;
import pl.maxmati.po.automaton.automaton.factories.AutomatonFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by maxmati on 12/30/15.
 */
public class CreateAutomatonDialog {
    public static final String AUTOMATON_TYPE_KEY = "Type";
    private static final String TYPE_SELECTION_LABEL = "Type:";
    private static final String CREATE_BUTTON_LABEL = "Create";
    private static final String CRATE_AUTOMATON_DIALOG_TITLE = "Create automaton";

    private final Dialog<Map<String, Object>> dialog;
    private final HashMap<String, Control> paramsFields = new HashMap<>();
    private final List<Node> gridElements = new LinkedList<>();

    public CreateAutomatonDialog() {
        dialog = new Dialog<>();

        dialog.setTitle(CRATE_AUTOMATON_DIALOG_TITLE);

        ButtonType loginButtonType = new ButtonType(CREATE_BUTTON_LABEL, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll(AutomatonFactory.getAvailableAutomatons());

        GridPane grid = new GridPane();
        grid.setHgap(3.);
        grid.setVgap(3.);
        grid.setAlignment(Pos.TOP_CENTER);

        type.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            Map<String, Object> params = AutomatonFactory.getParams(newValue);
            clearGrid(grid);
            int i = 1;
            for (Map.Entry<String, Object> entry: params.entrySet()){
                addToGrid(grid, new Label(entry.getKey()), 0, i);
                Control c = createControlForParam(entry);
                paramsFields.put(entry.getKey(), c);
                grid.add(c, 1, i);
                ++i;
            }
        });

        grid.add(new Label(TYPE_SELECTION_LABEL), 0, 0);
        grid.add(type, 1, 0);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonType -> {
            if(buttonType == loginButtonType){
                Map<String, Object> result = getValuesFromParamsFields(paramsFields);

                result.put(AUTOMATON_TYPE_KEY, type.getValue());
                return result;
            }
            return null;
        });
    }


    private void clearGrid(GridPane grid) {
        grid.getChildren().removeAll(gridElements);
        gridElements.clear();
    }

    private void addToGrid(GridPane grid, Node control, int col, int row) {
        gridElements.add(control);
        grid.add(control, col, row);
    }

    private Control createControlForParam(Map.Entry<String, Object> entry) {
        if(entry.getValue() instanceof Integer) {
            Spinner spinner = new Spinner(1,99, (Integer) entry.getValue());
            spinner.setEditable(true);
            return spinner;
        } else if(entry.getValue() instanceof Boolean){
            CheckBox checkbox = new CheckBox();
            checkbox.setSelected((Boolean) entry.getValue());
            return checkbox;
        } else if(entry.getValue() instanceof GameOfLife.Rule) {
            TextField textField = new TextField();
            textField.setText(entry.getValue().toString());
            return textField;
        } else if(entry.getValue() instanceof Automaton1Dim.Rule) {
            Spinner spinner = new Spinner(0,255, Integer.valueOf(entry.getValue().toString()));
            spinner.setEditable(true);
            return spinner;
        }
        return null;
    }

    private Map<String, Object> getValuesFromParamsFields(HashMap<String, Control> paramsFields) {
        return paramsFields.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        this::getControlValue
                ));
    }

    private Object getControlValue(Map.Entry<String, Control> e) {
        Control control = e.getValue();
        if(control instanceof Spinner) {
            Spinner spinner = (Spinner) control;
            return spinner.getValue();
        } else if(control instanceof CheckBox){
            return ((CheckBox) control).selectedProperty().getValue();
        } else if(control instanceof TextField){
            return ((TextField) control).getText();
        }
        return null;
    }

    public Optional<Map<String, Object>> show() {
        return dialog.showAndWait();
    }
}
