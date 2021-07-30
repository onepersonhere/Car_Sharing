package carsharing;

import carsharing.database.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CustomerMenu {
    String cusName;
    int cusID;
    public CustomerMenu(Map<String, Integer> map, String key) throws SQLException {
        cusName = key;
        cusID = map.get(key);

        while(true) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                //rent car
                rentCar();
            } else if (choice.equals("2")) {
                //return car
                returnCar();
            } else if (choice.equals("3")) {
                //rent list
                listCar();
            } else if (choice.equals("0")) {
                break;
            }
        }
    }

    private void rentCar() throws SQLException {
        //choose a company
        Statement st = connect.getConn().createStatement();
        ResultSet rs = st.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + cusID);
        int id = 0;
        if(rs.next()){
            id = rs.getInt(1);
        }
        if(id == 0){
            getCom();
        }else{
            System.out.println("You've already rented a car!");
        }
    }

    private void returnCar() throws SQLException {
        Statement st = connect.getConn().createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = "+cusID);
        int id = 0;
        if(rs.next()){
            id = rs.getInt(1);
        }
        if(id != 0){
            //see if it triggers any error such that id == null
            st.executeUpdate(
                    "UPDATE CUSTOMER SET RENTED_CAR_ID=NULL WHERE ID = " + cusID);
            System.out.println("You've returned a rented car!");
        }else{
            System.out.println("You didn't rent a car!");
        }
    }

    private void listCar() throws SQLException {
        Statement st = connect.getConn().createStatement();
        try {
            //get associated "cars" id
            ResultSet rs = st.executeQuery(
                    "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = "+cusID);
            List<Integer> list = new ArrayList<>(); //assume list is a singleton
            Map<String, Integer> map = new LinkedHashMap<>();
            while(rs.next()){
                int carID = rs.getInt(1);
                list.add(carID);
            }

            //get car and its com
            for(int i = 0; i < list.size(); i++) {
                rs = st.executeQuery(
                        "SELECT * FROM CAR WHERE ID = " + list.get(i));
                if(rs.next()){
                    String name = rs.getString(2);
                    int comID = rs.getInt(3);
                    map.put(name, comID);
                }
            }
            if(!map.isEmpty()) {
                System.out.println("Your rented car:");
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    System.out.println(entry.getKey());
                    System.out.println("Company:");
                    rs = st.executeQuery("SELECT NAME FROM COMPANY WHERE ID = " + entry.getValue());
                    if(rs.next()){
                        System.out.println(rs.getString(1));
                    }
                    System.out.println();
                }
            }else{
                System.out.println("You didn't rent a car!");
            }

        }catch (Exception ignored){}
    }


    private void getCom() throws SQLException {
        Statement st = connect.getConn().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM COMPANY");
        Map<String, Integer> map = new LinkedHashMap<>();
        while(rs.next()){
            int comIdx = rs.getInt(1);
            String comName = rs.getString(2);
            map.put(comName, comIdx);
        }
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

    private void chooseCompany(List<String> list, Map<String, Integer> map) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        /*
        Choose a company:
        1. Car To Go
        2. Drive Now
        0. Back
        */
        int idx = Integer.parseInt(scanner.nextLine());
        if(idx != 0) {
            String comName = list.get(idx - 1);
            int comIdx = map.get(comName);
            Statement st = connect.getConn().createStatement();
            try {
                ResultSet rs = st.executeQuery("SELECT * FROM CAR WHERE COMPANY_ID = " + comIdx);
                List<String> newList = new ArrayList<>();
                map = new LinkedHashMap<>();
                while(rs.next()){
                    int carID = rs.getInt(1);
                    String carName = rs.getString(2);
                    boolean bool = checkIfCarIsRented(carID);
                    if(!bool) {
                        map.put(carName, carID);
                        newList.add(carName);
                    }
                }
                if(!map.isEmpty()) {
                    chooseCar(newList, map);
                }else{
                    System.out.println("No available cars in the "+comName+" company.");
                }
            }catch (Exception ignored){}
        }
    }

    private void chooseCar(List<String> list, Map<String, Integer> map) throws SQLException {
        Statement st = connect.getConn().createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a car:");
        for (int i = 1; i < list.size() + 1; i++) {
            System.out.println(i + ". " + list.get(i - 1));
        }

        int choice = Integer.parseInt(scanner.nextLine());
        if(choice != 0){
            for(Map.Entry<String, Integer> entry : map.entrySet()){
                if(choice == 1) {
                    st.executeUpdate(
                            "UPDATE CUSTOMER SET RENTED_CAR_ID='"
                                    + entry.getValue() + "' WHERE ID = " + cusID);
                    System.out.println("You rented '" + entry.getKey() + "'");
                }
                choice--;
            }
        }
    }

    private boolean checkIfCarIsRented(int carID) throws SQLException {
        Statement st = connect.getConn().createStatement();
        ResultSet rs = st.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER");
        int id = 0;
        while(rs.next()){
            id = rs.getInt(1);
            if(carID == id){
                return true;
            }
        }
        return false;
    }
}
