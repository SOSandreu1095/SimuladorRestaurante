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
public class Cliente implements Runnable {

    private Simulador simulador;
    private Mesa mesa;
    private int tiempoComer;
    private int tiempoDigestion;
    private boolean descansando;
    private boolean comiendo;

    public Cliente(Simulador simulador, Mesa mesa) {
        this.simulador = simulador;
        this.mesa = mesa;
        this.tiempoComer = generarTiempoRandomEntre(1, 15);
        this.tiempoDigestion = generarTiempoRandomEntre(1, 60);
        this.comiendo = false;
        this.descansando = false;
    }

    /**
     * Bucle que se lleva a cabo mientras el simulador esta ejecutandose En el
     * se mira si hay platos a coger en la mesa, si los hay, se coge, se come y
     * se descansa
     */
    @Override
    public void run() {
        while (simulador.isRunning()) {
            while (!simulador.isPaused()) {
                mesa.cogerPlato();
                comer();
                descansar();
            }
            sleepMilSec(100);
        }
    }

    /**
     * Se cogera un plato, y se dormira el proceso durante un periodo de tiempo
     * correspondiente al tiempo que tarda un cliente en comerse el plato
     */
    public void comer() {
        comiendo = true;
        sleepMilSec(this.tiempoComer * 1000); //1000 = 1 sec
        comiendo = false;
        simulador.incrementarHamburguesasComidas();
        simulador.incrementarTiempoComiendo(tiempoComer);
    }

    /**
     * Duereme el proceso durante un periodo de tiempo correspondiente al tiempo
     * de digestion del cliente
     */
    public void descansar() {
        descansando = true;
        sleepMilSec(this.tiempoDigestion * 1000); //1000 = 1 sec
        descansando = false;
        simulador.incrementarTiempoDescansando(tiempoDigestion);
    }

    public boolean estaComiendo() {
        return comiendo;
    }

    public boolean estaDescansando() {
        return descansando;
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

    private void sleepMilSec(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
