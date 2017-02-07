package com.example.rere.practice.extendsandsuper;

import com.example.rere.practice.base.activity.TestBaseActivity;

import android.widget.LinearLayout;

/**
 * test <? extends T> and <? super T>
 *
 * Created by rere on 2017/1/24.
 */

public class TestExtendAndSuperActivity extends TestBaseActivity {

    @Override
    protected void addViews(LinearLayout layout) {
        super.addViews(layout);

        getButton(layout, "extend", v -> {
            // extend

            /*
            //upwrap this block comment to see which expression is not valib

            List<? extends Fruit> fruitExtendList = null;

            // fruitExtendList can be a Fruit list, WaterMelon list, Strawberry list
            fruitExtendList = new ArrayList<Base>();// not valid, because this list can get Fruit, but you add Base.
            fruitExtendList = new ArrayList<Fruit>();
            fruitExtendList = new ArrayList<WaterMelon>();
            fruitExtendList = new ArrayList<Strawberry>();
            fruitExtendList = new ArrayList<? extends Fruit>();// not vaild,


            // read from list
            // valid
            Fruit fruit = fruitExtendList.get(0);

            // not valid, u can only guarantee in the list, they are all Fruit,
            // they can be fruit, strawberry, melon
            // so u cannot reference it to Melon without cast.
            Melon melon = fruitExtendList.get(0);

            //  add to list

            // not valid, because it could be melon list
            fruitExtendList.add(new Fruit());

            // not valid, because it could be other type list
            fruitExtendList.add(new Melon());
            fruitExtendList.add(new Strawberry());
            fruitExtendList.add(new WaterMelon());

            fruitExtendList.add(new Base());

            // !! Basically we can say that we cannot write anything to a fruitExtendedList.

            // !! You can't add any object to List<? extends T>
            // because you can't guarantee what kind of List it is really pointing to,
            // so you can't guarantee that the object is allowed in that List.
            // The only "guarantee" is that you can only read from it and you'll get a T or subclass of  T.

            */
        });

        getButton(layout, "super", v -> {
            // super

            /*
            //upwrap this block comment to see which expression is not valib

            List<? super Melon> melonSuperList = null;
            melonSuperList = new ArrayList<Melon>();
            melonSuperList = new ArrayList<Fruit>();
            melonSuperList = new ArrayList<Base>();
            melonSuperList = new ArrayList<Object>();
            melonSuperList = new ArrayList<WaterMelon>();// not valid, can only be melon super class.

            // add to list
            melonSuperList.add(new Object());
            melonSuperList.add(new Base());
            melonSuperList.add(new Fruit());
            melonSuperList.add(new Melon());
            melonSuperList.add(new WaterMelon());

            Object object = melonSuperList.get(0);
            Melon melon = melonSuperList.get(0);//

            // !! To sum it up we can add Melon or its subclass in melonSuperList
            // !! and read only Object type object.

            */
        });

        getButton(layout, "PECS", v -> {
            // PECS
            /**
             * PECS
             Remember PECS: "Producer Extends, Consumer Super".
             "Producer Extends" - If you need a List to produce T values (you want to read Ts from the list), you need to declare it with ? extends T, e.g. List<? extends Integer>. But you cannot add to this list.
             "Consumer Super" - If you need a List to consume T values (you want to write Ts into the list), you need to declare it with ? super T, e.g. List<? super Integer>. But there are no guarantees what type of object you may read from this list.
             If you need to both read from and write to a list, you need to declare it exactly with no wildcards, e.g. List<Integer>.
             *
             * public class Collections {
                 public static <T> void copy(List<? super T> dest, List<? extends T> src)
                 {
                    for (int i=0; i<src.size(); i++)
                        dest.set(i,src.get(i));// src can't add
                 }
             }
             *
             *
             * **/

        });

        getButton(layout, "test public<U> MyOptional<U> map(Function<? super T, ? extends U> mapper)",  v -> {
            // test public<U> MyOptional<U> map(Function<? super T, ? extends U> mapper)

            /*
            //upwrap this block comment to see which expression is not valib

            Function<Fruit, Fruit> function = (fruit) -> fruit;

            Optional<Fruit> optional = Optional.of(new Fruit());

            Optional<String> optionalString;
            optionalString = optional.map(new Function<Object, String>() {
                @Override
                public String apply(Object o) {
                    return "";
                }
            });

            optionalString = optional.map(new Function<Base, String>() {
                @Override
                public String apply(Base o) {
                    return "";
                }
            });

            optionalString = optional.map(new Function<Fruit, String>() {
                @Override
                public String apply(Fruit o) {
                    return "";
                }
            });

            // the map function , can be Type Object, Base, Fruit.
            // i guess the map want to handle the Fruit with Object, Base, Fruit style.
            */
        });

    }


    private static class Base {

    }

    private static class Fruit extends Base {

        // sub class Melon / strawberry

    }

    private static class Strawberry extends Fruit {
    }

    private static class Melon extends Fruit {

        // sub class WaterMelon

    }

    private static class WaterMelon extends Melon {
    }


}
