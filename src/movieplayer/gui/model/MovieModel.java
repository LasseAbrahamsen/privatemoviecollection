package movieplayer.gui.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.bll.Facade;
import movieplayer.exceptions.CreateMovieException;

/**
 *
 * @author a
 */
public class MovieModel {
    
    Facade facade = new Facade();
    
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies(String nameFilter, double minImdbRatingFilter, ArrayList<Category> categoryFilter) throws SQLException {
        movies.clear();
        movies.addAll(facade.getAllMovies(nameFilter, minImdbRatingFilter, categoryFilter));
        return movies;
    }
    
    public Movie createMovie(String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview, double imdbRating) throws SQLException, CreateMovieException {
        return facade.createMovie(name, rating, categories, filelink, lastview, imdbRating);
    } 
    
    public Movie updateMovie(Movie movie, String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview, double imdbRating) throws SQLException {
        return facade.updateMovie(movie, name, rating, categories, filelink, lastview, imdbRating);
    }
    
    public void deleteMovie(Movie m) throws SQLException{
        facade.deleteMovie(m);
    }
    
    public ArrayList<String> getObsoleteMovies() throws SQLException {
        return facade.getObsoleteMovies();
    }
    
    public void deleteObsoleteMovies() throws SQLException {
        facade.deleteObsoleteMovies();
    }
    
}
