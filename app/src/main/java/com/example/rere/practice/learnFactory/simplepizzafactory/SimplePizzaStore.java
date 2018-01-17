package com.example.rere.practice.learnFactory.simplepizzafactory;

import com.example.rere.practice.learnFactory.pizza.base.Pizza;

import android.support.annotation.NonNull;

/**
 * Created by rere on 2017/6/16.
 */

public class SimplePizzaStore {

    private final SimplePizzaFactory mSimplePizzaFactory;

    public SimplePizzaStore(@NonNull SimplePizzaFactory simplePizzaFactory) {
        mSimplePizzaFactory = simplePizzaFactory;
    }

    public Pizza orderPizza(String type) {

        Pizza pizza = mSimplePizzaFactory.createPizza(type);

        pizza.perpare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }


}
