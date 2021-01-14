package gui.ClienteGui;

import Cliente.Cliente;
import kanvan.FlujoTrabajo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TableroCliente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable tablero;
    private JTextField textFieldActividad;
    private JComboBox comboBoxActividad;
    private JButton buttonAddActividad;
    private JComboBox comboBoxFase;
    private JTextField textFieldTarea;
    private JComboBox comboBoxTarea;
    private JButton buttonAddTarea;
    private JTextField textFieldFase;
    private JButton buttonAddFase;
    private JButton cargarButton;
    private Cliente cliente;
    kanvan.FlujoTrabajo flujoTrabajo;
    private DefaultTableModel modelo;


    public TableroCliente(String host, int Puerto) {
        //flujoTrabajo=new FlujoTrabajo("flujo de trabajo");

        cliente = new Cliente("localhost",9990);
        setContentPane(contentPane);
        setPreferredSize(new Dimension(500,400));
        setResizable(false);
        setModal(true);
        actualizarTablero();

        Hilo hilo=new Hilo(flujoTrabajo,modelo,tablero,cliente,comboBoxActividad,comboBoxFase,comboBoxTarea);
        hilo.start();
        comboBoxTarea.setVisible(false);
        //actualizarTablero();
        buttonAddActividad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textFieldActividad.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(null,"Capturar tarea");
                }
                else
                {
                    cliente.enviarMensaje("ADD ACT "+textFieldActividad.getText());
                  //  grabar();
                }

            }
        });
        buttonAddFase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textFieldFase.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(null,"Capturar fase");
                }
                else
                {
                    cliente.enviarMensaje("ADD FAS "+textFieldFase.getText());
                    //grabar();
                }
            }
        });

        buttonAddTarea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cliente.enviarMensaje("ADD TAS "+ comboBoxActividad.getSelectedIndex() + comboBoxFase.getSelectedIndex() + textFieldTarea.getText());
              //  grabar();
            }
        });
    }

    private void  actualizarTablero() {
     //   cliente.enviarMensaje("GET FLUJO");
        flujoTrabajo = cliente.getFlujoDeTrabajo();
        modelo=new DefaultTableModel();;
        tablero.setModel(modelo);

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

        for(int i=0;i<flujoTrabajo.getFase().size();i++)
        {
            modelo.addColumn(flujoTrabajo.getFase().get(i).getNombre(),flujoTrabajo.getFase().get(i).getTarea());
        }
    }

    public static void main(String[] args) {
        TableroCliente dialog = new TableroCliente("localhost",9998);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

    }
}
