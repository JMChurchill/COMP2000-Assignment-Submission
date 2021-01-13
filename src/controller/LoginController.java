package controller;

import model.AdminDataManager;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginController {
    private static boolean loggedIn = false;

    public boolean logIntoAccount(String userName, String password){
        //String hashedPassword = null;
        boolean userFound = false;

        try {
            String hashedPassword = hashPassword(password);
            //loop through array
            AdminDataManager adminDM = new AdminDataManager();
            userFound = adminDM.findUser(userName,hashedPassword);
            if (userFound){
                loggedIn = true;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return userFound;
    }
    public static void logOut(){
        //change login status to false
        loggedIn = false;
    }

    public static boolean isLoggedIn(){
        return loggedIn;
    }


    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return password;
    }

}
