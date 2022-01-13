package com.jh.framework;

import java.math.BigDecimal;

public class Test {
    public static void main(String... args) {
        BigDecimal bigDecimal = new BigDecimal(1.1);
        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString());
    }
}
