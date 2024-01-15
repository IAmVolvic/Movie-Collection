package DAL.CatMoviesLogic;

import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteCat {
    private final ConnectionManager cm = new ConnectionManager();

    public void removeCatByMovie(int movieId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM catMovie WHERE movieId = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, movieId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }


    public void removeCatByCategory(int categoryId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM catMovie WHERE categoryId = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, categoryId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
