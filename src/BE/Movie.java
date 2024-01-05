package BE;

import java.util.Date;

public class Movie {
    private int id;
    private String name;
    private double rating;
    private String path;
    private String lastViewed;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getLastViewed() {
        return lastViewed;
    }
}
