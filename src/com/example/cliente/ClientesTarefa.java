package com.example.cliente;

import java.io.IOException;
import java.net.Socket;

public class ClientesTarefa {
    private static final int PORT_SERVER = 30051;
    private static final String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(ADDRESS, PORT_SERVER);
        System.out.println("Conexão estabelecida");
        socket.close();

    }
}
