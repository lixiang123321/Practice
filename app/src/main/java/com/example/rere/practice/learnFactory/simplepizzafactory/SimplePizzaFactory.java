package com.example.rere.practice.learnFactory.simplepizzafactory;

import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.learnFactory.pizza.CheesePizza;
import com.example.rere.practice.learnFactory.pizza.ClamPizza;
import com.example.rere.practice.learnFactory.pizza.PepperoniPizza;
import com.example.rere.practice.learnFactory.pizza.VeggiePizza;
import com.example.rere.practice.learnFactory.pizza.base.Pizza;

/**
 * Simple Factory
 *
 * Created by rere on 2017/6/16.
 */

public class SimplePizzaFactory {

    private static final String TAG = SimplePizzaFactory.class.getSimpleName();

    // static Factory method, weakness : cannot derive.
    public static Pizza createPizzaStatic(String type) {
        Pizza pizza = null;

        if ("cheese".equals(type)) {
            pizza = new CheesePizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new PepperoniPizza();
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza();
        } else if ("veggie".equals(type)) {
            pizza = new VeggiePizza();
        } else {
            TagLog.w(TAG, "no this type pizza");
        }
        return pizza;
    }

    // not static, can derive and modify.
    public Pizza createPizza(String type) {
        Pizza pizza = null;

        if ("cheese".equals(type)) {
            pizza = new CheesePizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new PepperoniPizza();
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza();
        } else if ("veggie".equals(type)) {
            pizza = new VeggiePizza();
        } else {
            TagLog.w(TAG, "no this type pizza");
        }
        return pizza;
    }

}
