package controller;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class LoginController {
    private static boolean loggedIn = false;
//    private static final String UNICODE_FORMAT = "UTF-8";



//    public LoginController(String UserName, String Password) throws InvalidKeySpecException, NoSuchAlgorithmException {
////        this.userName = UserName;
////        this.password = hashPassword(Password);
//        logIntoAccount(UserName,Password);
//
//    }

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
    public void logOut(){
        //change login status to false
        loggedIn = false;
    }


    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        //salt = "1".getBytes(StandardCharsets.UTF_8);
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 6553, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
//        JOptionPane.showMessageDialog(null, hashedPassword);
//        JOptionPane.showMessageDialog(null, "salt: "+ salt);
//
//        return hashedPassword.toString();
        return password;


//        try {
//            SecretKey key = generateKey("AES");
//            Cipher cipher;
//            cipher = Cipher.getInstance("AES");
//
//            byte[] encryptedData = encryptString(password, key, cipher);
//            String encryptedString = new String (encryptedData);
//            JOptionPane.showMessageDialog(null, encryptedString);
//            JOptionPane.showInputDialog(null, encryptedString,encryptedString);
//            return encryptedString;
//
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//            return null;
//        }



    }

//    public static SecretKey generateKey(String encryptionType){
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionType);
//            SecretKey myKey = keyGenerator.generateKey();
//            return myKey;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static byte[] encryptString(String dataToEncrypt, SecretKey myKey, Cipher cipher){
//        try {
//            byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
//            cipher.init(Cipher.ENCRYPT_MODE,myKey);
//            byte[] textEncrypted = cipher.doFinal(text);
//
//            return textEncrypted;
//        } catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String decryptString(byte[] dataToDecrypt, SecretKey myKey,Cipher cipher){
//        try {
//            cipher.init(Cipher.DECRYPT_MODE,myKey);
//            byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
//            String result = new String(textDecrypted);
//
//            return result;
//        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//
//    }

}
