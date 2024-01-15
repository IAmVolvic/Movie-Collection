package DAL.MovieLogic;

import BE.Movie;
import COMMON.ApplicationException;
import DAL.ConnectionManager;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class InsertMovie {
    private final ConnectionManager cm = new ConnectionManager();

    public Movie newMovie(String movieName, String filePath, Double movieRating) throws ApplicationException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO movie(name, rating, filePath, lastViewed) VALUES (?, ?, ?, ?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, movieName);
            pt.setDouble(2, movieRating);
            pt.setString(3, filePath);
            pt.setString(4, dateFormat.format(cal.getTime()));
            int affectedRows = pt.executeUpdate();

            if (affectedRows == 0) {
                throw new ApplicationException("Creating movie failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Movie(generatedKeys.getInt(1), movieName, movieRating, filePath, dateFormat.format(cal.getTime()));
                }
                else {
                    throw new ApplicationException("Creating movie failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}