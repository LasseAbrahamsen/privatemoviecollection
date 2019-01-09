package movieplayer.gui.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import movieplayer.be.Category;
import movieplayer.be.Movie;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.model.MovieModel;

/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class MovieWindowController implements Initializable {

    @FXML private TextField textfieldMovieName;
    @FXML private TextField textfieldFileChosen;
    @FXML private Label labelCategories;
    @FXML private ComboBox<Integer> comboboxRating;
    @FXML private ComboBox<Category> comboboxAddCategory;
    
    private boolean isEditing = false;
    private Movie movie;
    
    MovieModel mmodel = new MovieModel();
    CategoryModel cmodel = new CategoryModel();
    private ObservableList<Category> observableListCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListCategory = cmodel.getCategories();
        
        comboboxRating.getItems().addAll(0,1,2,3,4,5);
        comboboxAddCategory.getItems().addAll(observableListCategory);
    }

    
    @FXML
    private void getFileLink(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop")); //Sets the directory to the desktop
        fileChooser.setTitle("Select movie (.mp4 or mpeg4 file)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            textfieldFileChosen.setText(selectedFile.getAbsolutePath());
        }
    }
    /*
    private void saveMovie(ActionEvent event) {
        String movieName = textfieldMovieName.getText().trim();
         if (movieName.length() > 0 && movieName.length() < 50 && movieName != null && textfieldFileChosen.getText() != null && textfieldFileChosen.getText().length() != 0) {
             if (!isEditing)
                mmodel.createMovie(movieName, Integer.parseInt(sliderRating.getValue()), textfieldFileChosen.getText()); 
         } else {
                mmodel.updateMovie(movie, movieName, Integer.parseInt(comboboxRating.getSelectionModel().getSelectedItem().toString()), textfieldFileChosen.getText());
         }
    }*/
    
    void setEditingMode(Movie selectedMovie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addCategoryToLabel(ActionEvent event) {
        Category selectedCategory = comboboxAddCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            labelCategories.setText(selectedCategory.toString());
        }
    }
    
}
