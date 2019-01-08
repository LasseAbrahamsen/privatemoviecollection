package movieplayer.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a
 */
public class DBConnect {
    
        public List<String> getDatabaseInfo() throws IOException {
        List<String> loginInfo = new ArrayList();
        String source = "data/loginInfo.txt";
        File file = new File(source);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    try {
                        loginInfo.add(line);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
        return loginInfo;
    }
    
}
