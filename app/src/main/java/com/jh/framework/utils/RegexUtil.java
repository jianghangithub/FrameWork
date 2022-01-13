package com.jh.framework.utils;

public class RegexUtil {
    String regex = "^1...1";

    public static void main(String... args) {
        String regex = "^([1].{3}[1].*)?";
        String source = "0111111";
        boolean matches = source.matches(regex);
        System.out.println(matches);
    }
}
