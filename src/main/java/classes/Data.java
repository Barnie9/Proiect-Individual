package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Data {

    public static Connection connection;
    public static List<Movie> movies;
    public static List<String> genres;
    public static List<Movie> comingSoonMovies;
    public static List<Movie> topRatedMovies;
    public static List<Movie> fanFavoritesMovies;
    public static List<Movie> topPicksMovies;
    public static List<Movie> topRatedByUsersMovies;
    public static Movie currentMovie;
    public static User connectedUser;
    public static List<Movie> watchlist;
    public static List<Movie> favorites;

    public static void getConnection() {
        String databaseName = "movienight";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select * from movies where release_date < current_date";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            movies = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                movies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getGenresFromDatabase() {
        try {
            String query = "select distinct genre from genres";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            genres = new ArrayList<>();
            while(resultSet.next()) {
                genres.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getComingSoonMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select * from movies where release_date > current_date";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            comingSoonMovies = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                comingSoonMovies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTopRatedMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select * from movies order by rating desc";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            topRatedMovies = new ArrayList<>();
            while (resultSet.next() || topRatedMovies.size() == 30) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                topRatedMovies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getFanFavoritesMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select movies.*, count(favlist.id_user) from movies left join favlist on movies.id_movie = favlist.id_movie where movies.release_date < current_date group by 1 order by count(favlist.id_user) desc";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            fanFavoritesMovies = new ArrayList<>();
            while (resultSet.next() || fanFavoritesMovies.size() == 30) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                fanFavoritesMovies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTopPicksMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select movies.*, count(watchlist.id_user) from movies left join watchlist on movies.id_movie = watchlist.id_movie where movies.release_date < current_date group by 1 order by count(watchlist.id_user) desc";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            topPicksMovies = new ArrayList<>();
            while (resultSet.next() || topPicksMovies.size() == 30) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                topPicksMovies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTopRatedByUsersMoviesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select movies.*, avg(ratings.rating) from movies left join ratings on movies.id_movie = ratings.id_movie where movies.release_date < current_date group by 1 order by avg(ratings.rating) desc";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            topRatedByUsersMovies = new ArrayList<>();
            while (resultSet.next() || topRatedByUsersMovies.size() == 30) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                topRatedByUsersMovies.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getWatchlistFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select * from movies where id_movie in (select id_movie from watchlist where id_user = ?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, connectedUser.getId());
            ResultSet resultSet = ps.executeQuery();

            watchlist = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                watchlist.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getFavoritesFromDatabase() {
        try {
            String query;
            PreparedStatement ps;

            query = "select * from movies where id_movie in (select id_movie from favlist where id_user = ?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, connectedUser.getId());
            ResultSet resultSet = ps.executeQuery();

            favorites = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imageSrc = resultSet.getString(3);
                double rating = resultSet.getDouble(4);
                int runningTime = resultSet.getInt(5);
                Date releaseDate = resultSet.getDate(6);
                String description = resultSet.getString(7);

                query = "select genre from genres where id_movie = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet resultSet1 = ps.executeQuery();
                List<String> genres = new ArrayList<>();
                while(resultSet1.next()) {
                    genres.add(resultSet1.getString(1));
                }

                favorites.add(new Movie(id, title, imageSrc, rating, runningTime, releaseDate, description, genres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
