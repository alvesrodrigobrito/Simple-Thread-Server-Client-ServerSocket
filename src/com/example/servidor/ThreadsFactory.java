package com.example.servidor;

import java.util.concurrent.ThreadFactory;

public class ThreadsFactory implements ThreadFactory {
    private static int numero = 1;
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,"Thread servidor de tarefas " + numero);
        numero++;
        thread.setUncaughtExceptionHandler(new TratadorDeException());
        return thread;
    }
}
