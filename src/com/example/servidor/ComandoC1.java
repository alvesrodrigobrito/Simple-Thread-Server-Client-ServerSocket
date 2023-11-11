package com.example.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable{
    private final PrintStream saidaComando;
    public ComandoC1(PrintStream saidaComando) {
        this.saidaComando = saidaComando;
    }

    @Override
    public void run() {
        System.out.println("Executnado comando c1");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        saidaComando.println("Comando c1 executado com sucesso");
    }
}
