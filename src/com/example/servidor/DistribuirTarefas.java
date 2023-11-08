package com.example.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable{
    private final Socket socket;
    private final ServidorTarefas servidorTarefas;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidorTarefas) {
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
                        break;
                    }
                    case "c2": {
                        saidaComando.println("Confirmação comando 2.");
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
