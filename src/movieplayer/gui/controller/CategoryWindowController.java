/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieplayer.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import movieplayer.be.Category;
import movieplayer.gui.model.CategoryModel;

/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class CategoryWindowController implements Initializable {
    
    CategoryModel cmodel = new CategoryModel();

    @FXML
    private Label labelTitle;
    @FXML
    private ListView<Category> listviewCategories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listviewCategories.setItems(cmodel.getCategories());
    }
    
    @FXML
    private void newCategory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/EditCategoriesWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) labelTitle.getScene().getWindow();
        stage.close();
    }
    
    @FXML private void clickLoadList(ActionEvent event) {
        cmodel.loadAllCategories();
    }
    
}
