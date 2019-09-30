package com.dziadkouskaya.findinshop.by.parsing.model.logic.validation;

public class Validation {
    public static final String EMPTY_STRING = " ";
    public static final int NULL_STRING = 0;

    public static boolean validateOnlySpaces(String string) {
        boolean result = true;
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.toString(string.charAt(i)).equals(EMPTY_STRING)) {
                counter += 1;
            }
        }

        if (counter == string.length()) {
            result = false;
        }

        return result;
    }
/*
    public static String validateRequest(String msg) {
        while (msg.length() == 0 || Validation.validateOnlySpaces(msg) == false) {
            msg = UserInput.inputString("Введите запрос еще раз: ");
        }

        return msg;
    }

}


 */
}
