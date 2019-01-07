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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import movieplayer.be.Movie;

/**
 * FXML Controller class
 *
 * @author youPCbr0
 */
public class MainWindowController implements Initializable {

    @FXML
    private TableView<Movie> tableViewMain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void editCategories(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/CategoryWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addMovie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editMovie(ActionEvent event) throws IOException {
        Movie selectedMovie = tableViewMain.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movieplayer/gui/view/MovieWindow.fxml"));
        MovieWindowController controller = fxmlLoader.getController();
        controller.setEditingMode(selectedMovie);
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    
}
