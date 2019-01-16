package movieplayer.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import movieplayer.be.Category;
import movieplayer.gui.model.CategoryModel;
import movieplayer.gui.util.MessageBoxHelper;

public class CategoryWindowController implements Initializable {
    
    CategoryModel cmodel = new CategoryModel();

    @FXML private Label labelTitle;
    @FXML private ListView<Category> listviewCategories;
    //fxml instance variables used for css
    @FXML private Button addCatbtn;
    @FXML private Button deleteCatbtn;
    @FXML private Button cancelbtn4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reload();
    }
    
    @FXML
    private void newCategoryWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/EditCategoriesWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.setTitle("Adding category");
        stage.getIcons().add(new Image("/icon.png"));
        stage.showAndWait();
        reload();
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) labelTitle.getScene().getWindow();
        stage.close();
    }
    
    @FXML 
    private void deleteCategory(ActionEvent event) {
        try {
            Category clickedCategory = listviewCategories.getSelectionModel().getSelectedItem();
            if(clickedCategory != null) {
                if(!MessageBoxHelper.askYesNo("Are you sure you want to delete the category " + clickedCategory.getName() + "?")) {
                    return;
                }
                cmodel.deleteCategory(clickedCategory);
                reload();
            } else {
                MessageBoxHelper.displayError("You must select a category.");
            } 
        } catch(SQLException ex) {
            MessageBoxHelper.displayException(ex);
        }
    }
    
    private void reload() {
        listviewCategories.setItems(cmodel.getCategories());
    }
}
