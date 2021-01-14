package Servidor;

import com.sun.security.ntlm.Server;
import kanvan.Actividad;
import kanvan.Fase;
import kanvan.FlujoTrabajo;
import kanvan.Tarea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private ServerSocket serverSocket;
    private FlujoTrabajo flujoTrabajo;
    private Socket socket;
    private int PUERTO;

    public Servidor(int PUERTO){
        flujoTrabajo=new FlujoTrabajo("Mi flujo de trabajo");
        this.PUERTO=PUERTO;
        iniciar();
    }

    public void iniciar(){
        try {
            serverSocket=new ServerSocket(PUERTO);
            System.out.println("Servidor conectado en el puerto " + this.PUERTO);
            while(true){
                System.out.println("Esperando un nuevo cliente");
                socket=serverSocket.accept();
                HiloServer hiloServer=new HiloServer(flujoTrabajo,socket);
                hiloServer.start();

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
