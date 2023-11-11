package com.example.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaBanco implements Callable<String> {
    private final PrintStream saidaComando;
    public ComandoC2ChamaBanco(PrintStream saidaComando) {
        this.saidaComando = saidaComando;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Servidor recebeu comando c2, executando Banco...");
        saidaComando.println("processando comando c2 Banco...");
        Thread.sleep(25000);
        int numero = new Random().nextInt(100) + 1;
        this.saidaComando.println("Comando c2 executado com sucesso no Banco");
        return Integer.toString(numero);
    }
}
