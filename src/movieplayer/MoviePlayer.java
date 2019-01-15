package movieplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import movieplayer.gui.controller.MainWindowController;
import movieplayer.gui.model.MovieModel;
import movieplayer.gui.util.MessageBoxHelper;


public class MoviePlayer extends Application {
    
    MovieModel mmodel = new MovieModel();
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movieplayer/gui/view/MainWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainWindowController controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Private Movie Collection");
        stage.getIcons().add(new Image("/icon.png"));
        stage.show();
        
        if (mmodel.getObsoleteMovies().size() >= 1) {
            if(!MessageBoxHelper.askYesNo("Do you want to delete the obsolete movies " + mmodel.getObsoleteMovies() + "?")) {
                    return;
            }
            mmodel.deleteObsoleteMovies();
            controller.reload();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
