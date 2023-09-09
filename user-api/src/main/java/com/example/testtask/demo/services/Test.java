package com.example.testtask.demo.services;

public class Test {
    public static void main(String[] args) {
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <=12; j++) {
                if (j == i || i > j) {
                    continue;
                }

                System.out.println(j);
            }
        }
    }
}
