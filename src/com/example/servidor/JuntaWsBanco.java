package com.example.servidor;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaWsBanco implements Callable<Void> {
    private final Future<String> submitWs;
    private final Future<String> submitBanco;
    private final PrintStream saidaComando;

    public JuntaWsBanco(Future<String> submitWs,
                        Future<String> submitBanco,
                        PrintStream saidaComando) {
        this.submitWs = submitWs;
        this.submitBanco = submitBanco;
        this.saidaComando = saidaComando;
    }

    @Override
    public Void call() {
        System.out.println("Aguadando resultado do submit Ws e Banco");
        try {
            String numeroMAgicoWs = this.submitWs.get(20, TimeUnit.SECONDS);
            String numeroMAgicoBanco = this.submitWs.get(20, TimeUnit.SECONDS);
            this.saidaComando.println("Resultado do comando c2: " + numeroMAgicoWs + " " + numeroMAgicoBanco);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.saidaComando.println("Timeout: na execução do comando c2");
            this.submitWs.cancel(true);
            this.submitBanco.cancel(true);
        }
        System.out.println("Finalizou JuntaWsBanco.class");
        return null;
    }
}
