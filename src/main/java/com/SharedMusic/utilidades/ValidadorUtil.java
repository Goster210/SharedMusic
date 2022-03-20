package com.SharedMusic.utilidades;


public class ValidadorUtil {
    public static Float priceConvert(String price) {
        try {

            return Float.parseFloat(price.replace("$", "").trim().replace(".", ""));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(price + "  \t " + e.getMessage());
            throw e;
        }
    }
}
