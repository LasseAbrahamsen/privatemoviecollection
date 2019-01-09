package movieplayer.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movieplayer.be.Movie;
import movieplayer.bll.Facade;

/**
 *
 * @author a
 */
public class MovieModel {
    
    Facade facade = new Facade();
    
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
    }
    
    public Movie createMovie(String name, int rating, String filelink, String lastview) {
        return facade.createMovie(name, rating, filelink, lastview);
    }
    
    public Movie updateMovie(Movie movie, String name, int rating, String filelink, String lastview) {
        return facade.updateMovie(movie, name, rating, filelink, lastview);
    }
    
    public List<Movie> getAllMovies() {
        return facade.getAllMovies();
    }
    
    public void deleteMovie(Movie m) {
        facade.deleteMovie(m);
    }
    
}
