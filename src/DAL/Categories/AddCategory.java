package DAL.Categories;

import BE.Category;
import DAL.ConnectionManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;

public class AddCategory {
    public void addCategoryToDB(Category category){
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            Connection con = connectionManager.getConnection();
            String sql = "INSERT INTO category (name) VALUES (?)";
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            st.setString(1, category.getName());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId( generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
