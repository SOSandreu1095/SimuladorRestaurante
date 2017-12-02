/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantethread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreu
 */
public class Cocinero implements Runnable {

    private Simulador simulador;
    private Mesa mesa;
    private boolean cocinando;
    private int numHamburguesasPreparadas;
    private int tiempoCocinar; //Valor fijo de 10 hamburguesas

    public Cocinero(Simulador simulador, Mesa mesa) {
        cocinando = true;
        this.simulador = simulador;
        this.mesa = mesa;
        this.numHamburguesasPreparadas = 0;
        this.tiempoCocinar = generarTiempoRandomEntre(5, 10);
    }

    /**
     * Bucle mientras el simulador este activado, si el cocinero tiene
     * hamburguesas preparadas, y si hay sitio en la mesa, pondra una
     * hamburguesa y si no tiene hamburguesas preparadas, se pondra a cocinarlas
     */
    @Override
    public synchronized void run() {
        while (simulador.isRunning()) {
            while (!simulador.isPaused()) {
                if (numHamburguesasPreparadas > 0) {
                    if (mesa.addPlato()) {
                        numHamburguesasPreparadas--;
                    }
                } else {
                    //System.out.println("Cocinando..");
                    cocinar();
                    //System.out.println("Preparados..");
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cocinero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * El cocinero cocina 10 una ronda de hamburguesas durante un tiempo
     * determinado (tiempo de Cocinar)
     */
    public void cocinar() {
        try {
            cocinando = true;
            Thread.sleep(tiempoCocinar * 1000); //1000 = 1 sec
            numHamburguesasPreparadas = 10;
            cocinando = false;
            simulador.incrementarHamburguesasCocinadas(10);
            simulador.incrementariempoCocinando(tiempoCocinar);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cocinero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean estaCocinando(){
        return cocinando;
    }

    /**
     * Metodo que devuelve un numero entero aleatorio definido entre un minimo y
     * un maximo
     *
     * @param min Valor entero minimo
     * @param max Valor entero maximo
     * @return Devuelve el numero aleatorio generado
     */
    private int generarTiempoRandomEntre(int min, int max) {
        return (min + (int) (Math.random() * max));
    }

}
