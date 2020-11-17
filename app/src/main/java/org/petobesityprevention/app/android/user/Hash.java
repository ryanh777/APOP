package org.petobesityprevention.app.android.user;

import android.util.Log;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hash {

    // Compare password
    // Deprecated function, username and password are hashed and the existence of the file is enough to verify
    public static Boolean validatePasswordHash(String enteredPassword, String storedPasswordHash) {

        Log.i("APOPapp", "Checking hashed Password: <" + enteredPassword + ">");
        Log.i("APOPapp", "Stored: <" + storedPasswordHash + ">");
        Log.i("APOPapp", "Enterd: <" + hashPassword(enteredPassword) + ">");

        try {
            //Get the sections from the stored password
            String[] parts = storedPasswordHash.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            // hash the entered info with the same spec
            PBEKeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            // Compare
            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++)
            {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        } catch (NoSuchAlgorithmException n) {
            Log.e("APOPApp", "Hashing algorithm error", n);
        } catch (InvalidKeySpecException i) {
            Log.e("APOPApp", "Hashing Key error", i);
        }
        // if there's an error just send back null and the org will be unauthorized
        return null;
    }

    // Hash username and password
    public static String hashPassword(String password) {
         try {
             // Strength of algorithm will change these fields
             int iterations = ThreadLocalRandom.current().nextInt(25, 100);;
             byte[] salt = getSalt(4);
             int keyBytes = 32;

             // hash the info
             PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyBytes * 8);
             SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
             byte[] hash = skf.generateSecret(spec).getEncoded();

             // format to store
             return iterations + ":" + toHex(salt) + ":" + toHex(hash);
         } catch (NoSuchAlgorithmException n) {
             Log.e("APOPApp", "Hashing algorithm error", n);
         } catch (InvalidKeySpecException i) {
             Log.e("APOPApp", "Hashing Key error", i);
         }
         // if error
         return null;
    }

    private static byte[] getSalt(int size) throws NoSuchAlgorithmException
    {
        byte[] salt = new byte[size];
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
