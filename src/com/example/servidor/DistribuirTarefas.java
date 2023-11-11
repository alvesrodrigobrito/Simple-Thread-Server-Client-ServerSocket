package com.example.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable{
    private final ExecutorService threadPool;
    private final BlockingQueue<String> filaComandos;
    private final Socket socket;
    private final ServidorTarefas servidorTarefas;

    public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidorTarefas) {
        this.threadPool = threadPool;
        this.filaComandos = filaComandos;
        this.socket = socket;
        this.servidorTarefas = servidorTarefas;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo tarefa via thread " + this.socket);

            Scanner entradaComando = new Scanner(socket.getInputStream());
            PrintStream saidaComando = new PrintStream(socket.getOutputStream());

            while (entradaComando.hasNextLine()){
                String comandoRecebido = entradaComando.nextLine();
                System.out.println("Comado recebido: " + comandoRecebido);
                switch (comandoRecebido){
                    case "c1": {
                        saidaComando.println("Confirmação comando 1.");
                        ComandoC1 c1 = new ComandoC1(saidaComando);
                        this.threadPool.execute(c1);
                        break;
                    }
                    case "c2": {
                        saidaComando.println("Confirmação comando 2.");
                        ComandoC2ChamaWebService c2WS = new ComandoC2ChamaWebService(saidaComando);
                        ComandoC2ChamaBanco c2Banco = new ComandoC2ChamaBanco(saidaComando);
                        Future<String> submitWs = this.threadPool.submit(c2WS);
                        Future<String> submitBanco = this.threadPool.submit(c2Banco);

                        this.threadPool.submit(new JuntaWsBanco(submitWs,
                                submitBanco,
                                saidaComando));

                        break;
                    }
                    case "c3": {
                        this.filaComandos.put(comandoRecebido); //Bloqueia
                        saidaComando.println("Comando c3 adicionado na fila");
                        break;
                    }
                    case "halt": {
                        saidaComando.println("Desligando o servidor.");
                        this.servidorTarefas.parar();
                        break;
                    }
                    default: {
                        saidaComando.println("Comando não encontrado.");
                        break;
                    }
                }
            }
            entradaComando.close();
            saidaComando.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
