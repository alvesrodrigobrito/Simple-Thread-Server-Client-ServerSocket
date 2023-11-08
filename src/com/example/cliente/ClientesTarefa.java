package com.example.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientesTarefa {
    private static final int PORT_SERVER = 30051;
    private static final String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(ADDRESS, PORT_SERVER);
        System.out.println("Conexão estabelecida");

        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintStream saida = new PrintStream(socket.getOutputStream());
                    System.out.println("Digite os comandos:");
                    Scanner scan = new Scanner(System.in);
                    while (scan.hasNextLine()) {
                        String linha = scan.nextLine();
                        if(linha.trim().equals("")){
                            break;
                        }
                        saida.println(linha);
                    }
                    scan.close();
                    saida.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread threadRecebeRetornoComando = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner responseServer = new Scanner(socket.getInputStream());

                    while (responseServer.hasNextLine()){
                        String linha = responseServer.nextLine();
                        System.out.println("Resposta do servidor: " + linha);
                    }
                    responseServer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        threadRecebeRetornoComando.start();
        threadEnviaComando.start();

        try {
            threadEnviaComando.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Fechando conexão com servidor");
        socket.close();
    }
}
