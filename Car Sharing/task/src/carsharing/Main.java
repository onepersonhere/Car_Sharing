package carsharing;

import carsharing.database.connect;
import carsharing.database.create;

public class Main {

    public static void main(String[] args) throws Exception {
        String filename = "carsharing";
        for(int i = 0; i < args.length; i+=2){
            if(args[i].equals("-databaseFileName")){
                filename = args[i+1];
            }
        }
        new connect(filename);
        create.createCompanyTable();
        create.createCarTable();
        create.createCustomerTable();

        new Menu();

        connect.getConn().close();
    }
}