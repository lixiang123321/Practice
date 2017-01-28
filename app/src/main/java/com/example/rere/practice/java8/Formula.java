package com.example.rere.practice.java8;

/**
 * Formula : default method interface
 *
 * Created by rere on 2017/1/20.
 */

public interface Formula {

    double calculate(int a);

    // default method required api 24
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

}
