package carsharing;

import carsharing.database.connect;
import carsharing.database.create;
import carsharing.database.sort;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public Menu() throws SQLException {
        do{
            login();
        }while(true);
    }

    private void login() throws SQLException {
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
        String choice = scanner.nextLine();
        if(choice.equals("1")){
            manager();
        }
        else if (choice.equals("2")){
            customer();
        }
        else if (choice.equals("3")){
            create.createCustomer();
        }
        else if (choice.equals("0")){
            connect.getConn().close();
            System.exit(0);
        }
    }

    private void manager() throws SQLException {
        while(true) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                //sort list of companies by IDs (1 as first)
                sort.sortByID();
            } else if (choice.equals("2")) {
                //create a company
                create.createCompany();
            } else if (choice.equals("0")) {
                break;
            }
        }
    }

    private void customer() throws SQLException {
        sort.customers();
    }
}
