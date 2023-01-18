package classes;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Movie {

    private int id;
    private String title;
    private String imageSrc;
    private double rating;
    private int runningTime;
    private Date releaseDate;
    private String description;
    List<String> genres;

    public Movie() {

    }

    public Movie(int id, String title, String imageSrc, double rating, int runningTime, Date releaseDate, String description, List<String> genres) {
        this.id = id;
        this.title = title;
        this.imageSrc = imageSrc;
        this.rating = rating;
        this.runningTime = runningTime;
        this.releaseDate = releaseDate;
        this.description = description;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                "\nTitle: " + title +
                "\nImage: " + imageSrc +
                "\nRating: " + rating +
                "\nRunning Time: " + runningTime +
                "\nRelease Date: " + releaseDate +
                "\nGenres: " + genres;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public double getRating() {
        return rating;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
        return genres;
    }
}
