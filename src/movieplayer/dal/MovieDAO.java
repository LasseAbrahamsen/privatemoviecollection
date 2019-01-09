package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import movieplayer.be.Movie;

/**
 *
 * @author a
 */
public class MovieDAO {
    
    SQLServerDataSource ds;
    
    public MovieDAO() {
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
    //testMov = new MovieDAO
    // testMov.createMovie(textfieldinfo, 0, data/movieName, lastview)
    //creates a movie with variables name, rating, categories, filelink and lastview
    public Movie createMovie(String name, int rating, String filelink, String lastview) {
        Movie m = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO Movie(name, rating, filelink, lastview) VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, rating);
            stmt.setString(3, filelink);
            stmt.setString(4, lastview);
            stmt.execute();
            m = new Movie(name, rating, logicfacade.getEnteredCategories(), m.getFilelink(), getLastID());
            return m;
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    //The method is for movie creation. It gives us the last ID in the movie list.
    public int getLastID() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            String sql = "SELECT TOP(1) * FROM Movie ORDER by id desc";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();
            while(rs.next()) {
                lastID = rs.getInt("id");
            }
            return lastID;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
        catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
    }
    
    //Updating the movie, if the user wants to edit a movie that already exists in the database.
    public Movie updateMovie(Movie movie, String name, int rating, String filelink, String lastview) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE Movie set name=?, rating=?, filelink=?, lastview=? WHERE id=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, rating);
            preparedStmt.setString(3, filelink);
            preparedStmt.setString(4, lastview);
            preparedStmt.setInt(5, movie.getID());
            preparedStmt.executeUpdate();
            Movie m = new Movie(name, rating, logicfacade.getEnteredCategories(), m.getFilelink(), lastview, movie.getID());
            return m;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //Deletes a song from the database.
    public void deleteMovie(Movie m) {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, m.getID());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //returns a list of all the songs, used for the song list.
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM Movie";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                String categories = rs.getString("categories");
                String filelink = rs.getString("filelink");
                String lastview = rs.getString("lastview");
                int id = rs.getInt("id");
                Movie m = new Movie(name, rating, logicfacade.getCategories(), lastview, filelink, id);
                movies.add(m);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movies;
    }
    
}
