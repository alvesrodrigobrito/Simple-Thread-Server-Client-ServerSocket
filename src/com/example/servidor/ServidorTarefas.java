package com.example.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {
    public static final int PORT = 30051;

    public static void main(String[] args) throws IOException {

        System.out.println("--- Iniciando servidor na porta " + PORT + " ---");
        ServerSocket servidor = new ServerSocket(PORT);
        while (true) {
            Socket socket = servidor.accept();
            System.out.println("--- Cliente conectado na porta " + socket.getPort());
        }
    }
}
