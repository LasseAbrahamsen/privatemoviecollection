package movieplayer.gui.controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.model.MovieModel;


/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class MainWindowController implements Initializable {

    @FXML private TableView<Movie> tableViewMain;
    @FXML private TableColumn<Movie, String> colMovieTitle;
    @FXML private TableColumn<Movie, String> colCategory;
    @FXML private TableColumn<Movie, String> colRating;
    @FXML private ComboBox<Category> comboboxFilterCategory;
    @FXML private TextField textfieldSearch;
    
    MovieModel mmodel = new MovieModel();
    CategoryModel cmodel = new CategoryModel();
    private ObservableList<Movie> observableListMovie;
    private ObservableList<Category> observableListCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListMovie = mmodel.getMovies();
        observableListCategory = cmodel.getCategories();
        
        colMovieTitle.setCellValueFactory(new PropertyValueFactory("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory("category"));
        colRating.setCellValueFactory(new PropertyValueFactory("rating"));
        
        tableViewMain.setItems(mmodel.getMovies());
        
        comboboxFilterCategory.getItems().addAll(observableListCategory);
    }
    
    @FXML
    private void editCategoriesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/CategoryWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addMovieWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void editMovieWindow(ActionEvent event) throws IOException {
        Movie selectedMovie = tableViewMain.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
        MovieWindowController controller = fxmlLoader.getController();
        controller.setEditingMode(selectedMovie);
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML private void deleteMovie(ActionEvent event) {
        Movie clickedMovie = tableViewMain.getSelectionModel().getSelectedItem();
        //mmodel.deleteMovie(clickedMovie);
    }
    
    @FXML private void search (KeyEvent event) {
        //TODO
    }
    
    
    
    
    
}
