package com.example.focusedhubapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;


public class Security {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static byte[] obtainSHA256(String s) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        BigInteger h = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(h.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');

        }

        return hexString.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String ConvertToHash(String s) throws NoSuchAlgorithmException {
        System.out.println("Conversion taking place");

        s = toHexString(obtainSHA256(s));

        return s;
    }


}
