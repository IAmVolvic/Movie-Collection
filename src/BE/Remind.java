package BE;

import DAL.CategoryLogic.SelectCategory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Remind {
    private final int findIndex;
    private final String movieName;
    private final Double movieRating;
    private final String movieLastSeen;
    private final ArrayList<String> categoryTable = new ArrayList<>();

    public Remind(int mId, String mName, Double mRating, String mLastSeen){
        this.findIndex = mId;
        this.movieName = mName;
        this.movieRating = mRating;
        this.movieLastSeen = mLastSeen;
    }

    public Integer getIndex(){ return this.findIndex; }

    public String getMovieName(){ return this.movieName; }

    public Double getMovieRating() { return this.movieRating; }

    public String getMovieLastSeen() { return this.movieLastSeen; }

    public void addCategory(String selectedCategory){
        this.categoryTable.add(selectedCategory);
    }

    public ArrayList<String> getConnectedCategories() { return this.categoryTable; }
}
