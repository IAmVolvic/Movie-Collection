package DAL.MovieLogic;

import BE.Movie;
import COMMON.ApplicationException;
import DAL.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectMovie {
    private final ConnectionManager cm = new ConnectionManager();
    private final ObservableList<Movie> movieList = FXCollections.observableArrayList();

    public ObservableList<Movie> getMoviesDB() throws ApplicationException {
        try( Connection con = cm.getConnection() )
        {
            String sql = "SELECT * FROM movie";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int movieId              = rs.getInt("id");
                String movieName         = rs.getString("name");
                Double movieRating       = rs.getDouble("rating");
                String movieFilePath     = rs.getString("filePath");
                String movieLastViewed   = rs.getString("lastViewed");

                Movie cate = new Movie(movieId, movieName, movieRating, movieFilePath, movieLastViewed);
                movieList.add(cate);
            }
            return movieList;

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
