package com.example.rere.practice.java8;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        getButton(layout, "Stream filter forEach", v -> {
            // Stream
            stringCollection
                    .stream()
                    .filter((s) -> s.startsWith("a"))
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));
        });

        getButton(layout, "Stream sort filter forEach", v -> {
            // Stream sort filter forEach
            TagLog.i(TAG, "stringCollection stream, sorted, forEach");
            stringCollection
                    .stream()
                    .sorted()
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));

            TagLog.i(TAG, "stringCollection stream, forEach");
            stringCollection
                    .stream()
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));

            // sorted would not change the order of the collection,
            // it only sort the stream.
        });

        getButton(layout, "stream map", v -> {
            // stream map
            TagLog.i(TAG, "stringCollection stream, map, forEach");
            stringCollection
                    .stream()
                    .map(String::toUpperCase)
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));

            TagLog.i(TAG, "stringCollection stream, forEach");
            stringCollection
                    .stream()
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));

        });

        getButton(layout, "stream match", v -> {
            // stream match
            boolean anyMatch = stringCollection
                    .stream()
                    .anyMatch(s -> s.startsWith("a"));

            boolean allMatch = stringCollection
                    .stream()
                    .allMatch(s -> s.startsWith("a"));

            boolean noneMatch = stringCollection
                    .stream()
                    .noneMatch(s -> s.startsWith("z"));

            TagLog.i(TAG, " anyMatch = " + anyMatch + ",");
            TagLog.i(TAG, " allMatch = " + allMatch + ",");
            TagLog.i(TAG, " noneMatch = " + noneMatch + ",");

        });

        getButton(layout, "stream count", v -> {
            // stream count
            long streamCount = stringCollection
                    .stream()
                    .filter(s -> s.startsWith("a"))
                    .count();

            TagLog.i(TAG, " streamCount = " + streamCount + ",");

        });

        getButton(layout, "stream reduce", v -> {
            // stream reduce
            Optional<String> reduce = stringCollection
                    .stream()
                    .reduce((s, s2) -> s + "#" + s2);

            reduce.ifPresent(s -> TagLog.i(TAG, " s = " + s + ","));

            // equals to :
            stringCollection
                    .stream()
                    .reduce(new BinaryOperator<String>() {
                        @Override
                        public String apply(String s, String s2) {
                            return s + "#" + s2;
                        }
                    });
            // and the apply result will be the next s, to apply
        });

        getButton(layout, "parallel stream", v -> {
            // parallel stream
            stringCollection
                    .parallelStream()
                    .sorted()
                    .forEach(s -> TagLog.i(TAG, " s = " + s + ","));
        });

        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val"+ i);// looks like val0, val1
            // put if absent means :
            // if (null == map.get(key))
            //      map.put(key, value);
        }

        getButton(layout, "map", v -> {
            // map
            map.forEach((i, str)-> TagLog.i(TAG, " (i , str) = " + (i + "," + str) + ","));

            map.computeIfPresent(3, (i, str) -> str + i);
            String mapGet3 = map.get(3);
            TagLog.i(TAG, " mapGet3 = " + mapGet3 + ",");//val33
            // computeIfPresent meas :
            /* if (map.get(key) != null) {
                *     V oldValue = map.get(key);
                *     V newValue = remappingFunction.apply(key, oldValue);
                *     if (newValue != null)
                *         map.put(key, newValue);
                *     else
                *         map.remove(key);
                * }
            * */

            map.computeIfPresent(9, (i, str) -> null);// key 9 -> null
            TagLog.i(TAG, " (map.containsKey(9)) = " + (map.containsKey(9)) + ",");
            // false, the key will be remove, because the null

            TagLog.i(TAG, " (map.containsKey(23)) = " + (map.containsKey(23)) + ",");// false
            map.computeIfAbsent(23, i -> "val" + i);// 23 is not exist, 23 -> val23
            TagLog.i(TAG, " (map.containsKey(23)) = " + (map.containsKey(23)) + ",");// true

            map.computeIfAbsent(3, i -> "bam");// 3 is exist, so does not compute
            TagLog.i(TAG, " (map.get(3)) = " + (map.get(3)) + ",");// val33

            map.remove(3, "val3");// cause 3 -> val33, so did not remove
            TagLog.i(TAG, " (map.get(3)) = " + (map.get(3)) + ",");// val33

            map.remove(3, "val33");// cause 3 -> val33, so  remove
            TagLog.i(TAG, " (map.get(3)) = " + (map.get(3)) + ",");// null

            map.getOrDefault(42, "not found");
            TagLog.i(TAG, " (map.getOrDefault(42, \"not found\")) = " + (map.getOrDefault(42, "not found")) + ",");
            // not found

            map.merge(9, "abc9", (value, newValue) -> value.concat(newValue));
            map.get(9);// abc9, because key 9 have already remove, so use the newValue directly.
            TagLog.i(TAG, " (map.get(9)) = " + (map.get(9)) + ",");// abc9

            map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
            map.get(9);// abc9concat, "abc9".concat("concat");
            TagLog.i(TAG, " (map.get(9)) = " + (map.get(9)) + ",");// abc9concat

        });


        getButton(layout, "Date API(skipped)", v -> {
            // Date API(skipped)
            

        });

        getButton(layout, "Annotations(repeatable) (skipped)", v -> {
            // Annotations(repeatable) (skipped)


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
