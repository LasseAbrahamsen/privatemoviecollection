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

/**
 *
 * @author a
 */
public class Facade {
    
    MovieDAO mdao = new MovieDAO();
    CategoryDAO cdao = new CategoryDAO();

    public Movie createMovie(String name, int rating, ArrayList<Category> categories, String filelink, LocalDate lastview) throws SQLException {
        return mdao.createMovie(name, rating, categories, filelink, Date.valueOf(lastview));
    }
    /*
    public Movie updateMovie(Movie movie, String name, int rating, String filelink) {
        return mdao.updateMovie(movie, name, rating, filelink);
    }*/

    public ArrayList<Movie> getAllMovies() throws SQLException {
        return mdao.getAllMovies();
    }
    
    /*
    public void deleteMovie(Movie m) {
        mdao.deleteMovie(m);
    } */
    
    public Category createCategory(String name) throws SQLException {
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
