package com.example.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWebService implements Callable<String> {
    private final PrintStream saidaComando;
    public ComandoC2ChamaWebService(PrintStream saidaComando) {
        this.saidaComando = saidaComando;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Servidor recebeu comando c2, executando WebService...");
        saidaComando.println("processando comando c2 WebService...");
        Thread.sleep(25000);
        int numero = new Random().nextInt(100) + 1;
        this.saidaComando.println("Comando c2 executado com sucesso no WebService");
        return Integer.toString(numero);
    }
}
