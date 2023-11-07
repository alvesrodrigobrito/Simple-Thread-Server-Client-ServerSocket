package com.example.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
    public static final int PORT = 30051;

    public static void main(String[] args) throws Exception {

        System.out.println("--- Iniciando servidor na porta " + PORT + " ---");
        ServerSocket servidor = new ServerSocket(PORT);

        //ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ExecutorService threadPool = Executors.newCachedThreadPool(); // aumenta dinamicamente e diminui dinamicamente depois de 60s

        while (true) {

            Socket socket = servidor.accept();
            System.out.println("--- Cliente conectado na porta " + socket.getPort());

            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            // Comentei depois de criar obj threadPool
            // Thread threadClient = new Thread(distribuirTarefas);
            // threadClient.start();
            threadPool.execute(distribuirTarefas);
        }
    }
}
