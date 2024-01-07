package DAL.MovieLogic;

import BE.Movie;
import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.util.ArrayList;

public class SelectMovie {
    private final ConnectionManager cm = new ConnectionManager();
    private final ArrayList<Movie> movieList = new ArrayList<>();

    public ArrayList<Movie> getMoviesDB() throws ApplicationException { return null;}
}
