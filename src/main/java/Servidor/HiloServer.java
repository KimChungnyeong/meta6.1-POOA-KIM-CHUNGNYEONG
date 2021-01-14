package Servidor;

import kanvan.Actividad;
import kanvan.Fase;
import kanvan.FlujoTrabajo;
import kanvan.Tarea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloServer extends Thread {
    private FlujoTrabajo flujoTrabajo;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public HiloServer(FlujoTrabajo flujoTrabajo, Socket socket) {
        this.flujoTrabajo = flujoTrabajo;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Cliente conectado");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String mensaje = (String) objectInputStream.readObject();
            System.out.println("El cliente ha enviado " + mensaje);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            if (mensaje.contains("GET FLUJO")) {
                objectOutputStream.writeObject(flujoTrabajo);
                System.out.println("El servidor respondio el nuevo objeto " + flujoTrabajo);
            } else if (mensaje.contains("ADD ACT")) {
                flujoTrabajo.getActividad().add(new Actividad(mensaje.substring(8), flujoTrabajo));
                String respuesta = "Se agrego la actividad: " + mensaje.substring(8);
                objectOutputStream.writeObject(flujoTrabajo);
            } else if (mensaje.contains("ADD FAS")) {
                flujoTrabajo.getFase().add(new Fase(mensaje.substring(8), flujoTrabajo));
                String respuesta = "Se agrego la fase: " + mensaje.substring(8);
                objectOutputStream.writeObject(flujoTrabajo);
            } else if (mensaje.contains("ADD TAS")) {
                Actividad actividad = flujoTrabajo.getActividad().get(Integer.parseInt(mensaje.substring(8, 9)));
                Fase fase = flujoTrabajo.getFase().get(Integer.parseInt(mensaje.substring(9, 10)));
                Tarea tarea = new Tarea(mensaje.substring(10), flujoTrabajo, actividad, fase);
                flujoTrabajo.getTarea().add(tarea);
                actividad.getTarea().add(tarea);
                fase.getTarea().add(tarea);
                String respuesta = "Se agrego la tarea: " + mensaje.substring(10);
                objectOutputStream.writeObject(flujoTrabajo);
            } else {
                String respuesta = "I DONT KNOW";
                objectOutputStream.writeObject(respuesta);
                System.out.println("El servidor respondio el nuevo objeto " + respuesta);
            }
            objectOutputStream.close();
            socket.close();
            System.out.println("Cliente desconectado");
        }catch (IOException ioException){
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
