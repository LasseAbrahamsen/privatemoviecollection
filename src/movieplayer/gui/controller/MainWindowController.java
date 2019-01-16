package movieplayer.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.model.MovieModel;
import movieplayer.gui.util.MessageBoxHelper;


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
    @FXML private TableColumn<Movie, String> colImdb;
    @FXML private TextField textfieldSearch;
    @FXML private TextField textfieldFilterImdb;
    @FXML private Label labelCategoryFilter;
    @FXML private ComboBox<Category> comboboxSelectCategoryFilter;
    
    //fxml instance variables used for css
    @FXML private Button removeMoviebtn;
    @FXML private Button editMoviebtn;
    @FXML private Label title;
    @FXML private Button exitbtn;
    @FXML private Button editCategoriesbtn;
    @FXML private Button playbtn;
    @FXML private Button addFilterbtn;
    @FXML private Button resetFilterbtn;
    
    private ArrayList<Category> selectedCategories;
    
    MovieModel mmodel = new MovieModel();
    CategoryModel cmodel = new CategoryModel();
    private ObservableList<Movie> observableListMovie;
    private ObservableList<Category> observableListCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedCategories = new ArrayList<>();
        observableListCategory = cmodel.getCategories();
        
        colMovieTitle.setCellValueFactory(new PropertyValueFactory("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory("categories"));
        colRating.setCellValueFactory(new PropertyValueFactory("rating"));
        colImdb.setCellValueFactory(new PropertyValueFactory("imdbRating"));
        
        /*
            -> lambda syntax. In principle it's a method like this:
            private void onFilterPropertyChanged(Observable observable) {
            reload(); }
        
            addListener simply calls this method whenever stuff changes.
        */
        textfieldSearch.textProperty().addListener((Observable observable) -> {
            reload();
        });
        textfieldFilterImdb.textProperty().addListener((Observable observable) -> {
            reload();
        });
        
        reload();
        
        comboboxSelectCategoryFilter.getItems().addAll(observableListCategory);
    }
    
    public void reload() {
        try {
            tableViewMain.setItems(mmodel.getMovies(textfieldSearch.getText(), getImdbRating(), selectedCategories));
        } catch (SQLException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
    
    private double getImdbRating() {
        try {
                if(!textfieldFilterImdb.getText().matches("[0.-9.]*") || textfieldFilterImdb.getText() == null) {
                    MessageBoxHelper.displayError("You must write a number in minimum IMDB rating filter");
                    textfieldFilterImdb.clear();
                    return 0.0;
                }
                return Double.parseDouble(textfieldFilterImdb.getText());
        }
        catch(NumberFormatException ex) {
            return 0.0;
        }
    }
    
    @FXML
    private void editCategoriesWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/CategoryWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.setTitle("Editing categories");
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
    }

    @FXML
    private void addMovieWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.setTitle("Adding movie to database");
        stage.getIcons().add(new Image("/icon.png"));
        stage.showAndWait();
        
        reload();
    }
    
    @FXML
    private void editMovieWindow(ActionEvent event) throws IOException {
        Movie selectedMovie = tableViewMain.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            MessageBoxHelper.displayError("You must select a movie to edit.");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            MovieWindowController controller = fxmlLoader.getController();
            controller.setEditingMode(selectedMovie);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editing movie from the database.");
            stage.getIcons().add(new Image("/icon.png"));
            stage.showAndWait();

            reload();
        }
    }
    
    @FXML 
    private void deleteMovie(ActionEvent event) {
        try {
            Movie clickedMovie = tableViewMain.getSelectionModel().getSelectedItem();
            if(clickedMovie != null) {
                if(!MessageBoxHelper.askYesNo("Are you sure you want to delete the movie " + clickedMovie.getName() + "?")) {
                    return;
                }
                mmodel.deleteMovie(clickedMovie);
                reload();
            } else {
                MessageBoxHelper.displayError("You must select a movie to delete.");
            } 
        } catch(SQLException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
    
    @FXML
    public void resetFilter(ActionEvent event) {
        selectedCategories.clear();
        labelCategoryFilter.setText("-");
        reload();
    }
    
    @FXML
    public void addFilter(ActionEvent event) {
        Category selectedCategory = comboboxSelectCategoryFilter.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) {
            MessageBoxHelper.displayError("You have to select a category");
            return;
        }
        if (!selectedCategories.contains(selectedCategory)) {
            selectedCategories.add(selectedCategory);
            updateCategoryLabel();
            reload();
        } else {
            MessageBoxHelper.displayError("Category has already been added");
        } 
    }
    
    private void updateCategoryLabel() {
        String text = "";
        for (Category category : selectedCategories) {
            text += category.getName();
            if(category != selectedCategories.get(selectedCategories.size() - 1)) { // - 1 because .get uses zero-based index of the list
                text += ", ";
            }
        }
        labelCategoryFilter.setText(text);
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) textfieldSearch.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void playMovie(ActionEvent event) {
        try {
            Movie clickedMovie = tableViewMain.getSelectionModel().getSelectedItem();
            if (clickedMovie == null) {
                MessageBoxHelper.displayError("You must select a movie to play.");
            } else {
                File file = new File(clickedMovie.getFilelink());
                Desktop.getDesktop().open(file);    
            }
        } catch (IOException ex) {
            MessageBoxHelper.displayException(ex);
        } catch (IllegalArgumentException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
}
