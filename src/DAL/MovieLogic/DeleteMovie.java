package DAL.MovieLogic;

import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMovie {
    private final ConnectionManager cm = new ConnectionManager();

    public void removeMovie(int movieId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM movie WHERE id = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, movieId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}