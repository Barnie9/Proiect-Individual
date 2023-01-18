package api;

import classes.Data;
import classes.Movie;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainAPI {
    private static Set<String> set;

    private static void readFromFile(String file) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                set.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String file) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for(String string : set) {
                bufferedWriter.write(string + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getMovieIdByGenre(String genre) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/v2/get-popular-movies-by-genre?genre=" + genre + "&limit=100"))
                .header("X-RapidAPI-Key", "652288369emsh6c5512431e8ad5ep10119cjsn0559421fccc2")
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String query1 = response.body();
        while(query1.charAt(1) != ']') {
            query1 = query1.substring(query1.indexOf("/") + 1, query1.length());
            query1 = query1.substring(query1.indexOf("/") + 1, query1.length());
            set.add(query1.substring(query1.indexOf("tt"), query1.indexOf("/")));
            query1 = query1.substring(query1.indexOf("/") + 1, query1.length());
        }
    }

    private static String getMovieDetailsById(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=" + id + "&currentCountry=US"))
                .header("X-RapidAPI-Key", "652288369emsh6c5512431e8ad5ep10119cjsn0559421fccc2")
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private static void insertIntoDatabase(String movieDetails) throws IOException, SQLException {
        String imageURL = "";
        int runningTime = 0;
        String title = "";
        double rating = 0;
        String releaseDateString = "";
        String description = "";
        String genresString = "";

        if(movieDetails.contains("\"url\":\"")) {
            imageURL = movieDetails.substring(movieDetails.indexOf("\"url\":\"") + 7, movieDetails.indexOf('\"', movieDetails.indexOf("\"url\":\"") + 7));
        }
        if(movieDetails.contains("\"runningTimeInMinutes\":")) {
            runningTime = Integer.parseInt(movieDetails.substring(movieDetails.indexOf("\"runningTimeInMinutes\":") + 23, movieDetails.indexOf(',', movieDetails.indexOf("\"runningTimeInMinutes\":") + 23)));
        }
        if(movieDetails.contains("\"title\":\"")) {
            title = movieDetails.substring(movieDetails.indexOf("\"title\":\"") + 9, movieDetails.indexOf('"', movieDetails.indexOf("\"title\":\"") + 9));
        }
        if(movieDetails.contains("\"rating\":")) {
            rating = Double.parseDouble(movieDetails.substring(movieDetails.indexOf("\"rating\":") + 9, movieDetails.indexOf(',', movieDetails.indexOf("\"rating\":") + 9)));
        }
        if(movieDetails.contains("\"releaseDate\":\"")) {
            releaseDateString = movieDetails.substring(movieDetails.indexOf("\"releaseDate\":\"") + 15, movieDetails.indexOf('\"', movieDetails.indexOf("\"releaseDate\":\"") + 15));
        }
        if(movieDetails.contains("\"plotOutline\":{")) {
            String plot = movieDetails.substring(movieDetails.indexOf("\"plotOutline\":{") + 15, movieDetails.indexOf('}', movieDetails.indexOf("\"plotOutline\":{") + 15));
            description = plot.substring(plot.indexOf("\"text\":\"") + 8, plot.indexOf('\"', plot.indexOf("\"text\":\"") + 8));
        }
        if(movieDetails.contains("\"genres\":[")) {
            genresString = movieDetails.substring(movieDetails.indexOf("\"genres\":[") + 10, movieDetails.indexOf(']', movieDetails.indexOf("\"genres\":[") + 10));
        }

        if(releaseDateString.length() == 10) {
            genresString = genresString.replaceAll("[\"]", "");
            String[] genres = genresString.split(",");
            List<String> genresList = new ArrayList<>(List.of(genres));

            String imageSrc = title.replaceAll("[^a-zA-Z0-9]", "") + ".jpg";
            InputStream in = new URL(imageURL).openStream();
            Files.copy(in, Paths.get("D:/Proiect_P3/p3-proiect-ia-sg1-Barnie9/images/" + imageSrc));

            Date releaseDate = Date.valueOf(releaseDateString);
            String query;
            PreparedStatement ps;
            query = "insert into movies (title, image_src, rating, running_time, release_date, description) values (?, ?, ?, ?, ?, ?)";
            ps = Data.connection.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, imageSrc);
            ps.setDouble(3, rating);
            ps.setInt(4, runningTime);
            ps.setDate(5, releaseDate);
            ps.setString(6, description);
            ps.execute();

            query = "insert into genres (id_movie, genre) values ((select id_movie from movies where title = ? and release_date = ?), ?)";
            ps = Data.connection.prepareStatement(query);
            for (String query1 : genresList) {
                ps.setString(1, title);
                ps.setDate(2, releaseDate);
                ps.setString(3, query1);
                ps.execute();
            }
        }
    } 

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        getMovieIdByGenre("action");
        getMovieIdByGenre("adventure");
        getMovieIdByGenre("comedy");
        getMovieIdByGenre("crime");
        getMovieIdByGenre("documentary");
        getMovieIdByGenre("drama");
        getMovieIdByGenre("family");
        getMovieIdByGenre("fantasy");
        getMovieIdByGenre("horror");
        getMovieIdByGenre("romance");
        getMovieIdByGenre("sci-fi");
        getMovieIdByGenre("thriller");

        set = new HashSet<>();

        readFromFile("movie_id.txt");

        Set<String> toBeRemoved = new HashSet<>();
        int i = 0;
        for (String id : set) {
            if (i == 50) {
                break;
            }
            insertIntoDatabase(getMovieDetailsById(id));
            toBeRemoved.add(id);
            i++;
        }
        set.removeAll(toBeRemoved);

        writeToFile("movie_id.txt");
    }
}
