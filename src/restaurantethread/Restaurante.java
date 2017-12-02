/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantethread;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Andreu
 */
public class Restaurante extends Canvas implements Runnable {

    private Simulador simulador;

    public Restaurante(Simulador simulador) {
        this.simulador = simulador;
        this.setBackground(Color.GREEN);
    }

    @Override
    public void run() {
        while (simulador.isRunning()) {
            if (!simulador.isPaused()) {
                repaint();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        pintarCocineros(g);
        pintarMesa(g);
        pintarClientes(g);
    }

    private void pintarCocineros(Graphics g) {
        try {
            BufferedImage preparado, cocinando;
            cocinando = ImageIO.read(new File("img/cocineroCocinando.png"));
            preparado = ImageIO.read(new File("img/cocineroPreparado.png"));

            int ancho = getWidth() / (10 + 2);

            for (int i = 0; i < 10; i++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(ancho * (i + 1), this.getHeight() / 20, ancho, this.getHeight() / 12);
                g.setColor(Color.BLUE);
                g.drawRect(ancho * (i + 1), this.getHeight() / 20, ancho, this.getHeight() / 12);

                if (simulador.estaCocineroCocinando(i)) {
                    g.drawImage(cocinando, ancho * (i + 1), this.getHeight() / 20, ancho, this.getHeight() / 12, this);
                } else {
                    g.drawImage(preparado, ancho * (i + 1), this.getHeight() / 20, ancho, this.getHeight() / 12, this);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pintarMesa(Graphics g) {
        try {
            int totHam = simulador.getHamburguesasMesa();
            int cnt = 0;
            BufferedImage img;
            img = ImageIO.read(new File("img/ham.png"));

            int ancho = getWidth() / (10 + 2);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 10; x++, cnt++) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(ancho * (x + 1), (this.getHeight() / 5) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20);
                    g.setColor(Color.BLUE);
                    g.drawRect(ancho * (x + 1), (this.getHeight() / 5) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20);

                    if (cnt < totHam) {
                        g.drawImage(img, ancho * (x + 1), (this.getHeight() / 5) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20, null);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pintarClientes(Graphics g) {
        try {
            BufferedImage esperando, comiendo, descansando;

            esperando = ImageIO.read(new File("img/clienteEsperando.png"));
            comiendo = ImageIO.read(new File("img/clienteComiendo.png"));
            descansando = ImageIO.read(new File("img/clienteDescansando.png"));
            int indice = 0;
            int ancho = getWidth() / (20 + 2);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 20; x++, indice++) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(ancho * (x + 1), (this.getHeight() / 2 + 50) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20);
                    g.setColor(Color.BLUE);
                    g.drawRect(ancho * (x + 1), (this.getHeight() / 2 + 50) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20);
                    
                    if (simulador.estaComiendoCliente(indice)){
                        g.drawImage(comiendo, ancho * (x + 1), (this.getHeight() / 2 + 50) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20, null);
                    } else if (simulador.estaDescansandoCliente(indice)){
                        g.drawImage(descansando, ancho * (x + 1), (this.getHeight() / 2 + 50) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20, null);
                    } else {
                        g.drawImage(esperando, ancho * (x + 1), (this.getHeight() / 2 + 50) + (y + 1) * this.getHeight() / 20, ancho, this.getHeight() / 20, null);
                    }                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
