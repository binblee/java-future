package com.example.java8;

/**
 * Created by libin on 3/21/16.
 */
public interface Formula {
    int calculate(int a);

    default int abs(int a){
        return Math.abs(a);
    }
}
