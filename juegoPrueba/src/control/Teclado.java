package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {

    private final static int numeroTeclas = 120;
    private final boolean[] teclas = new boolean[numeroTeclas];

    public boolean arriba;
    public boolean abajo;
    public boolean derecha;
    public boolean izquierda;

    public void actualizar(){
        arriba = teclas[KeyEvent.VK_UP];
        abajo = teclas[KeyEvent.VK_DOWN];
        derecha = teclas[KeyEvent.VK_RIGHT];
        izquierda = teclas[KeyEvent.VK_LEFT];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
    }
}
