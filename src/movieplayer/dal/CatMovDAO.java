package movieplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a
 */
public class CatMovDAO {
    
        SQLServerDataSource ds;
    
        public CatMovDAO() throws IOException {
        this.ds = new SQLServerDataSource();
        DBConnect connectionInfo = new DBConnect(); 
        List<String> loginInfo; 
        try {
            loginInfo = connectionInfo.getDatabaseInfo();
            ds.setDatabaseName(loginInfo.get(0));
            ds.setUser(loginInfo.get(1));
            ds.setPassword(loginInfo.get(2));
            ds.setPortNumber(Integer.parseInt(loginInfo.get(3)));
            ds.setServerName(loginInfo.get(4));
        } catch (IOException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
