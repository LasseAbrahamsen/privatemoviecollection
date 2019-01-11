package movieplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MoviePlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/movieplayer/gui/view/MainWindow.fxml"));
        Scene scene = new Scene(root);
        /*String css = MoviePlayer.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);*/
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
