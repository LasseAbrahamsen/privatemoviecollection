package movieplayer.bll;

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
    
    public Movie createMovie(String name, int rating, String filelink, String lastview) {
        return mdao.createMovie(name, rating, filelink, lastview);
    }
    
    public Movie updateMovie(Movie movie, String name, int rating, String filelink, String lastview) {
        return mdao.updateMovie(movie, name, rating, filelink, lastview);
    }
    
    public List<Movie> getAllMovies() {
        return mdao.getAllMovies();
    }
    
    public void deleteMovie(Movie m) {
        mdao.deleteMovie(m);
    }
    
    public Category createCategory(String name) {
        return cdao.createCategory(name);
    }
    
    public Category updateCategory(Category category, String name) {
        return cdao.updateCategory(category, name);
    }
    
    public void deleteCategory(Category c) {
        cdao.deleteCategory(c);
    }
    
    public List<Category> getAllCategories() {
        return cdao.getAllCategories();
    }
}
