package com.gama.mutamer.helpers;


/***
 * Helper To Convert English Numbers int String to Arabic Numbers
 */
public class ArabicNumberHelper {

    public String ReplaceToArabicNumber(String input) {
        Character[] EnglishNumbers = new Character[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        Character[] ArabicNumbers = new Character[]{'١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩', '٠'};
        for (int i = 0; i < EnglishNumbers.length; i++) {
            input = input.replace(EnglishNumbers[i], ArabicNumbers[i]);
        }
        return input;
    }
}
