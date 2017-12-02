/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantethread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreu
 */
public class Simulador extends JFrame implements ActionListener {

    private final String TITLE = "SIMULADOR DE RESTAURANTE (BATMAN)";
    private JButton jButPlay, jButStop, jButReset;
    private JPanel jPanEst;
    private JPanel jPanBut;
    private Container cp;

    private Estadistica estadistica;
    private Cocinero[] cocineros; //10
    private final int max_cocineros = 10;
    private Cliente[] clientes; //100
    private final int max_clientes = 100;
    private Restaurante restaurante; //Canvas
    private Mesa mesa;
    
    //Estadisticas
    private int hamburguesasComidas;
    private int hamburguesasCocinadas;
    private int tiempoCocinando;
    private int tiempoComiendo;
    private int tiempoDescansando;

    private boolean running; //Si se esta ejecutando el simulador o no
    private boolean paused;

    public Simulador() {
        initFrame();
        initComponents();
        initClasses();
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.setVisible(true);
    }

    private void initFrame() {
        this.setTitle(TITLE);
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.running = true;
        this.paused = true;
        this.restaurante = new Restaurante(this);
        this.estadistica = new Estadistica(this);
        reiniciarEstadisticas();

        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        this.jPanEst = new JPanel();
        this.jPanEst.setLayout(new BorderLayout());
        this.jPanEst.setBackground(Color.yellow);
        cp.add(jPanEst, BorderLayout.WEST);

        //Botones
        this.jButPlay = new JButton("PLAY");
        this.jButPlay.setMargin(new Insets(5, 15, 5, 15));
        this.jButPlay.addActionListener(this);
        this.jButPlay.setFont(new Font("Arial", Font.PLAIN, 20));
        this.jButStop = new JButton("STOP");
        this.jButStop.setMargin(new Insets(5, 15, 5, 15));
        this.jButStop.setFont(new Font("Arial", Font.PLAIN, 20));
        this.jButStop.addActionListener(this);
        this.jButReset = new JButton("RESET");
        this.jButReset.setMargin(new Insets(5, 15, 5, 15));
        this.jButReset.setFont(new Font("Arial", Font.PLAIN, 20));
        this.jButReset.addActionListener(this);

        //Panel de los Botones
        jPanBut = new JPanel();
        jPanBut.setLayout(new GridLayout(1, 3));

        //Añadimos los Botones
        jPanBut.add(jButPlay, BorderLayout.NORTH);
        jPanBut.add(jButStop);
        jPanBut.add(jButReset, BorderLayout.SOUTH);

        //Añadimos el panel de los Botones bajo las estadisticas
        jPanEst.add(jPanBut, BorderLayout.SOUTH);

        //CANVAS
        cp.add(this.restaurante);
        
        //Estadisitca
        jPanEst.add(estadistica.getTableHeader(), BorderLayout.NORTH);
        jPanEst.add(estadistica, BorderLayout.CENTER);

    }

    private void initClasses() {
        this.mesa = new Mesa(this);
        new Thread(estadistica).start();
        new Thread(restaurante).start();

        //Crear los cocineros
        this.cocineros = new Cocinero[max_cocineros];
        for (int i = 0; i < cocineros.length; i++) {
            cocineros[i] = new Cocinero(this, mesa);
            new Thread(cocineros[i]).start(); //Start THREAD
        }

        //Crear los clientes
        this.clientes = new Cliente[max_clientes];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(this, mesa);
            new Thread(clientes[i]).start(); //Start THREAD
        }
    }

    /**
     * Devuelve el estado del simuldador, si se esta ejecutando o no
     *
     * @return True if running / False if is stoped
     */
    public boolean isRunning() {
        return this.running;
    }

    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "PLAY":
                System.out.println("Play Button");
                this.paused = false;
                break;
            case "STOP":
                System.out.println("Stop Button");
                this.paused = true;
                break;
            case "RESET":
                System.out.println("Reset Button");
                reiniciarEstadisticas();
                this.running = false;
                break;
        }
    }

    /**
     * Devuelve si el camarero i esta cocinanco o no
     *
     * @param i Camarero que queremos referenciar
     * @return True si esta cocinando, False si esta preparado
     */
    public boolean estaCocineroCocinando(int i) {
        return this.cocineros[i].estaCocinando();
    }
    
    public int getHamburguesasMesa(){
        return this.mesa.getNumPlatos();
    }
    
    public boolean estaComiendoCliente(int i){
        return this.clientes[i].estaComiendo();
    }
    
    public boolean estaDescansandoCliente(int i){
        return this.clientes[i].estaDescansando();
    }
    
    //Estadisticas
    public int getMax_cocineros() {
        return max_cocineros;
    }

    public int getMax_clientes() {
        return max_clientes;
    }

    public int getHamburguesasComidas() {
        return hamburguesasComidas;
    }

    public int getHamburguesasCocinadas() {
        return hamburguesasCocinadas;
    }

    public int getTiempoCocinando() {
        return tiempoCocinando;
    }

    public int getTiempoComiendo() {
        return tiempoComiendo;
    }

    public int getTiempoDescansando() {
        return tiempoDescansando;
    }

    public void incrementarHamburguesasComidas() {
        this.hamburguesasComidas++;
    }

    public void incrementarHamburguesasCocinadas(int hamburguesasCocinadas) {
        this.hamburguesasCocinadas += hamburguesasCocinadas;
    }

    public void incrementariempoCocinando(int tiempoCocinando) {
        this.tiempoCocinando += tiempoCocinando;
    }

    public void incrementarTiempoComiendo(int tiempoComiendo) {
        this.tiempoComiendo += tiempoComiendo;
    }

    public void incrementarTiempoDescansando(int tiempoDescansando) {
        this.tiempoDescansando += tiempoDescansando;
    }
    
    private void reiniciarEstadisticas(){
        this.hamburguesasComidas = 0;
        this.hamburguesasCocinadas = 0;
        this.tiempoCocinando = 0;
        this.tiempoComiendo = 0;
        this.tiempoDescansando = 0;
    }
    
    
    
}
