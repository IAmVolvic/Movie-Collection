package DAL.CatMoviesLogic;

import BE.Movie;
import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.*;


public class InsertCat {
    private final ConnectionManager cm = new ConnectionManager();

    public void newCat(int movieId, int categoryId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO catMovie(categoryId, movieId) VALUES (?, ?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, categoryId);
            pt.setInt(2, movieId);
            pt.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}