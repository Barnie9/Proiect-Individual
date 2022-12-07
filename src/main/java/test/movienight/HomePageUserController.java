package test.movienight;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageUserController {

    private static List<Movie> movies = getData();
    private static List<Movie> watchlist = new ArrayList<>();
    private static List<Movie> favorites = new ArrayList<>();
    private static List<Movie> watched = new ArrayList<>();

    @FXML
    private Button homeButton;
    @FXML
    private Button recommendationsButton;
    @FXML
    private Button watchlistButton;
    @FXML
    private Button favoritesButton;
    @FXML
    private Button watchedButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane Home;
    @FXML
    private AnchorPane Recommendations;
    @FXML
    private AnchorPane Watchlist;
    @FXML
    private AnchorPane Favorites;
    @FXML
    private AnchorPane Watched;
    @FXML
    private AnchorPane MyAccount;
    @FXML
    private AnchorPane MoviePage;
    @FXML
    private Label movieTitle;
    @FXML
    private ImageView movieImage;
    @FXML
    private TextField movieGenres;
    @FXML
    private TextField movieLength;
    @FXML
    private TextArea movieDescription;

    public void goToPage(ActionEvent event) throws IOException {
        if(event.getSource() == homeButton) {
            ScrollPane scrollPane = (ScrollPane) Home.getChildren().get(1);
            scrollPane.setContent(movieGrid());

            Home.setVisible(true);
            Recommendations.setVisible(false);
            Watchlist.setVisible(false);
            Favorites.setVisible(false);
            Watched.setVisible(false);
            MyAccount.setVisible(false);
            MoviePage.setVisible(false);
        } else if(event.getSource() == recommendationsButton) {
            Home.setVisible(false);
            Recommendations.setVisible(true);
            Watchlist.setVisible(false);
            Favorites.setVisible(false);
            Watched.setVisible(false);
            MyAccount.setVisible(false);
            MoviePage.setVisible(false);
        } else if(event.getSource() == watchlistButton) {
            ScrollPane scrollPane = (ScrollPane) Watchlist.getChildren().get(0);
            scrollPane.setContent(watchlistGrid());

            Home.setVisible(false);
            Recommendations.setVisible(false);
            Watchlist.setVisible(true);
            Favorites.setVisible(false);
            Watched.setVisible(false);
            MyAccount.setVisible(false);
            MoviePage.setVisible(false);
        } else if(event.getSource() == favoritesButton) {
            ScrollPane scrollPane = (ScrollPane) Favorites.getChildren().get(0);
            scrollPane.setContent(favoritesGrid());

            Home.setVisible(false);
            Recommendations.setVisible(false);
            Watchlist.setVisible(false);
            Favorites.setVisible(true);
            Watched.setVisible(false);
            MyAccount.setVisible(false);
            MoviePage.setVisible(false);
        } else if(event.getSource() == watchedButton) {
            ScrollPane scrollPane = (ScrollPane) Watched.getChildren().get(0);
            scrollPane.setContent(watchedGrid());

            Home.setVisible(false);
            Recommendations.setVisible(false);
            Watchlist.setVisible(false);
            Favorites.setVisible(false);
            Watched.setVisible(true);
            MyAccount.setVisible(false);
            MoviePage.setVisible(false);
        } else if(event.getSource() == myAccountButton) {
            Home.setVisible(false);
            Recommendations.setVisible(false);
            Watchlist.setVisible(false);
            Favorites.setVisible(false);
            Watched.setVisible(false);
            MyAccount.setVisible(true);
            MoviePage.setVisible(false);
        }
    }

    private double x = 0;
    private double y = 0;

    public void logout() throws IOException {
        logoutButton.getScene().getWindow().hide();
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));

        watchlist = new ArrayList<>();
        favorites = new ArrayList<>();
        watched = new ArrayList<>();

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

    private static List<Movie> getData() {
        List<Movie> movieList = new ArrayList<>();

        String[] genuri = new String[2];
        genuri[0] = "Actiune";
        genuri[1] = "Aventura";

        Movie m1 = new Movie(1, "images/Avatar2.jpg", "Avatar 2", " ", 120, genuri);
        Movie m2 = new Movie(2, "images/Jumanji1.jpg", "Jumanji 1", " ", 100, genuri);
        Movie m3 = new Movie(3, "images/Avatar2.jpg", "Avatar 2", " ", 120, genuri);
        Movie m4 = new Movie(4, "images/Jumanji1.jpg", "Jumanji 1", " ", 100, genuri);
        Movie m5 = new Movie(5, "images/Avatar2.jpg", "Avatar 2", " ", 120, genuri);
        Movie m6 = new Movie(6, "images/Jumanji1.jpg", "Jumanji 1", " ", 100, genuri);
        Movie m7 = new Movie(7, "images/Avatar2.jpg", "Avatar 2", " ", 120, genuri);
        Movie m8 = new Movie(8, "images/Jumanji1.jpg", "Jumanji 1", " ", 100, genuri);

        movieList.add(m1);
        movieList.add(m2);
        movieList.add(m3);
        movieList.add(m4);
        movieList.add(m5);
        movieList.add(m6);
        movieList.add(m7);
        movieList.add(m8);

        return movieList;
    }

    public static GridPane movieGrid() {
        GridPane gridPane = new GridPane();

        int j;
        for(int i = 0; i < movies.size(); i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(250);
            vBox.setPrefHeight(510);
            vBox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image("file:" + movies.get(i).getImgSrc()));
            imageView.setFitHeight(350);
            imageView.setFitWidth(250);
            imageView.setId("image" + movies.get(i).getId());

            Label label = new Label(movies.get(i).getNume());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font(20));

            Button addToWatchlist = new Button();
            addToWatchlist.setText("Add to watchlist");
            addToWatchlist.setPrefWidth(250);
            addToWatchlist.setPrefHeight(40);
            addToWatchlist.setAlignment(Pos.CENTER);
            addToWatchlist.setFont(new Font(16));
            addToWatchlist.setId("watchlist" + movies.get(i).getId());
            addToWatchlist.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : movies) {
                        if(str.equals("watchlist" + m.getId()) && !watchlist.contains(m)) {
                            watchlist.add(m);
                            break;
                        }
                    }
                }
            });

            Button addToFavorites = new Button();
            addToFavorites.setText("Favorite");
            addToFavorites.setPrefWidth(250);
            addToFavorites.setPrefHeight(40);
            addToFavorites.setAlignment(Pos.CENTER);
            addToFavorites.setFont(new Font(16));
            addToFavorites.setId("favorites" + movies.get(i).getId());
            addToFavorites.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : movies) {
                        if(str.equals("favorites" + m.getId()) && !favorites.contains(m)) {
                            favorites.add(m);
                            break;
                        }
                    }
                }
            });

            Button addToWatched = new Button();
            addToWatched.setText("Watched");
            addToWatched.setPrefWidth(250);
            addToWatched.setPrefHeight(40);
            addToWatched.setAlignment(Pos.CENTER);
            addToWatched.setFont(new Font(16));
            addToWatched.setId("watched" + movies.get(i).getId());
            addToWatched.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : movies) {
                        if(str.equals("watched" + m.getId()) && !watched.contains(m)) {
                            watched.add(m);
                            break;
                        }
                    }
                }
            });

            vBox.getChildren().addAll(imageView, label, addToWatchlist, addToFavorites, addToWatched);

            j = i / 4;

            //  add them to the GridPane
            gridPane.add(vBox, i % 4, j); //  (child, columnIndex, rowIndex)

            // margins are up to your preference
            GridPane.setMargin(vBox, new Insets(20, 0, 0, 20));
        }
        gridPane.setAlignment(Pos.CENTER);

//        System.out.println(watchlist);
//        System.out.println(favorites);
//        System.out.println(watched);

        return gridPane;
    }

    public static GridPane watchlistGrid() {
        GridPane gridPane = new GridPane();

        int j;
        for(int i = 0; i < watchlist.size(); i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(250);
            vBox.setPrefHeight(430);
            vBox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image("file:" + watchlist.get(i).getImgSrc()));
            imageView.setFitHeight(350);
            imageView.setFitWidth(250);

            Label label = new Label(watchlist.get(i).getNume());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font(20));

            Button removeFromWatchlist = new Button();
            removeFromWatchlist.setText("Remove from watchlist");
            removeFromWatchlist.setPrefWidth(250);
            removeFromWatchlist.setPrefHeight(40);
            removeFromWatchlist.setAlignment(Pos.CENTER);
            removeFromWatchlist.setFont(new Font(16));
            removeFromWatchlist.setId("removeWatchlist" + watchlist.get(i).getId());
            removeFromWatchlist.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : watchlist) {
                        if(str.equals("removeWatchlist" + m.getId())) {
                            watchlist.remove(m);
                            break;
                        }
                    }
                }
            });

            vBox.getChildren().addAll(imageView, label, removeFromWatchlist);

            j = i / 4;

            //  add them to the GridPane
            gridPane.add(vBox, i % 4, j); //  (child, columnIndex, rowIndex)

            // margins are up to your preference
            GridPane.setMargin(vBox, new Insets(20, 0, 0, 20));
        }
        gridPane.setAlignment(Pos.CENTER);

//        System.out.println(watchlist);
//        System.out.println(favorites);
//        System.out.println(watched);

        return gridPane;
    }

    public static GridPane favoritesGrid() {
        GridPane gridPane = new GridPane();

        int j;
        for(int i = 0; i < favorites.size(); i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(250);
            vBox.setPrefHeight(430);
            vBox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image("file:" + favorites.get(i).getImgSrc()));
            imageView.setFitHeight(350);
            imageView.setFitWidth(250);

            Label label = new Label(favorites.get(i).getNume());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font(20));

            Button removeFromFavorites = new Button();
            removeFromFavorites.setText("Remove from favorites");
            removeFromFavorites.setPrefWidth(250);
            removeFromFavorites.setPrefHeight(40);
            removeFromFavorites.setAlignment(Pos.CENTER);
            removeFromFavorites.setFont(new Font(16));
            removeFromFavorites.setId("removeFavorites" + favorites.get(i).getId());
            removeFromFavorites.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : favorites) {
                        if(str.equals("removeFavorites" + m.getId())) {
                            favorites.remove(m);
                            break;
                        }
                    }
                }
            });

            vBox.getChildren().addAll(imageView, label, removeFromFavorites);

            j = i / 4;

            //  add them to the GridPane
            gridPane.add(vBox, i % 4, j); //  (child, columnIndex, rowIndex)

            // margins are up to your preference
            GridPane.setMargin(vBox, new Insets(20, 0, 0, 20));
        }
        gridPane.setAlignment(Pos.CENTER);

//        System.out.println(watchlist);
//        System.out.println(favorites);
//        System.out.println(watched);

        return gridPane;
    }

    public static GridPane watchedGrid() {
        GridPane gridPane = new GridPane();

        int j;
        for(int i = 0; i < watched.size(); i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(250);
            vBox.setPrefHeight(470);
            vBox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image("file:" + watched.get(i).getImgSrc()));
            imageView.setFitHeight(350);
            imageView.setFitWidth(250);

            Label label = new Label(watched.get(i).getNume());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font(20));

            Button removeFromWatched = new Button();
            removeFromWatched.setText("Remove from watched");
            removeFromWatched.setPrefWidth(250);
            removeFromWatched.setPrefHeight(40);
            removeFromWatched.setAlignment(Pos.CENTER);
            removeFromWatched.setFont(new Font(16));
            removeFromWatched.setId("removeWatched" + watched.get(i).getId());
            removeFromWatched.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String str = actionEvent.getSource().toString();
                    str = str.substring(str.indexOf('=') + 1, str.indexOf(','));
                    for(Movie m : watched) {
                        if(str.equals("removeWatched" + m.getId())) {
                            watched.remove(m);
                            break;
                        }
                    }
                }
            });

            Button addRating = new Button();
            addRating.setText("Add rating");
            addRating.setPrefWidth(250);
            addRating.setPrefHeight(40);
            addRating.setAlignment(Pos.CENTER);
            addRating.setFont(new Font(16));

            vBox.getChildren().addAll(imageView, label, removeFromWatched, addRating);

            j = i / 4;

            //  add them to the GridPane
            gridPane.add(vBox, i % 4, j); //  (child, columnIndex, rowIndex)

            // margins are up to your preference
            GridPane.setMargin(vBox, new Insets(20, 0, 0, 20));
        }
        gridPane.setAlignment(Pos.CENTER);

//        System.out.println(watchlist);
//        System.out.println(favorites);
//        System.out.println(watched);

        return gridPane;
    }

}
