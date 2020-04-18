package ui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;

class TableGenerator<T> {

    TableView<T> generateTable(Object o) {
        TableView<T> table = new TableView<>();
        Class oClass = o.getClass();
        for (Field f : oClass.getDeclaredFields()) {
            TableColumn<T, ?> column = new TableColumn<>(f.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            table.getColumns().add(column);
        }
        return table;
    }
}
