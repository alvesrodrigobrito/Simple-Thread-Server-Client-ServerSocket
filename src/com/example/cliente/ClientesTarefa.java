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

        PrintStream saida = new PrintStream(socket.getOutputStream());
        saida.println("Comando1");

        Scanner scan = new Scanner(System.in);
        scan.nextLine();

        scan.close();
        saida.close();
        socket.close();
    }
}
