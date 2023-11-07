package com.example;

import java.util.Set;

public class ListarThreads {
    public static void main(String[] args) {
        Set<Thread> todasAsThreads = Thread.getAllStackTraces().keySet();

        for (Thread thread : todasAsThreads) {
            System.out.println(thread.getName());
        }
    }
}
