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
public class Mesa {

    private Simulador simulador;
    private final int MAX_PLATOS; //50
    private int numPlatos;

    public Mesa(Simulador simulador) {
        this.simulador = simulador;
        this.MAX_PLATOS = 50;
        this.numPlatos = 0;
    }

    /**
     * Incrementa +1 el numero de platos actuales sobre la mesa (si se puede)
     *
     * @return True si se puede (maximo 50 platos), sino devuelve false
     */
    public synchronized void addPlato() {
        while (!cabenPlatos()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.numPlatos++;
        notifyAll();
    }

    /**
     * Decrementa -1 el numero de platos actuales sobre la mesa (si se puede)
     *
     * @return True si se puede (minimo 0 platos), sino devuelve false
     */
    public synchronized void cogerPlato() {
        while (!hayPlatos()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.numPlatos--;
        notifyAll();
    }

    public int getNumPlatos() {
        return this.numPlatos;
    }

    public boolean hayPlatos() {
        return this.numPlatos > 0;
    }

    public boolean cabenPlatos() {
        return this.numPlatos < this.MAX_PLATOS;
    }

}
