package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    static Connection conn;
    public connect(String filename) throws SQLException, ClassNotFoundException {
        Class.forName ("org.h2.Driver");
        conn = DriverManager.getConnection (
                "jdbc:h2:" +
                        "C:\\Users\\wh\\IdeaProjects\\" +
                        "Car Sharing1\\Car Sharing\\task\\" +
                        "src\\carsharing\\db\\" + filename);
        conn.setAutoCommit(true);
    }

    public static Connection getConn() {
        return conn;
    }
}
