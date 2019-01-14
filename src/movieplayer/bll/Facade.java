package movieplayer.bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.dal.MovieDAO;
import movieplayer.dal.CategoryDAO;
import movieplayer.exceptions.CreateCategoryException;
import movieplayer.exceptions.CreateMovieException;

/**
 * 
 * @author a
 */
public class Facade {
    
    MovieDAO mdao = new MovieDAO();
    CategoryDAO cdao = new CategoryDAO();

    public Movie createMovie(String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview, double imdbRating) throws SQLException, CreateMovieException {
        return mdao.createMovie(name, rating, categories, filelink, Date.valueOf(lastview), imdbRating);
    }
    
    public Movie updateMovie(Movie movie, String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview, double imdbRating) throws SQLException {
        return mdao.updateMovie(movie, name, rating, categories, filelink, Date.valueOf(lastview), imdbRating);
    }

    public ArrayList<Movie> getAllMovies(String nameFilter, double minImdbRatingFilter, ArrayList<Category> categoryFilter) throws SQLException {
        return mdao.getAllMovies(nameFilter, minImdbRatingFilter, categoryFilter);
    }
    
    public void deleteMovie(Movie m) throws SQLException {
        mdao.deleteMovie(m);
    }
    
    public ArrayList<String> getObsoleteMovies() throws SQLException {
        return mdao.getObsoleteMovies();
    }
    
    public void deleteObsoleteMovies() throws SQLException {
        mdao.deleteObsoleteMovies();
    }
    
    public Category createCategory(String name) throws SQLException, CreateCategoryException {
        return cdao.createCategory(name);
    }
    
    public Category updateCategory(Category category, String name) {
        return cdao.updateCategory(category, name);
    }
    
    public void deleteCategory(Category c) throws SQLException {
        cdao.deleteCategory(c);
    }
    
    public List<Category> getAllCategories() {
        return cdao.getAllCategories();
    }
}
