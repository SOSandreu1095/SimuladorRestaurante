/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantethread;

import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Andreu
 */
public class Estadistica extends JTable implements Runnable {

    private Simulador simulador;
    private DefaultTableModel modelo;
    private String[] header = {"DATO", "VALOR"};
    private String[][] datos = {{"Cocineros", ""},
    {"Hamburguesas cocinadas", ""},
    {"Tiempo cocinando", ""},
    {"Clientes", ""},
    {"Hamburguesas comidas", ""},
    {"Tiempo comiendo", ""},
    {"Tiempo descansando", ""}};

    public Estadistica(Simulador simulador) {
        this.simulador = simulador;
        initTable();
        refresh();
    }

    @Override
    public void run() {
        while (simulador.isRunning()) {
            while (!simulador.isPaused()) {
                refresh();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Estadistica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * R
     */
    private void refresh() {
        modelo.setValueAt(simulador.getMax_cocineros(), 0, 1);
        modelo.setValueAt(simulador.getHamburguesasCocinadas(), 1, 1);
        modelo.setValueAt(simulador.getTiempoCocinando(), 2, 1);
        modelo.setValueAt(simulador.getMax_clientes(), 3, 1);
        modelo.setValueAt(simulador.getHamburguesasComidas(), 4, 1);
        modelo.setValueAt(simulador.getTiempoComiendo(), 5, 1);
        modelo.setValueAt(simulador.getTiempoDescansando(), 6, 1);
    }

    private void initTable() {
        modelo = new DefaultTableModel(datos, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.setModel(modelo);
        this.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        this.getTableHeader().setPreferredSize(new Dimension(200,50));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column;
        for (int i = 0; i < this.getColumnCount(); i++) {
            column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
            column.setCellRenderer(centerRenderer);
        }
        this.setRowHeight(50);

    }

}
