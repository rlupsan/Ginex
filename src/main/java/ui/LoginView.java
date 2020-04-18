package ui;

import bll.EmployeeBLL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class LoginView extends Application {
    private TextField usernameInput;
    private PasswordField passwordInput;
    private EmployeeBLL businessLogic;
    private Scene sceneLogin, sceneUserMenu, sceneAdminMenu;

    @Override
    public void start(Stage primaryStage) {
        businessLogic = new EmployeeBLL();
        primaryStage.setTitle("GINOSAN");

        GridPane gridPaneLogin = new GridPane();
        gridPaneLogin.setPadding(new Insets(30, 30, 30, 30));
        gridPaneLogin.setVgap(8);
        gridPaneLogin.setHgap(10);

        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 24));
        GridPane.setConstraints(welcomeLabel, 2, 0);

        Label usernameLabel = new Label("Username: ");
        usernameLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        GridPane.setConstraints(usernameLabel, 1, 2);

        usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 2, 2);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));

        passwordInput = new PasswordField();
        GridPane.setConstraints(passwordLabel, 1, 3);

        GridPane.setConstraints(passwordInput, 2, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 2, 5);
        loginButton.setMaxWidth(Double.MAX_VALUE);

        loginButton.setOnAction(this::performLogin);

        gridPaneLogin.getChildren().addAll(welcomeLabel, usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        gridPaneLogin.setAlignment(Pos.CENTER);

        sceneLogin = new Scene(gridPaneLogin, 450, 250);
        //END OF SCENE 0 (LoginView)

        //START OF SCENE1 (USER VIEW)
        primaryStage.setTitle("GINOSAN");
        VBox vBoxUserMenu = new VBox(8);

        Button toManagePatientsButton = new Button("PATIENTS");
        toManagePatientsButton.setPrefWidth(150);

        Button toManageAppointmentsButton = new Button("APPOINTMENTS");
        toManageAppointmentsButton.setPrefWidth(150);

        vBoxUserMenu.getChildren().addAll(toManagePatientsButton, toManageAppointmentsButton);
        vBoxUserMenu.setAlignment(Pos.CENTER);

        toManagePatientsButton.setOnAction(e -> sceneUserMenu.setRoot(new PatientView()));
        toManageAppointmentsButton.setOnAction(e -> sceneUserMenu.setRoot(new AppointmentView()));

        sceneUserMenu = new Scene(vBoxUserMenu, 1100, 550);
        //END SCENE 1 (USER MENU VIEW)

        //START SCENE 2 (ADMIN MENU VIEW) - NEVERFICATA PT GINOSAN!
        primaryStage.setTitle("ADMIN");
        VBox vBoxAdminMenu = new VBox(8);
        Button toManageEmployeeButton = new Button("Manage Employees");
        toManageEmployeeButton.setPrefWidth(150);
        Button toGenerateReports = new Button("Generate Reports");
        toGenerateReports.setPrefWidth(150);
        vBoxAdminMenu.getChildren().addAll(toManageEmployeeButton, toGenerateReports);

        vBoxAdminMenu.setAlignment(Pos.CENTER);

        sceneAdminMenu = new Scene(vBoxAdminMenu, 1100, 550);
        //END SCENE 2 (ADMIN MENU VIEW)

        //BUTTON LOGIN
        loginButton.setOnAction(e -> {
            String target = businessLogic.login(usernameInput.getText(), passwordInput.getText());
            switch (target) {
                case "admin":
                    primaryStage.setTitle("ADMIN");
                    primaryStage.setScene(sceneAdminMenu);
                    //scene.setRoot(new AdminMenuView());
                    break;
                case "user":
                    primaryStage.setTitle("GINOSAN");
                    primaryStage.setScene(sceneUserMenu);
                    //scene.setRoot(new UserMenuView());
                    break;
                default:
                    showAlert();
                    usernameInput.clear();
                    passwordInput.clear();
            }
        });

        primaryStage.setTitle("Login");
        primaryStage.setScene(sceneLogin);
        primaryStage.show();


    }

    private void performLogin(ActionEvent actionEvent) {
        String target = businessLogic.login(usernameInput.getText(), passwordInput.getText());
        switch (target) {
            case "admin":
                sceneLogin.setRoot(new AdminMenuView());
                break;
            case "user":
                sceneLogin.setRoot(new UserMenuView());
                break;
            default:
                showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Login");
        alert.setHeaderText("Your credentials are invalid!");
        alert.showAndWait();
    }


}
