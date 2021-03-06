package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.exceptions.CreateMovieException;

public class MovieDAO {
    
    SQLServerDataSource ds;
    CatMovDAO cmDAO = new CatMovDAO();
    
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
    
    //Creates a movie with variables name, rating, categories, filelink, lastview, imdbRating.
    public Movie createMovie(String name, int rating, ArrayList<Category> categories, String filelink, Date lastview, double imdbRating) throws SQLException, CreateMovieException {
        Movie m = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO Movie(name, rating, filelink, lastview, imdbRating) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, rating);
            stmt.setString(3, filelink);
            stmt.setDate(4, lastview);
            stmt.setDouble(5, imdbRating);
            stmt.execute();
            m = new Movie(name, rating, categories, filelink, lastview, getLastID(), imdbRating);
            cmDAO.setCategoriesToMovie(m, categories);
            return m;
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            if(ex.getErrorCode() == 2627) { //2627 = unique key violated.
                throw new CreateMovieException("There is already a movie with name " + name);
            }
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    //The method is for movie creation. It gives us the last ID in the movie list.
    public int getLastID() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            String sql = "SELECT TOP(1) * FROM Movie ORDER BY id DESC";
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
    public Movie updateMovie(Movie movie, String name, int rating, ArrayList<Category> categories, String filelink, Date lastview, double imdbRating) throws SQLException {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE Movie set name=?, rating=?, filelink=?, lastview=?, imdbRating=? WHERE id=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, rating);
            preparedStmt.setString(3, filelink);
            preparedStmt.setDate(4, lastview);
            preparedStmt.setDouble(5, imdbRating);
            preparedStmt.setInt(6, movie.getID());
            preparedStmt.executeUpdate();
            Movie m = new Movie(name, rating, categories, filelink, lastview, movie.getID(), imdbRating);
            cmDAO.updateCategoriesOfMovie(m, categories);
            return m;
        }
        catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    //Deletes a movie from the database.
    public void deleteMovie(Movie m) throws SQLException {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, m.getID());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    //Returns a list of all movies, also includes filtering.
    public ArrayList<Movie> getAllMovies(String nameFilter, double minImdbRatingFilter, ArrayList<Category> categoryFilter) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        HashMap<Integer, Movie> moviesById = new HashMap<>();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT Movie.id, Movie.name, Movie.rating, Movie.filelink, Movie.lastview, Movie.imdbRating, Category.name AS categoryName, Category.id AS categoryId FROM Movie LEFT JOIN CatMovie ON CatMovie.MovieId = Movie.id JOIN Category ON Category.id = CatMovie.CategoryId WHERE Movie.name LIKE ? AND imdbRating >= ?";
            if(categoryFilter.size() > 0) {
                ArrayList<String> ids = new ArrayList<>();
                for (Category category : categoryFilter) {
                    ids.add(String.valueOf(category.getID()));
                }
                sqlStatement += " AND CatMovie.CategoryId IN (" + String.join(",", ids) + ")";
            }
            PreparedStatement pstmt = con.prepareStatement(sqlStatement);
            pstmt.setString(1, "%" + nameFilter + "%");
            pstmt.setDouble(2, minImdbRatingFilter);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                String filelink = rs.getString("filelink");
                String lastview = rs.getString("lastview");
                int id = rs.getInt("id");
                double imdbRating = rs.getDouble("imdbRating");
                if(moviesById.containsKey(id)) {
                    Movie m = moviesById.get(id);
                    m.addCategory(new Category(rs.getString("categoryName"), rs.getInt("categoryId")));
                } else {
                    ArrayList<Category> cats = new ArrayList<>();
                    String catName = rs.getString("categoryName");
                    if(catName != null) {
                        cats.add(new Category(rs.getString("categoryName"), rs.getInt("categoryId")));
                    }
                    Movie m = new Movie(name, rating, cats, filelink, Date.valueOf(lastview), id, imdbRating);
                    movies.add(m);
                    moviesById.put(id, m);
                }
            }
            return movies;
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    //Deletes movies that are under 6 personal rating and are over 730 days old (2 years).
    public void deleteObsoleteMovies() throws SQLException {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM Movie WHERE rating < 6 AND DATEDIFF(day, lastview, GETDATE()) >= 730";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    //Returns a list of strings which contains the movie names.
    public ArrayList<String> getObsoleteMovies() throws SQLException {
        ArrayList<String> obsoleteMovies = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT name FROM Movie WHERE rating < 6 AND DATEDIFF(day, lastview, GETDATE()) >= 730";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                String name = rs.getString("name");
                obsoleteMovies.add(name);
            }
            return obsoleteMovies;
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
