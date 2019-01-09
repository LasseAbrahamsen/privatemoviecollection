package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movieplayer.be.Category;

/**
 *
 * @author a
 */
public class CategoryDAO {
    
    SQLServerDataSource ds;
    
    public CategoryDAO() {
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
    
    public Category createCategory(String name) {
        Category c = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO Category(name) VALUES(?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.execute();
            c = new Category(name, getLastID());
            return c;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public int getLastID() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            String sql = "SELECT TOP(1) * FROM Category ORDER by id desc";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();
            while(rs.next()) {
                lastID = rs.getInt("id");
            }
            return lastID;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
    }
    
    public Category updateCategory(Category category, String name) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE Category set name=? WHERE id=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, category.getID());
            preparedStmt.executeUpdate();
            Category c = new Category(name, category.getID());
            return c;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteCategory(Category c) {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM Category WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, c.getID());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM Category";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                Category c = new Category(name, id);
                categories.add(c);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }
        
        
        
        
    
}
