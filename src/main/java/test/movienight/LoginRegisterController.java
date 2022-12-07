package test.movienight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static test.movienight.HomePageUserController.movieGrid;

public class LoginRegisterController {
    @FXML
    private Hyperlink switchToLogin;
    @FXML
    private Hyperlink switchToRegister;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane registerForm;
    @FXML
    private AnchorPane loginForm;

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == switchToLogin || event.getSource() == registerButton) {
            registerForm.setVisible(false);
            loginForm.setVisible(true);
        } else if (event.getSource() == switchToRegister) {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        }
    }

    private double x = 0;
    private double y = 0;

    public void login() throws IOException {
        loginButton.getScene().getWindow().hide();
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePageUser.fxml")));

        AnchorPane homePage = (AnchorPane) root.getChildren().get(1);
        ScrollPane scrollPane = (ScrollPane) homePage.getChildren().get(1);
        scrollPane.setContent(movieGrid());

        homePage.setVisible(true);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 1400, 700);

        //  move the screen
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        stage.setScene(scene);
        stage.show();
    }

}