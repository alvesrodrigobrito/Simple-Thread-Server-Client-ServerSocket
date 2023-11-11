package com.example.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
    public static final int PORT = 30051;
    private final ServerSocket servidor;
    private final ExecutorService threadPool;
    //private volatile boolean estaRodando;
    private AtomicBoolean estaRodando; // 2 threads utilizando a mesma variável, evita que o thread trabalhe com cache
    private BlockingQueue<String> filaComandos;
    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando servidor na porta " + PORT + " ---");
        this.servidor = new ServerSocket(PORT);
        //ExecutorService threadPool = Executors.newFixedThreadPool(2);
        // newCachedThreadPool() aumenta dinamicamente e diminui dinamicamente depois de 60s
        this.threadPool = Executors.newCachedThreadPool(new ThreadsFactory()); //newCachedThreadPool();
        //this.estaRodando = true;
        this.estaRodando = new AtomicBoolean(true);
        this.filaComandos = new ArrayBlockingQueue<>(3);
        iniciarComsumidores();
    }

    private void iniciarComsumidores() {
        int qtdConsumidores = 2;
        for (int i = 0; i < qtdConsumidores; i++) {
            TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
            this.threadPool.execute(tarefa);
        }
    }

    public static void main(String[] args) throws Exception {

        ServidorTarefas servidorTarefas = new ServidorTarefas();
        servidorTarefas.rodar();
        servidorTarefas.parar();

    }
    public void rodar() throws IOException {
        while (this.estaRodando.get()) {
            try {
                Socket socket = this.servidor.accept();
                System.out.println("--- Cliente conectado na porta " + socket.getPort());
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, this.filaComandos, socket, this);
                // Comentei depois de criar obj threadPool
                // Thread threadClient = new Thread(distribuirTarefas);
                // threadClient.start();
                this.threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                System.out.println("SocketExceptio, Está rodando? " + this.estaRodando);
            }
        }
    }
    public void parar() throws IOException {
        //this.estaRodando = false;
        this.estaRodando.set(false);
        this.servidor.close();
        this.threadPool.shutdown();
        //System.exit(0);
    }
}
