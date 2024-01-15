package DAL.CatMoviesLogic;

import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class SelectCat {
    private final ConnectionManager cm = new ConnectionManager();


    public ArrayList<Integer> getCatMovieListDB(int categoryId) throws ApplicationException {
        ArrayList<Integer> catMovieList = new ArrayList<>();
        try( Connection con = cm.getConnection() )
        {
            String sql = "SELECT movieId FROM catMovie WHERE categoryId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,categoryId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id              = rs.getInt("movieId");
                catMovieList.add(id);
            }
            return catMovieList;
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
