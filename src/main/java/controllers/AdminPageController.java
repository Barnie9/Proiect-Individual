package controllers;

import classes.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AdminPageController {
    @FXML
    private Button backToIndexButton;

    public void backToIndexButtonPressed() throws IOException {
        backToIndexButton.getScene().getWindow().hide();
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1400, 700);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button userAccountsButton;
    @FXML
    private ScrollPane userAccountsPage;
    @FXML
    private VBox userAccountsPane;
    public void userAccountsButtonPressed() {
        try {
            if(userAccountsPane.getChildren().size() != 0) {
                userAccountsPane.getChildren().remove(0, userAccountsPane.getChildren().size());
            }

            String query = "select * from users";
            PreparedStatement ps = Data.connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(5, 10, 0, 20));
                hBox.setSpacing(20);
                hBox.setPrefWidth(1080);
                hBox.setPrefHeight(50);

                TextField id = new TextField();
                id.setAlignment(Pos.CENTER);
                id.setPrefWidth(50);
                id.setPrefHeight(40);
                id.setEditable(false);
                id.setText(resultSet.getInt(1) + "");

                TextField username = new TextField();
                username.setAlignment(Pos.CENTER);
                username.setPrefWidth(150);
                username.setPrefHeight(40);
                username.setPromptText(resultSet.getString(2));

                TextField email = new TextField();
                email.setAlignment(Pos.CENTER);
                email.setPrefWidth(200);
                email.setPrefHeight(40);
                email.setPromptText(resultSet.getString(3));

                TextField password = new TextField();
                password.setAlignment(Pos.CENTER);
                password.setPrefWidth(150);
                password.setPrefHeight(40);
                password.setPromptText(resultSet.getString(4));

                Button save = new Button("Save");
                save.setAlignment(Pos.CENTER);
                save.setPrefWidth(150);
                save.setPrefHeight(40);
                setOnActionSave(save, id, username, email, password);

                Button delete = new Button("Delete");
                delete.setAlignment(Pos.CENTER);
                delete.setPrefWidth(150);
                delete.setPrefHeight(40);
                setOnActionDelete(delete, id, username, email, password);

                hBox.getChildren().addAll(id, username, email, password, save, delete);

                userAccountsPane.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOnActionSave(Button button, TextField id, TextField username, TextField email, TextField password) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String query = "update users set username = ?, email = ?, password = ? where id_user = ?";
                    PreparedStatement ps = Data.connection.prepareStatement(query);
                    if(username.getText().isEmpty()) {
                        ps.setString(1, username.getPromptText());
                    } else {
                        ps.setString(1, username.getText());
                    }
                    if(email.getText().isEmpty()) {
                        ps.setString(2, email.getPromptText());
                    } else {
                        ps.setString(2, email.getText());
                    }
                    if(password.getText().isEmpty()) {
                        ps.setString(3, password.getPromptText());
                    } else {
                        ps.setString(3, password.getText());
                    }
                    ps.setInt(4, Integer.parseInt(id.getText()));
                    ps.execute();

                    userAccountsButtonPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setOnActionDelete(Button button, TextField id, TextField username, TextField email, TextField password) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String query1 = "delete from users where id_user = ?";
                    String query2 = "delete from ratings where id_user = ?";
                    String query3 = "delete from watchlist where id_user = ?";
                    String query4 = "delete from favlist where id_user = ?";

                    PreparedStatement ps1 = Data.connection.prepareStatement(query1);
                    PreparedStatement ps2 = Data.connection.prepareStatement(query2);
                    PreparedStatement ps3 = Data.connection.prepareStatement(query3);
                    PreparedStatement ps4 = Data.connection.prepareStatement(query4);

                    ps1.setInt(1, Integer.parseInt(id.getText()));
                    ps2.setInt(1, Integer.parseInt(id.getText()));
                    ps3.setInt(1, Integer.parseInt(id.getText()));
                    ps4.setInt(1, Integer.parseInt(id.getText()));

                    ps1.execute();
                    ps2.execute();
                    ps3.execute();
                    ps4.execute();

                    userAccountsButtonPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
