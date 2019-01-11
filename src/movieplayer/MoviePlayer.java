package movieplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import movieplayer.gui.controller.MainWindowController;
import movieplayer.gui.model.MovieModel;
import movieplayer.gui.util.MessageBoxHelper;


public class MoviePlayer extends Application {
    
    MovieModel mmodel = new MovieModel();
    MainWindowController mwCtrl = new MainWindowController();
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/MainWindow.fxml"));
        Scene scene = new Scene(root);
        /*String css = MoviePlayer.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);*/
        stage.setScene(scene);
        stage.show();
        
        if (mmodel.getObsoleteMovies().size() >= 1) {
            if(!MessageBoxHelper.askYesNo("Do you want to delete the obsolete movies " + mmodel.getObsoleteMovies() + "?")) {
                    return;
                }
            mmodel.deleteObsoleteMovies();
            mwCtrl.reload();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
