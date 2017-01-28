package com.example.rere.practice.java8;

/**
 * test functionInterface : exactly one abstract method
 *
 * Created by rere on 2017/1/20.
 */

@FunctionalInterface
public interface Converter<F, T> {

    T convert(F from);

}
