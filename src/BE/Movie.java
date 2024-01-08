package BE;

import java.util.Date;

public class Movie {
    private int id;
    private String name;
    private double rating;
    private String moviePath;
    private String lastViewed;

    public Movie(int setId, String setName, double setRating, String setPath, String setLastViewed){
        this.id = setId;
        this.name = setName;
        this.rating = setRating;
        this.moviePath = setPath;
        this.lastViewed = setLastViewed;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getRating() {
        return this.rating;
    }

    public String getMoviePath() { return this.moviePath; }

    public String getLastViewed() {
        return this.lastViewed;
    }
}
