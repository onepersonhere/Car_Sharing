package carsharing.database;

import carsharing.CustomerMenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class sort {
    public static void sortByID() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM COMPANY");
            Map<String, Integer> map = new LinkedHashMap<>();
            while(rs.next()){
                int comIdx = rs.getInt(1);
                String comName = rs.getString(2);
                map.put(comName, comIdx);
            }
            printComList(map);
        }catch (Exception ignored){}
    }

    public static void printComList(Map<String, Integer> map) throws SQLException {
        if(map.size()>0) {
            System.out.println("Choose a company:");
            List<String> list = new ArrayList<>();
            int i = 1;
            for(Map.Entry<String, Integer> entry : map.entrySet()){
                System.out.println(i + ". " + entry.getKey());
                list.add(entry.getKey());
                i++;
            }
            System.out.println("0. Back");
            chooseCompany(list, map);
        }else{
            System.out.println("The company list is empty!");
        }

    }

    public static void chooseCompany(List<String> list, Map<String, Integer> map) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int idx = Integer.parseInt(scanner.nextLine());
        if(idx != 0){
            String comName = list.get(idx - 1);
            int comIdx = map.get(comName);
            while (true) {
                System.out.println(comName + " company:\n" +
                        "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    //car list of com
                    getComCar(comName, comIdx);
                } else if (choice == 2) {
                    //create the car for com
                    create.createCar(comIdx);
                } else if (choice == 0) {
                    break;
                }
            }
        }
    }

    public static void getComCar(String comName, int comIdx) throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM CAR WHERE COMPANY_ID = " + comIdx);
            List<String> list = new ArrayList<>();
            while(rs.next()){
                String carName = rs.getString(2);
                list.add(carName);
            }
            printCarList(list, comName);
        }catch (Exception ignored){}
    }

    public static void printCarList(List<String> list, String comName){
        if(list.size()>0) {
            System.out.println(comName + " cars:");
            for (int i = 1; i < list.size() + 1; i++) {
                System.out.println(i + ". " + list.get(i - 1));
            }
            System.out.println();
        }else{
            System.out.println("The car list is empty!");
        }
    }

    public static void customers() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER");
            Map<String, Integer> map = new LinkedHashMap<>();
            while(rs.next()){
                int cusIdx = rs.getInt(1);
                String cusName = rs.getString(2);
                map.put(cusName, cusIdx);
            }
            if(map.isEmpty()){
                System.out.println("The customer list is empty!");
            }else {
                getCustomer(map);
            }
        }catch (Exception ignored){}
    }

    private static void getCustomer(Map<String, Integer> map) throws SQLException {
            int i = 1;
            List<String> list = new ArrayList<>();
            System.out.println("Choose a customer:");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(i + ". " + entry.getKey());
                list.add(entry.getKey());
                i++;
            }
            System.out.println("0. Back");

            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice != 0) {
                new CustomerMenu(map, list.get(choice - 1));
            }
    }
}
