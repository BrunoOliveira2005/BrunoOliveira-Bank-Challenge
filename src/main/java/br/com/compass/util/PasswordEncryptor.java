package br.com.compass.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    
    public static boolean matches(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}