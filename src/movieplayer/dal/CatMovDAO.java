package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    public CatMovDAO() throws IOException {
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
    /*
    public void addCategoriesToMovie(Movie movie, List<Category> categories) {
        try (Connection con = ds.getConnection()) {
            String sql = "SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CatMovie.CategoryId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, c.getID());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
        
        
    
}
