package movieplayer.gui.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
public class MovieWindowController implements Initializable {

    @FXML private TextField textfieldMovieName;
    @FXML private TextField textfieldFileChosen;
    @FXML private Label labelCategories;
    @FXML private ComboBox<Integer> comboboxRating;
    @FXML private ComboBox<Category> comboboxAddCategory;
    @FXML private DatePicker datePickerLastSeen;
    
    private ArrayList<Category> selectedCategories;
    
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
        
        selectedCategories = new ArrayList<Category>();
        
        comboboxRating.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
        comboboxAddCategory.getItems().addAll(observableListCategory);
    }

    
    @FXML
    private void getFileLink(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop")); //Sets the directory to the desktop
        fileChooser.setTitle("Select movie (.mp4 or .mpeg4 file)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            textfieldFileChosen.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void saveMovie(ActionEvent event) {
        try {
            String movieName = textfieldMovieName.getText().trim();
            if(movieName.length() == 0 || movieName.length() >= 50 || movieName == null) {
                MessageBoxHelper.displayError("The movie name must not be empty and must be shorter than 50 characters.");
                return;
            }
            if(textfieldFileChosen.getText() == null || textfieldFileChosen.getText().length() == 0) {
                MessageBoxHelper.displayError("The filepath name must not be empty.");
                return;
            }
            if (!isEditing) {
                mmodel.createMovie(movieName, Integer.parseInt(comboboxRating.getSelectionModel().getSelectedItem().toString()),
                        selectedCategories, textfieldFileChosen.getText(), datePickerLastSeen.getValue()); 
            } else {
                //mmodel.updateMovie(movieName, movieName, Integer.parseInt(comboboxRating.getSelectionModel().getSelectedItem().toString()), textfieldFileChosen.getText());
            }
            close();
        } catch (SQLException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
    
    void setEditingMode(Movie selectedMovie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    public void resetCategories(ActionEvent event) {
        selectedCategories.clear();
        labelCategories.setText("-");
    }
    
    @FXML
    public void addCategory(ActionEvent event) {
        Category selectedCategory = comboboxAddCategory.getSelectionModel().getSelectedItem();
        if (!selectedCategories.contains(selectedCategory)) {
            selectedCategories.add(selectedCategory);
            String text = "";
            for (Category category : selectedCategories) {
                text += category.getName();
                if(category != selectedCategories.get(selectedCategories.size() - 1)) { // - 1 because .get uses zero-based index of the list
                    text += ", ";
                }
            }
            labelCategories.setText(text);
        } else {
            MessageBoxHelper.displayError("Category has already been added");
        } 
    }
    
    @FXML
    private void close(ActionEvent event) {
        close();
    }
    
    private void close() {
        Stage stage = (Stage) labelCategories.getScene().getWindow();
        stage.close();
    }
}
