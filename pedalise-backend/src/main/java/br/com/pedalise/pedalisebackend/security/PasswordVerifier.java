package br.com.pedalise.pedalisebackend.security;

import org.antlr.v4.runtime.misc.NotNull;

public class PasswordVerifier {

    private final char[] symbolsPattern = "!@#$%¨&*()-=+".toCharArray();
    private final char[] lettersPattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final int minLength = 6;
    private final int maxLength = 20;
    private final int minSymbols = 1;
    private final int minLetters = 1;

    public boolean verify(@NotNull String password) {
        if (!chechIfhasWhiteSpaces(password))
            return false;

        if (!checkIfHasMinLength(password))
            return false;

        if (checkIfHasMaxLength(password))
            return false;

        if (getPatternCount(symbolsPattern, password) < minSymbols)
            return false;

        if (getPatternCount(lettersPattern, password) < minLetters)
            return false;

        return true;
    }

    private boolean chechIfhasWhiteSpaces(@NotNull String password) {
        for (int i = 0; i < password.length(); i++) {
            if(password.charAt(i) == ' '){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfHasMinLength(@NotNull String password) {
        return password.length() >= minLength;
    }

    private boolean checkIfHasMaxLength(@NotNull String password) {
        return maxLength > 0 && password.length() >= maxLength;
    }

    private int getPatternCount(char[] pattern, @NotNull String password) {
        return (int) password.chars().mapToObj(it -> (char) it).filter(it -> equals(it, pattern)).count();
    }

    private boolean equals(char character, char[] array) {
        for (char element : array)
            if (element == character)
                return true;

        return false;
    }
}
