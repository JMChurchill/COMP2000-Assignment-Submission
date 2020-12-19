package controller;

import model.AdminUser;
import model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminDataManager {
    public String filePath = "resources/AdminData.txt";
    public String separator = "\\|";

    private final ArrayList<AdminUser> admins = new ArrayList<>();

    //get data from AdminData

    public void load(){
        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();

                String[] adminData = data.split(separator);

                AdminUser adminUser = new AdminUser();

                int adminIdToInt = Integer.parseInt(adminData[0]);
                adminUser.setAdminId(adminIdToInt);

                adminUser.setUserName(adminData[1]);

                adminUser.setPassword(adminData[2]);

                admins.add(adminUser);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(filePath);

            for (int i=0; i< admins.size(); i++){
                String data = "";

                if (i>0){
                    data += "\n";
                }

                String AdminIdToString = Integer.toString(admins.get(i).getAdminId());
                data += AdminIdToString;

                data += admins.get(i).getUserName();

                data += admins.get(i).getPassword();

                writer.write(data);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AdminUser getAdminAt(int index){
        if (index >= admins.size()){
            return null;
        }
        return admins.get(index);
    }

    public void addAdmin(AdminUser newAdmin){
        admins.add(newAdmin);
    }

    public void removeUser(AdminUser oldAdmin){
        admins.remove(oldAdmin);
    }
}
