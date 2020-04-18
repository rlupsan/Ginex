package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminMenuView extends VBox {

    AdminMenuView() {
        setSpacing(8);
        Button toManageEmployeeButton = new Button("Manage Employees");
        toManageEmployeeButton.setPrefWidth(150);

        getChildren().addAll(toManageEmployeeButton);
        setAlignment(Pos.CENTER);
    }
}