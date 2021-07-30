package carsharing.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class delete {
    public static void deleteTable() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            st.executeUpdate("DROP TABLE COMPANY");
            System.out.println("The Table was deleted!");
        }catch (Exception ignored){}
        connect.getConn().close();
    }
    public static void deleteCompanyByID() throws SQLException {
        Statement st = connect.getConn().createStatement();
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        try {
            st.executeUpdate(
                    "DELETE FROM COMPANY WHERE ID = "+id+";");
            System.out.println("The company was deleted!");
        }catch (Exception ignored){}
        connect.getConn().close();
    }
}
