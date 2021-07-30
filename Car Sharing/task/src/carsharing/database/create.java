package carsharing.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class create {
    public static void createCompanyTable() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            st.executeUpdate("CREATE TABLE COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR UNIQUE NOT NULL" +
                    ")");
            System.out.println("The Table was created!");
        }catch (Exception ignored){}
    }

    public static void createCarTable() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            st.executeUpdate("CREATE TABLE CAR (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR UNIQUE NOT NULL," +
                    "COMPANY_ID INT NOT NULL," +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    ")");
            System.out.println("The Table was created!");
        }catch (Exception ignored){}
    }


    public static void createCustomerTable() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            st.executeUpdate("CREATE TABLE CUSTOMER (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR UNIQUE NOT NULL," +
                    "RENTED_CAR_ID INT," +
                    "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                    ")");
            System.out.println("The Table was created!");
        }catch (Exception ignored){}
    }

    public static void createCompany() throws SQLException {
        Statement st = connect.getConn().createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine();
        try {
            st.executeUpdate("INSERT INTO COMPANY(NAME) "+
                    "VALUES ('"+companyName+"')");
            System.out.println("The company was created!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createCar(int comIdx) throws SQLException {
        Statement st = connect.getConn().createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        try {
            st.executeUpdate("INSERT INTO CAR(NAME, COMPANY_ID) "+
                    "VALUES ('"+carName+"', "+comIdx+")");
            System.out.println("The car was added!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createCustomer() throws SQLException {
        Statement st = connect.getConn().createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();
        try {
            st.executeUpdate("INSERT INTO CUSTOMER(NAME) "+
                    "VALUES ('"+name+"')");
            System.out.println("The customer was added!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getLatestID(Statement st) throws SQLException {
        return st.executeQuery("SELECT MAX(ID) FROM COMPANY")
                .getInt(1); //1st column
    }
}
