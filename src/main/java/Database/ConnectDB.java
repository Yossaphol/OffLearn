package Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectDB {

    private static final Dotenv nev = Dotenv.load();
    private static final String url = nev.get("DB_URL");
    private static final String user = nev.get("DB_USER");
    private static final String  password = nev.get("DB_PASS");
    private Connection connection;

    public Connection connectToDB(){
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract  void saveToDB();

    public abstract void deleteFromDB();
}
