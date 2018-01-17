package com.example.rere.practice.learnFactory.pizzastore.base;

import com.example.rere.practice.learnFactory.pizza.base.Pizza;

/**
 * Factory Method
 *
 * Created by rere on 2017/6/16.
 */

public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.perpare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    // Factory Method : subClass decide what product created.
    protected abstract Pizza createPizza(String type);

}
