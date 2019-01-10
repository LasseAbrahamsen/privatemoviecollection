package movieplayer.gui.util;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageBoxHelper {
    
    public static boolean askYesNo(String prompt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(prompt);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }
    
    public static void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("An error has occurred: " + errorMessage);
        alert.showAndWait();
    }
    
    public static void displayException(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("An error has occurred: " + getErrorMessage(ex));
        alert.showAndWait();
    }
    
    private static String getErrorMessage(Exception ex) {
        if(ex instanceof SQLServerException) {
            SQLServerException e = (SQLServerException)ex;
            // 2627 => Unique key violated
            return String.valueOf(e.getErrorCode());
        }
        return ex.getLocalizedMessage();
    }
}
