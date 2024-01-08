package BE;

import java.util.ArrayList;

public class Category {
    private int categoryId;
    private String name;

    private ArrayList<Integer> movieIds = new ArrayList<>();

    public Category(int id, String name) {
        this.categoryId = id;
        this.name = name;
    }

    public int getId() {
        return this.categoryId;
    }

    public String getName() {
        return name;
    }

    public void setMovieIds(ArrayList<Integer> movieIds) {this.movieIds = movieIds;}

    public ArrayList<Integer> getMovieIds(){
        return movieIds;
    }
}
