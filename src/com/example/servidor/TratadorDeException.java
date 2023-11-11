package com.example.servidor;

public class TratadorDeException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Deu exception na thread " + t.getName() + ", " + e.getMessage());
    }
}
