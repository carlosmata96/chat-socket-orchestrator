/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author mata
 */
public class Encriptaciones {
    
    // Encoder para SHA-256
     public static String encrypt(String val) {
        String hash = null; 
        
        try {
            //Encriptando contraseña ingresada
            MessageDigest dig = MessageDigest.getInstance("SHA-256");
            dig.update(val.getBytes(StandardCharsets.UTF_8));
//            String hash = Base64.getEncoder().encodeToString( dig.digest() );
            hash = (new HexBinaryAdapter()).marshal(dig.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            return hash;
        }
        
        return hash;
    }
     
     public static int generateRamdomNumber(int minimo,int maximo){
        
       int num=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
       return num;
   }
     
}
