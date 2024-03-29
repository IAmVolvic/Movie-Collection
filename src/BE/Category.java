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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMovieIds(ArrayList<Integer> movieIds) {this.movieIds = movieIds;}

    public ArrayList<Integer> getMovieIds(){
        return movieIds;
    }

    public void addToMovieIds(int id){
        movieIds.add(id);
    }
    public void deleteFromMovieIds(Integer id){
        movieIds.remove(id);
    }
}
