package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class UserMenuView extends VBox {

    UserMenuView() {
        Button toManagePatientsButton = new Button("PATIENTS");
        toManagePatientsButton.setPrefWidth(150);

        Button toManageAppointmentsButton = new Button("APPOINTMENTS");
        toManageAppointmentsButton.setPrefWidth(150);

        getChildren().addAll(toManagePatientsButton, toManageAppointmentsButton);
        setAlignment(Pos.CENTER);
    }
}
