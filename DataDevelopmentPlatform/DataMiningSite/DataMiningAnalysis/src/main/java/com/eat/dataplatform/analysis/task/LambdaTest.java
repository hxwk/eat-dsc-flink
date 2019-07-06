package com.eat.dataplatform.analysis.task;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LambdaTest {
    public static void main(String[] args) {
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

        // 以前的循环方式
        for (String player : players) {
            System.out.print(player + "; ");
        }

        // 使用 lambda 表达式以及函数操作(functional operation)
        //players.forEach(player->System.out.println(player+","));
        //players.forEach((player) -> System.out.print(player + "; "));

        // 在 Java 8 中使用双冒号操作符(double colon operator)
        //players.forEach(System.out::println);

        // 1.2使用 lambda expression
        //new Thread(() -> System.out.println("Hello world !")).start();

        Comparator<String> stringComparator = (String s1, String s2) -> (s2.compareTo(s1));
        Arrays.sort(atp,stringComparator);
        Arrays.asList(atp).forEach(System.out::println);
    }
}
