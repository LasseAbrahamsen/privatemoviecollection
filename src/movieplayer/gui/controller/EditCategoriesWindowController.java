package movieplayer.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movieplayer.exceptions.CreateCategoryException;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.util.MessageBoxHelper;

/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class EditCategoriesWindowController implements Initializable {
    
    @FXML private TextField textfieldCategory;
    
    //fxml instance variables used for CSS
    @FXML private Button savebtn3;
    @FXML private Button cancelbtn3;
    
    CategoryModel cmodel = new CategoryModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void saveCategory(ActionEvent event) {
        try {
            
            String catName = textfieldCategory.getText();
            if(catName.trim().length() == 0) {
                MessageBoxHelper.displayError("Please enter a category name");
                return;
            }
            if (catName.length() < 128) {
                cmodel.createCategory(textfieldCategory.getText());
                close();
            } else {
                MessageBoxHelper.displayError("The name of the category must be less than 128 characters.");
            }
        } catch(SQLException ex) {
            MessageBoxHelper.displayException(ex);
        } catch (CreateCategoryException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
    
    @FXML
    private void close(ActionEvent event) {
        close();
    }

    private void close() {
        Stage stage = (Stage) textfieldCategory.getScene().getWindow();
        stage.close();
    }
    
}
