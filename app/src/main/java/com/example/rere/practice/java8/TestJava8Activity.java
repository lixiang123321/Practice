package com.example.rere.practice.java8;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static android.os.Build.VERSION_CODES.N;

/**
 * test java 8 character
 *
 * Created by rere on 2017/1/20.
 */

public class TestJava8Activity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestJava8Activity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "default method interface : Formula", v -> {
            // default method interface : Formula
            Formula formula = new Formula() {
                @Override
                public double calculate(int a) {
                    return sqrt(a * 100);
                }
            };
            TagLog.i(TAG, " formula.calculate(100) = " + formula.calculate(100) + ",");// 100
            TagLog.i(TAG, " formula.sqrt(16) = " + formula.sqrt(16) + ",");// 4
        });

        getButton(layout, "lambda", v -> {
            // lambda
            List<String> stringList = Arrays.asList("abc", "cde", "def", "efg");

            Collections.sort(stringList, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    return rhs.compareTo(lhs);
                }
            });

            Collections.sort(stringList, (String lhs, String rhs) -> {
                return rhs.compareTo(lhs);
            });

            Collections.sort(stringList, (String lhs, String rhs) -> rhs.compareTo(lhs));

            Collections.sort(stringList, (lhs, rhs) -> rhs.compareTo(lhs));
        });

        getButton(layout, "@FunctionalInterface : exactly one abstract method",
                v -> {
                    // @FunctionalInterface
                    Converter<String, Integer> converter = new Converter<String, Integer>() {
                        @Override
                        public Integer convert(String from) {
                            return Integer.valueOf(from);
                        }
                    };
                    converter = (from) -> Integer.valueOf(from);
                    Integer converterToInteger = converter.convert("123");
                    TagLog.i(TAG, " converterToInteger = " + converterToInteger + ",");// 123

                    converter = (from) -> Integer.valueOf(from);

                    // reference to static method
                    converter = Integer::valueOf;
                });

        getButton(layout, "reference object method", v -> {
            // reference object method
            TestReferenceMethod testReferenceMethod = new TestReferenceMethod();
            Converter<String, String> converter = testReferenceMethod::startWith;
            TagLog.i(TAG, converter.convert("Java"));// J
        });

        getButton(layout, "reference constructor method", v -> {
            // reference constructor method
            PersonFactory<Person> personFactory = new PersonFactory<Person>() {
                @Override
                public Person create(String firstName, String lastName) {
                    return new Person(firstName, lastName);
                }
            };
            Person person = personFactory.create("Tim", "Duncan");
            TagLog.i(TAG, " person = " + person + ",");// tim duncan

            PersonFactory<Person> personFactory2
                    = Person::new;//the compiltor will find the match constructor
            Person person2 = personFactory2.create("Tim", "Duncan");
            TagLog.i(TAG, " person2 = " + person2 + ",");//tim duncan

        });

        getButton(layout, "lambda scopes", new View.OnClickListener() {

            // field can be read or write in lambda and anonymous object
            private int fieldIntVar = 1;

            @Override
            public void onClick(View v) {
                // lambda scopes
                int num = 1;// no need final
                Converter<Integer, String> converter = (from) -> String.valueOf(from + num);
                converter.convert(4);// 5
                // num++;//compiler failed, num accutually final

                final int num2 = 2;// no need final
                Converter<Integer, String> converter2 = new Converter<Integer, String>() {
                    @Override
                    public String convert(Integer from) {
                        fieldIntVar++;
                        return String.valueOf(from + num2);
                    }
                };
                converter2.convert(4);// 6

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //num++;//compiler failed, num accutually final
                        TagLog.i(TAG, " num2 = " + num2 + ",");
                    }
                };
            }
        });

        getButton(layout, "can not access defalut method in lamba", v -> {
            // access defalut method in lamba
            // Formula formula = (a) -> sqrt(a);// compiler failed
        });

        getButton(layout, "predicate (断定)", v -> {
            // predicate (断定)
            Predicate<String> predicate = (s) -> s.length() > 0;

            predicate.test("foo");// true
            predicate.negate().test("foo");// false
            Predicate<String> negate = predicate.negate();
            negate.test("foo");// false

            Predicate<Boolean> nonNull = Objects::nonNull;
            Predicate<Boolean> isNull = Objects::isNull;

            Predicate<String> isEmpty = String::isEmpty;
            Predicate<String> isNotEmpty = isEmpty.negate();

        });

        getButton(layout, "Functions andThen, compose", v -> {
            // functions andThen, compose

            Function<String, String> funcAdd123 = (s) -> s += "123";
            Function<String, String> funcAdd456 = (s) -> s += "456";

            String str = "abc";
            TagLog.i(TAG, " funcAdd123.apply(str) = " + funcAdd123.apply(str) + ",");// abc123
            
            TagLog.i(TAG, " funcAdd456.apply(str) = " + funcAdd456.apply(str) + ",");// abc456

            TagLog.i(TAG, " funcAdd123.compose(funcAdd456).apply(str) = "
                    + funcAdd123.compose(funcAdd456).apply(str) + ",");// abc456123
            
            TagLog.i(TAG, " funcAdd123.andThen(funcAdd456).apply(str) = " 
                    + funcAdd123.andThen(funcAdd456).apply(str) + ",");// abc123456
        });

        getButton(layout, "Supplier", v -> {
            // Supplier
            Supplier<Person> personSupplier = Person::new;
            Person person = personSupplier.get();
            TagLog.i(TAG, " person = " + person + ",");
        });

        getButton(layout, "Consumer, only execute, return void", v -> {
            // Consumer, only execute, return void
            Consumer<Person> greeter = (p) -> TagLog.i(TAG, p.mLastName);
            greeter.accept(new Person("Tim", "Duncan"));
        });

        getButton(layout, "Comparator", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comparator
                Comparator<Person> comparator = (p1, p2) -> p1.mLastName.compareTo(p2.mLastName);

                Person duncan = new Person("", "Duncan");
                Person andy = new Person("", "Andy");
                int compareResult = comparator.compare(duncan, andy);
                TagLog.i(TAG, " compareResult = " + compareResult + ",");// > 0

                Comparator<Person> reversed = comparator.reversed();
                int reversedCompareResult = reversed.compare(duncan, andy);
                TagLog.i(TAG, " reversedCompareResult = " + reversedCompareResult + ",");// < 0
            }
        });



    }

    private static class TestReferenceMethod {
        String startWith(String s) {
            if (null == s || s.length() < 1) {
                return "";
            }
            return String.valueOf(s.charAt(0));
        }
    }

    // TestReferenceConstructorMethod
    private static class Person {
        private String mFirstName;

        private String mLastName;

        public Person() {
        }

        public Person(String firstName, String lastName) {
            mFirstName = firstName;
            mLastName = lastName;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "mFirstName='" + mFirstName + '\'' +
                    ", mLastName='" + mLastName + '\'' +
                    '}';
        }
    }

    private interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

}
