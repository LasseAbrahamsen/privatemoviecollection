package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movieplayer.be.Category;
import movieplayer.be.Movie;

/**
 *
 * @author a
 */
public class CatMovDAO {
    
        SQLServerDataSource ds;
    
    public CatMovDAO() {
        this.ds = new SQLServerDataSource();
        DBConnect connectionInfo = new DBConnect(); 
        List<String> loginInfo; 
        try {
            loginInfo = connectionInfo.getDatabaseInfo();
            ds.setDatabaseName(loginInfo.get(0));
            ds.setUser(loginInfo.get(1));
            ds.setPassword(loginInfo.get(2));
            ds.setPortNumber(Integer.parseInt(loginInfo.get(3)));
            ds.setServerName(loginInfo.get(4));
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCategoriesToMovie(Movie movie, ArrayList<Category> categories) {
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO CatMovie(MovieId, CategoryId) VALUES(?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            for (Category category: categories) {
                stmt.setInt(1, movie.getID());
                stmt.setInt(2, category.getID());
                stmt.execute(); 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateCategoriesOfMovie(Movie movie, ArrayList<Category> categories) {
        try (Connection con = ds.getConnection()) {
            String sqlDel = "DELETE FROM CatMovie WHERE MovieId=?";
            PreparedStatement stmtDel = con.prepareStatement(sqlDel);
            for (Category category: categories) {
                stmtDel.setInt(1, movie.getID());
                stmtDel.execute(); 
            }
            String sqlIns = "INSERT INTO CatMovie(MovieId, CategoryId) VALUES(?,?)";
            PreparedStatement stmtIns = con.prepareStatement(sqlIns);
            for (Category category: categories) {
                stmtIns.setInt(1, movie.getID());
                stmtIns.setInt(2, category.getID());
                stmtIns.execute(); 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CatMovie.CategoryId = ?
        
        
    
}
