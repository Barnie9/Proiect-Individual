package controllers;

import classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import classes.Data;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
public class LoginRegisterController {
    //  Index Page
    @FXML
    private AnchorPane index;
    @FXML
    private Button goToLoginFormButton;
    @FXML
    private Button goToRegisterFormButton;
    @FXML
    private Button continueWithoutAccountButton;

    //  Register Form
    @FXML
    private AnchorPane registerForm;
    @FXML
    private TextField usernameRegister;
    @FXML
    private TextField emailRegister;
    @FXML
    private PasswordField passwordRegister;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink switchToLogin;

    //  Login Form
    @FXML
    private AnchorPane loginForm;
    @FXML
    private TextField usernameOrEmailLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink switchToRegister;

    //  Back Button
    @FXML
    private Button backToIndexButton;

    @FXML
    public void switchForm(ActionEvent event) {
        if(event.getSource() == goToLoginFormButton || event.getSource() == switchToLogin) {
            index.setVisible(false);
            backToIndexButton.setVisible(true);
            registerForm.setVisible(false);
            loginForm.setVisible(true);
        } else if(event.getSource() == goToRegisterFormButton || event.getSource() == switchToRegister) {
            index.setVisible(false);
            backToIndexButton.setVisible(true);
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        } else if(event.getSource() == backToIndexButton) {
            index.setVisible(true);
            backToIndexButton.setVisible(false);
            registerForm.setVisible(false);
            loginForm.setVisible(false);
        }
    }

    public void loginButtonPressed() throws IOException, SQLException {
        if(usernameOrEmailLogin.getText().isEmpty() || passwordLogin.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields!");
        } else {
            String query = "select * from users where (username = ? or email = ?) and password = ?";
            PreparedStatement preparedStatement = Data.connection.prepareStatement(query);
            preparedStatement.setString(1, usernameOrEmailLogin.getText());
            preparedStatement.setString(2, usernameOrEmailLogin.getText());
            preparedStatement.setString(3, passwordLogin.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                loginButton.getScene().getWindow().hide();

                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                boolean isAdmin = resultSet.getBoolean(5);

                Data.connectedUser = new User(id, username, email, password, isAdmin);

                AnchorPane root;
                FXMLLoader loader;
                if(Data.connectedUser.isAdmin()) {
                    loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
                    root = loader.load();

                    AdminPageController adminPageController = loader.getController();
                    adminPageController.userAccountsButtonPressed();
                } else {
                    loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
                    root = loader.load();

                    Data.getComingSoonMoviesFromDatabase();
                    Data.getTopRatedMoviesFromDatabase();
                    Data.getFanFavoritesMoviesFromDatabase();
                    Data.getTopPicksMoviesFromDatabase();
                    Data.getTopRatedByUsersMoviesFromDatabase();

                    UserPageController userPageController = loader.getController();
                    userPageController.setUsernameLabel(Data.connectedUser.getUsername());
                    userPageController.homeButtonPressed();

                    Data.getWatchlistFromDatabase();
                    Data.getFavoritesFromDatabase();
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Wrong username, email or password!");
            }
        }
    }

    public void registerButtonPressed() throws SQLException {
        if(usernameRegister.getText().isEmpty() || emailRegister.getText().isEmpty() || passwordRegister.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields!");
        } else {
            String query;
            PreparedStatement ps;
            ResultSet resultSet;

            query = "select * from users where email = ?";
            ps = Data.connection.prepareStatement(query);
            ps.setString(1, emailRegister.getText());
            resultSet = ps.executeQuery();
            if(resultSet.next()) {
                showAlert(Alert.AlertType.ERROR, "An account with this email address has been found!");
            } else {
                query = "insert into users (username, email, password) values (?, ?, ?)";
                ps = Data.connection.prepareStatement(query);
                ps.setString(1, usernameRegister.getText());
                ps.setString(2, emailRegister.getText());
                ps.setString(3, passwordRegister.getText());
                ps.execute();

                showAlert(Alert.AlertType.INFORMATION, "Account created successfully!");

                index.setVisible(true);
                backToIndexButton.setVisible(false);
                registerForm.setVisible(false);
                loginForm.setVisible(false);
            }
        }
    }

    public void continueWithoutAccountButtonPressed() throws IOException {
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NoUserPage.fxml"));
        AnchorPane root = loader.load();

        Data.getComingSoonMoviesFromDatabase();
        Data.getTopRatedMoviesFromDatabase();
        Data.getFanFavoritesMoviesFromDatabase();
        Data.getTopPicksMoviesFromDatabase();
        Data.getTopRatedByUsersMoviesFromDatabase();

        NoUserPageController noUserPageController = loader.getController();
        noUserPageController.homeButtonPressed();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showAlert(Alert.AlertType alertType, String contentText) {
        Alert alert = new Alert(alertType);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/Alert.css")).toExternalForm());
        dialogPane.getStyleClass().add("customDialog");

        alert.showAndWait();
    }
}
