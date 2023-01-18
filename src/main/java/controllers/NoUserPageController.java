package controllers;

import classes.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class NoUserPageController {
    //  Menu
    @FXML
    private Button homeButton;
    @FXML
    private Button backToIndexButton;

    //  Home Page
    @FXML
    private ScrollPane homePage;

    //  Coming Soon Box
    @FXML
    private Button goToComingSoonButton;
    @FXML
    private HBox comingSoonBox;
    @FXML
    private Button comingSoonPrevButton;
    @FXML
    private Button comingSoonNextButton;
    private int comingSoonIndex;

    //  Top Rated Box
    @FXML
    private Button goToTopRatedButton;
    @FXML
    private HBox topRatedBox;
    @FXML
    private Button topRatedPrevButton;
    @FXML
    private Button topRatedNextButton;
    private int topRatedIndex;

    //  Fan Favorites Box
    @FXML
    private Button goToFanFavoritesButton;
    @FXML
    private HBox fanFavoritesBox;
    @FXML
    private Button fanFavoritesPrevButton;
    @FXML
    private Button fanFavoritesNextButton;
    private int fanFavoritesIndex;

    //  Top Picks Box
    @FXML
    private Button goToTopPicksButton;
    @FXML
    private HBox topPicksBox;
    @FXML
    private Button topPicksPrevButton;
    @FXML
    private Button topPicksNextButton;
    private int topPicksIndex;

    //  Top Rated By Users Box
    @FXML
    private Button goToTopRatedByUsersButton;
    @FXML
    private HBox topRatedByUsersBox;
    @FXML
    private Button topRatedByUsersPrevButton;
    @FXML
    private Button topRatedByUsersNextButton;
    private int topRatedByUsersIndex;

    //  All Movies Page
    @FXML
    private AnchorPane allMoviesPage;
    @FXML
    private ScrollPane moviesPane;
    @FXML
    private Button comingSoonTabButton;
    @FXML
    private Button topRatedTabButton;
    @FXML
    private Button fanFavoritesTabButton;
    @FXML
    private Button topPicksTabButton;
    @FXML
    private Button topRatedByUsersTabButton;

    //  Movie Page
    @FXML
    private AnchorPane moviePage;
    @FXML
    private Label movieTitle;
    @FXML
    private ImageView movieBanner;
    @FXML
    private Label ratingValue;
    @FXML
    private Label userRatingStar;
    @FXML
    private Label userRatingValue;
    @FXML
    private Label yourRatingStar;
    @FXML
    private Label noUserRatingLabel;
    @FXML
    private Label yourRatingValue;
    @FXML
    private Button rateButton;
    @FXML
    private Label movieGenres;
    @FXML
    private Label movieReleaseDate;
    @FXML
    private Label movieRunningTime;
    @FXML
    private TextArea movieDescriptionArea;
    @FXML
    private VBox movieReviewsBox;



    public void homeButtonPressed() {
        comingSoonIndex = 1;
        topRatedIndex = 1;
        fanFavoritesIndex = 1;
        topPicksIndex = 1;
        topRatedByUsersIndex = 1;
        generateComingSoonBox();
        generateTopRatedBox();
        generateFanFavoritesBox();
        generateTopPicksBox();
        generateTopRatedByUsersBox();
        comingSoonPrevButton.setVisible(false);
        topRatedPrevButton.setVisible(false);
        fanFavoritesPrevButton.setVisible(false);
        topPicksPrevButton.setVisible(false);
        topRatedByUsersPrevButton.setVisible(false);
        comingSoonNextButton.setVisible(true);
        topRatedNextButton.setVisible(true);
        fanFavoritesNextButton.setVisible(true);
        topPicksNextButton.setVisible(true);
        topRatedByUsersNextButton.setVisible(true);

        moviePage.setVisible(false);
        allMoviesPage.setVisible(false);
        homePage.setVisible(true);
    }

    public void backToIndexButtonPressed() throws IOException {
        backToIndexButton.getScene().getWindow().hide();
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1400, 700);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void anyGoToButtonPressed(ActionEvent event) {
        if(event.getSource() == goToComingSoonButton) {
            generateComingSoonGrid();

            homePage.setVisible(false);
            allMoviesPage.setVisible(true);
        } else if(event.getSource() == goToTopRatedButton) {
            generateTopRatedGrid();

            homePage.setVisible(false);
            allMoviesPage.setVisible(true);
        } else if(event.getSource() == goToFanFavoritesButton) {
            generateFanFavoritesGrid();

            homePage.setVisible(false);
            allMoviesPage.setVisible(true);
        } else if(event.getSource() == goToTopPicksButton) {
            generateTopPicksGrid();

            homePage.setVisible(false);
            allMoviesPage.setVisible(true);
        } else if(event.getSource() == goToTopRatedByUsersButton) {
            generateTopRatedByUsersGrid();

            homePage.setVisible(false);
            allMoviesPage.setVisible(true);
        }
    }

    @FXML
    public void anyTabButtonPressed(ActionEvent event) {
        if(event.getSource() == comingSoonTabButton) {
            generateComingSoonGrid();
        } else if(event.getSource() == topRatedTabButton) {
            generateTopRatedGrid();
        } else if(event.getSource() == fanFavoritesTabButton) {
            generateFanFavoritesGrid();
        } else if(event.getSource() == topPicksTabButton) {
            generateTopPicksGrid();
        } else if(event.getSource() == topRatedByUsersTabButton) {
            generateTopRatedByUsersGrid();
        }
    }

    @FXML
    public void anyPrevOrNextButtonPressed(ActionEvent event) {
        if(event.getSource() == comingSoonPrevButton) {
            comingSoonIndex--;
            generateComingSoonBox();
            comingSoonPrevButton.setVisible(comingSoonIndex != 1);
            comingSoonNextButton.setVisible(comingSoonIndex != 6);
        } else if(event.getSource() == comingSoonNextButton) {
            comingSoonIndex++;
            generateComingSoonBox();
            comingSoonPrevButton.setVisible(comingSoonIndex != 1);
            comingSoonNextButton.setVisible(comingSoonIndex != 6);
        } else if(event.getSource() == topRatedPrevButton) {
            topRatedIndex--;
            generateTopRatedBox();
            topRatedPrevButton.setVisible(topRatedIndex != 1);
            topRatedNextButton.setVisible(topRatedIndex != 6);
        } else if(event.getSource() == topRatedNextButton) {
            topRatedIndex++;
            generateTopRatedBox();
            topRatedPrevButton.setVisible(topRatedIndex != 1);
            topRatedNextButton.setVisible(topRatedIndex != 6);
        }  else if(event.getSource() == fanFavoritesPrevButton) {
            fanFavoritesIndex--;
            generateFanFavoritesBox();
            fanFavoritesPrevButton.setVisible(fanFavoritesIndex != 1);
            fanFavoritesNextButton.setVisible(fanFavoritesIndex != 6);
        } else if(event.getSource() == fanFavoritesNextButton) {
            fanFavoritesIndex++;
            generateFanFavoritesBox();
            fanFavoritesPrevButton.setVisible(fanFavoritesIndex != 1);
            fanFavoritesNextButton.setVisible(fanFavoritesIndex != 6);
        }  else if(event.getSource() == topPicksPrevButton) {
            topPicksIndex--;
            generateTopPicksBox();
            topPicksPrevButton.setVisible(topPicksIndex != 1);
            topPicksNextButton.setVisible(topPicksIndex != 6);
        } else if(event.getSource() == topPicksNextButton) {
            topPicksIndex++;
            generateTopPicksBox();
            topPicksPrevButton.setVisible(topPicksIndex != 1);
            topPicksNextButton.setVisible(topPicksIndex != 6);
        }  else if(event.getSource() == topRatedByUsersPrevButton) {
            topRatedByUsersIndex--;
            generateTopRatedByUsersBox();
            topRatedByUsersPrevButton.setVisible(topRatedByUsersIndex != 1);
            topRatedByUsersNextButton.setVisible(topRatedByUsersIndex != 6);
        } else if(event.getSource() == topRatedByUsersNextButton) {
            topRatedByUsersIndex++;
            generateTopRatedByUsersBox();
            topRatedByUsersPrevButton.setVisible(topRatedByUsersIndex != 1);
            topRatedByUsersNextButton.setVisible(topRatedByUsersIndex != 6);
        }
    }

    public void generateMoviePage() {
        try {
            if(movieReviewsBox.getChildren().size() != 0) {
                movieReviewsBox.getChildren().remove(0, movieReviewsBox.getChildren().size());
            }

            String query;
            PreparedStatement ps;
            ResultSet resultSet;

            movieTitle.setText(Data.currentMovie.getTitle());
            movieBanner.setImage(new Image("file:images/" + Data.currentMovie.getImageSrc()));
            String rating = ratingValue.getText();
            ratingValue.setText(Data.currentMovie.getRating() + rating.substring(rating.indexOf('/')));

            String userRating = userRatingValue.getText();
            query = "select movies.id_movie, round(avg(ratings.rating), 1) from movies, ratings where movies.id_movie = ratings.id_movie and movies.id_movie = ? group by movies.id_movie";
            ps = Data.connection.prepareStatement(query);
            ps.setInt(1, Data.currentMovie.getId());
            resultSet = ps.executeQuery();
            if(resultSet.next()) {
                userRatingValue.setText(resultSet.getDouble(2) + userRating.substring(userRating.indexOf('/')));
                noUserRatingLabel.setVisible(false);
                userRatingStar.setVisible(true);
                userRatingValue.setVisible(true);
            } else {
                noUserRatingLabel.setVisible(true);
                userRatingStar.setVisible(false);
                userRatingValue.setVisible(false);
            }

            String genres = "";
            for (String genre : Data.currentMovie.getGenres()) {
                genres += genre + ", ";
            }
            genres = genres.substring(0, genres.lastIndexOf(','));
            movieGenres.setText(genres);
            movieReleaseDate.setText(Data.currentMovie.getReleaseDate() + "");
            String runningTime = (Data.currentMovie.getRunningTime() / 60) + "h " + (Data.currentMovie.getRunningTime() % 60) + "m";
            movieRunningTime.setText(runningTime);
            movieDescriptionArea.setText(Data.currentMovie.getDescription());

            //  TODO Add Rating Details
            query = "select users.username, ratings.rating, ratings.comment from users, ratings, movies where users.id_user = ratings.id_user and movies.id_movie = ratings.id_movie and ratings.id_movie = ?";
            ps = Data.connection.prepareStatement(query);
            ps.setInt(1, Data.currentMovie.getId());
            resultSet = ps.executeQuery();
            while(resultSet.next()) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefWidth(1000);
                anchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                anchorPane.setMinWidth(Region.USE_PREF_SIZE);
                anchorPane.setMinHeight(Region.USE_PREF_SIZE);
                anchorPane.setMaxWidth(Region.USE_PREF_SIZE);
                anchorPane.setMaxHeight(Region.USE_PREF_SIZE);

                HBox hBox = new HBox();
                hBox.setPadding(new Insets(0, 0, 0, 10));
                hBox.setSpacing(30);
                hBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
                hBox.setPrefHeight(40);
                hBox.setMinWidth(Region.USE_PREF_SIZE);
                hBox.setMinHeight(Region.USE_PREF_SIZE);
                hBox.setMaxWidth(Region.USE_PREF_SIZE);
                hBox.setMaxHeight(Region.USE_PREF_SIZE);

                Label username = new Label(resultSet.getString(1));
                username.setFont(new Font(24));
                username.setPadding(new Insets(0, 20, 0, 20));
                username.setPrefWidth(Region.USE_COMPUTED_SIZE);
                username.setPrefHeight(40);
                username.setMinWidth(Region.USE_PREF_SIZE);
                username.setMinHeight(Region.USE_PREF_SIZE);
                username.setMaxWidth(Region.USE_PREF_SIZE);
                username.setMaxHeight(Region.USE_PREF_SIZE);

                Label reviewRating = new Label(resultSet.getDouble(2) + "/10");
                reviewRating.setFont(new Font(24));
                reviewRating.setPadding(new Insets(0, 20, 0, 20));
                reviewRating.setPrefWidth(Region.USE_COMPUTED_SIZE);
                reviewRating.setPrefHeight(40);
                reviewRating.setMinWidth(Region.USE_PREF_SIZE);
                reviewRating.setMinHeight(Region.USE_PREF_SIZE);
                reviewRating.setMaxWidth(Region.USE_PREF_SIZE);
                reviewRating.setMaxHeight(Region.USE_PREF_SIZE);

                hBox.getChildren().addAll(username, reviewRating);

                TextArea textArea = new TextArea(resultSet.getString(3));
                textArea.setFont(new Font(12));
                textArea.setWrapText(true);
                textArea.setEditable(false);
                textArea.setPrefWidth(980);
                textArea.setPrefHeight(100);
                textArea.setMinWidth(Region.USE_PREF_SIZE);
                textArea.setMinHeight(Region.USE_PREF_SIZE);
                textArea.setMaxWidth(Region.USE_PREF_SIZE);
                textArea.setMaxHeight(Region.USE_COMPUTED_SIZE);
                textArea.setLayoutX(0);
                textArea.setLayoutY(40);

                anchorPane.getChildren().addAll(hBox, textArea);

                movieReviewsBox.getChildren().add(anchorPane);
            }

            homePage.setVisible(false);
            allMoviesPage.setVisible(false);
            moviePage.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateComingSoonBox() {
        if(comingSoonBox.getChildren().size() != 0) {
            comingSoonBox.getChildren().remove(0, 5);
        }

        for (int i = 5 * (comingSoonIndex - 1); i < 5 * comingSoonIndex; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.comingSoonMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.comingSoonMovies.get(i).getId());

            Label label = new Label(Data.comingSoonMovies.get(i).getTitle());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.comingSoonMovies.get(i).getReleaseDate() + "");
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            comingSoonBox.getChildren().add(vBox);
        }
    }

    public void generateTopRatedBox() {
        if(topRatedBox.getChildren().size() != 0) {
            topRatedBox.getChildren().remove(0, 5);
        }

        for (int i = 5 * (topRatedIndex - 1); i < 5 * topRatedIndex; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topRatedMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topRatedMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topRatedMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topRatedMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topRatedMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            topRatedBox.getChildren().add(vBox);
        }
    }

    public void generateFanFavoritesBox() {
        if(fanFavoritesBox.getChildren().size() != 0) {
            fanFavoritesBox.getChildren().remove(0, 5);
        }

        for (int i = 5 * (fanFavoritesIndex - 1); i < 5 * fanFavoritesIndex; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.fanFavoritesMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.fanFavoritesMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.fanFavoritesMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.fanFavoritesMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.fanFavoritesMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            fanFavoritesBox.getChildren().add(vBox);
        }
    }

    public void generateTopPicksBox() {
        if(topPicksBox.getChildren().size() != 0) {
            topPicksBox.getChildren().remove(0, 5);
        }

        for (int i = 5 * (topPicksIndex - 1); i < 5 * topPicksIndex; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topPicksMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topPicksMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topPicksMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topPicksMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topPicksMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            topPicksBox.getChildren().add(vBox);
        }
    }

    public void generateTopRatedByUsersBox() {
        if(topRatedByUsersBox.getChildren().size() != 0) {
            topRatedByUsersBox.getChildren().remove(0, 5);
        }

        for (int i = 5 * (topRatedByUsersIndex - 1); i < 5 * topRatedByUsersIndex; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topRatedByUsersMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topRatedByUsersMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topRatedByUsersMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topRatedByUsersMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topRatedByUsersMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            topRatedByUsersBox.getChildren().add(vBox);
        }
    }

    public void generateComingSoonGrid() {
        GridPane movieGrid = new GridPane();

        for (int i = 0; i < 30; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.comingSoonMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.comingSoonMovies.get(i).getId());

            Label label = new Label(Data.comingSoonMovies.get(i).getTitle());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.comingSoonMovies.get(i).getReleaseDate() + "");
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            movieGrid.add(vBox, i % 5, i / 5);

            GridPane.setMargin(vBox, new Insets(10, 10, 10, 10));
        }

        moviesPane.setContent(movieGrid);
    }

    public void generateTopRatedGrid() {
        GridPane movieGrid = new GridPane();

        for (int i = 0; i < 30; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topRatedMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topRatedMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topRatedMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topRatedMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topRatedMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            movieGrid.add(vBox, i % 5, i / 5);

            GridPane.setMargin(vBox, new Insets(10, 10, 10, 10));
        }

        moviesPane.setContent(movieGrid);
    }

    public void generateFanFavoritesGrid() {
        GridPane movieGrid = new GridPane();

        for (int i = 0; i < 30; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.fanFavoritesMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.fanFavoritesMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.fanFavoritesMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.fanFavoritesMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.fanFavoritesMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            movieGrid.add(vBox, i % 5, i / 5);

            GridPane.setMargin(vBox, new Insets(10, 10, 10, 10));
        }

        moviesPane.setContent(movieGrid);
    }

    public void generateTopPicksGrid() {
        GridPane movieGrid = new GridPane();

        for (int i = 0; i < 30; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topPicksMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topPicksMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topPicksMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topPicksMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topPicksMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            movieGrid.add(vBox, i % 5, i / 5);

            GridPane.setMargin(vBox, new Insets(10, 10, 10, 10));
        }

        moviesPane.setContent(movieGrid);
    }

    public void generateTopRatedByUsersGrid() {
        GridPane movieGrid = new GridPane();

        for (int i = 0; i < 30; i++) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(180);
            vBox.setPrefHeight(330);

            ImageView imageView = new ImageView(new Image("file:images/" + Data.topRatedByUsersMovies.get(i).getImageSrc(), true));
            imageView.setFitWidth(180);
            imageView.setFitHeight(250);
            imageView.setId("image" + Data.topRatedByUsersMovies.get(i).getId());
            int x = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.currentMovie = Data.topRatedByUsersMovies.get(x);
                    generateMoviePage();
                }
            });

            Label label = new Label( "\uD83C\uDF1F " + Data.topRatedByUsersMovies.get(i).getRating());
            label.setPrefWidth(250);
            label.setPrefHeight(40);
            label.setFont(new Font(20));

            Label label1 = new Label(Data.topRatedByUsersMovies.get(i).getTitle());
            label1.setPrefWidth(250);
            label1.setPrefHeight(40);
            label1.setFont(new Font(20));

            vBox.getChildren().addAll(imageView, label, label1);

            movieGrid.add(vBox, i % 5, i / 5);

            GridPane.setMargin(vBox, new Insets(10, 10, 10, 10));
        }

        moviesPane.setContent(movieGrid);
    }
}
