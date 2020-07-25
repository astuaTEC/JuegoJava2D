package juego;

import control.Teclado;

import javax.swing.*;
import java.awt.*;

public class Juego extends Canvas implements Runnable{

    private static JFrame ventana;
    private static Thread thread;
    private static Teclado teclado;

    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private static volatile boolean enFuncionamiento = false;

    private static final String NOMBRE = "juego";
    private static int aps = 0;
    private static int fps = 0;

    private Juego(){
        setPreferredSize(new Dimension(ANCHO, ALTO));

        teclado = new Teclado();
        addKeyListener(teclado);

        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
    }

    private void iniciar(){
        enFuncionamiento = true;

        thread = new Thread(this, "Graficos");
        thread.start();
    }
    private void detener(){
        enFuncionamiento = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void actualizar(){
        teclado.actualizar();

        if(teclado.arriba){
            System.out.println("Arriba");
        }
        else if(teclado.abajo){
            System.out.println("Abajo");
        }
        else if(teclado.derecha){
            System.out.println("Derecha");
        }
        else if(teclado.izquierda){
            System.out.println("Izquierda");
        }

        aps++;
    }
    private void mostrar(){
        fps++;
    }
    @Override
    public void run() {
        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido;
        double delta = 0;

        requestFocus();

        while (enFuncionamiento){
            final long inicioBucle = System.nanoTime();

            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;
            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

            while (delta >= 1){
                actualizar();
                delta--;
            }
            mostrar();

            if(System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                ventana.setTitle(NOMBRE + " || APS: " + aps + " || FPS: " + fps);
                aps = 0;
                fps = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }
}
