/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantethread;

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
    public synchronized boolean addPlato() {
        if (cabenPlatos()){
            this.numPlatos++;
            //System.out.println("Plato added - Total: " + this.numPlatos);
            notifyAll();
            return true;
        }
        notifyAll();
        return false;
    }

    /**
     * Decrementa -1 el numero de platos actuales sobre la mesa (si se puede)
     *
     * @return True si se puede (minimo 0 platos), sino devuelve false
     */
    public synchronized boolean cogerPlato() {
        if (hayPlatos()){
            this.numPlatos--;
            //System.out.println("\tPlato comido - Restantes: "+this.numPlatos);
            notifyAll();
            return true;
        }
        notifyAll();
        return false;        
    }

    public synchronized int getNumPlatos() {
        notifyAll();
        return this.numPlatos;
    }

    public synchronized boolean hayPlatos() {
        notifyAll();
        return this.numPlatos > 0;
    }

    public synchronized boolean cabenPlatos() {
        notifyAll();
        return this.numPlatos < this.MAX_PLATOS;
    }

}
