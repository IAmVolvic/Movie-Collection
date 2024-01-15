package DAL.MovieLogic;

import BE.Movie;
import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditMovie {
    private final ConnectionManager cm = new ConnectionManager();

    public void editMovie(Movie oldMovie, Movie newMovie) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "UPDATE movie SET name = ?, rating=? WHERE id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, newMovie.getName() );
            pt.setDouble(2,newMovie.getRating());
            pt.setInt(3,oldMovie.getId());
            pt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
