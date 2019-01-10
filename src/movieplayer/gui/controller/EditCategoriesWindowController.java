/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieplayer.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movieplayer.be.Category;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.util.MessageBoxHelper;

/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class EditCategoriesWindowController implements Initializable {
    
    @FXML private TextField textfieldCategory;
    
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
            if (catName.length() < 128) {
                cmodel.createCategory(textfieldCategory.getText());
                close();
            } else {
                MessageBoxHelper.displayError("The name of the category must be less than 128 characters.");
            }
        } catch(SQLException ex) {
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
