package movieplayer.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movieplayer.be.Category;
import movieplayer.bll.Facade;

/**
 *
 * @author a
 */
public class CategoryModel {
    
    Facade facade = new Facade();
    
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Category> getMovies() {
        return categories;
    }
    
    public Category createCategory(String name) {
        return facade.createCategory(name);
    }
    
    public Category updateCategory(Category category, String name) {
        return facade.updateCategory(category, name);
    }
    
    public void deleteCategory(Category c) {
        facade.deleteCategory(c);
    }
    
    public List<Category> getAllCategories() {
        return facade.getAllCategories();
    }
    
}
