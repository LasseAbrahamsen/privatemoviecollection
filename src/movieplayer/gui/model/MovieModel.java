package movieplayer.gui.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.bll.Facade;

/**
 *
 * @author a
 */
public class MovieModel {
    
    Facade facade = new Facade();
    
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() throws SQLException {
        movies.clear();
        movies.addAll(facade.getAllMovies());
        return movies;
    }
    
    public Movie createMovie(String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview) throws SQLException {
        return facade.createMovie(name, rating, categories, filelink, lastview);
    } 
    /*
    
    public Movie updateMovie(Movie movie, String name, int rating, String filelink) {
        return facade.updateMovie(movie, name, rating, filelink);
    }
    
    public ArrayList<Movie> getAllMovies() throws SQLException {
        return facade.getAllMovies();
    }
    
    public void deleteMovie(Movie m) {
        facade.deleteMovie(m);
    }
*/
    
}
