/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieplayer.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import movieplayer.be.Category;
import movieplayer.gui.model.CategoryModel;

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
        cmodel.createCategory(textfieldCategory.getText());
    }

    
    
}
