package com.example.servidor;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable{
    private final Socket socket;
    public DistribuirTarefas(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Distribuindo tarefa via thread " + this.socket);
        try {
            Scanner entradaComando = new Scanner(socket.getInputStream());
            while (entradaComando.hasNextLine()){
                String comandoRecebido = entradaComando.nextLine();
                System.out.println(comandoRecebido);
            }
            entradaComando.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
