package gui.ClienteGui;

import Cliente.Cliente;
import kanvan.FlujoTrabajo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Hilo extends Thread{
    private FlujoTrabajo flujoTrabajo;
    private DefaultTableModel modelo;
    private JTable tablero;
    private Cliente cliente;
    private JComboBox comboBoxActividad;
    private JComboBox comboBoxFase;
    private JComboBox comboBoxTarea;

    public Hilo(FlujoTrabajo flujoTrabajo, DefaultTableModel modelo, JTable tablero, Cliente cliente, JComboBox comboBoxActividad, JComboBox comboBoxFase, JComboBox comboBoxTarea) {
        this.flujoTrabajo = flujoTrabajo;
        this.modelo = modelo;
        this.tablero = tablero;
        this.cliente = cliente;
        this.comboBoxActividad = comboBoxActividad;
        this.comboBoxFase = comboBoxFase;
        this.comboBoxTarea = comboBoxTarea;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flujoTrabajo = cliente.getFlujoDeTrabajo();
            modelo=new DefaultTableModel();;
            tablero.setModel(modelo);
            for(int i=0;i<flujoTrabajo.getFase().size();i++)
            {
                modelo.addColumn(flujoTrabajo.getFase().get(i).getNombre(),flujoTrabajo.getFase().get(i).getTarea());
            }
            comboBoxActividad.removeAllItems();
            for(int i=0;i<flujoTrabajo.getActividad().size();i++)
            {
                comboBoxActividad.addItem(flujoTrabajo.getActividad().get(i).getNombre());
            }

            comboBoxFase.removeAllItems();
            for(int i=0;i<flujoTrabajo.getFase().size();i++)
            {
                comboBoxFase.addItem(flujoTrabajo.getFase().get(i).getNombre());
            }

            comboBoxTarea.removeAllItems();
            for (int j = 0; j < flujoTrabajo.getTarea().size(); j++) {
                comboBoxTarea.addItem(flujoTrabajo.getTarea().get(j).getNombre());
            }

        }
    }
}
