package pl.text3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class AnimatedText3D {

    // ZMIEŃ TEN TEKST NA SWÓJ!
    private String text = "Text sample";

    private TextRenderer textRenderer = null;
    private int currentAnimation = 0;
    private long startTime = System.currentTimeMillis();

    public void setAnimationType(int type) {
        this.currentAnimation = type % 10;
        this.startTime = System.currentTimeMillis();
        System.out.println("Animacja: " + currentAnimation);
    }

    public void initialize(GLAutoDrawable drawable) {
        if (textRenderer == null) {
            // LEPSZA CZCIONKA + rozmiar 48 (możesz zmienić na 60, 72 itp.)
            textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 48), true, true);
            // Alternatywy jeśli Arial nie działa:
            // new Font("Verdana", Font.BOLD, 48)
            // new Font("Segoe UI", Font.BOLD, 48)
            // new Font("Tahoma", Font.BOLD, 48)
        }
    }

    public void render(GL2 gl, long currentTime) {
        if (textRenderer == null) return;

        gl.glPushMatrix();

        float time = (currentTime - startTime) / 1000.0f;

        switch (currentAnimation) {
            case 1:
                gl.glRotatef(time * 50, 0, 1, 0);
                break;
            case 2:
                gl.glRotatef(time * 50, 1, 0, 0);
                break;
            case 3:
                float scale = 1 + 0.3f * (float) Math.sin(time * 4);
                gl.glScalef(scale, scale, scale);
                break;
            case 4:
                gl.glRotatef(time * 60, 0, 1, 0);
                gl.glTranslatef(0, (float) Math.sin(time * 2) * 0.5f, 0);
                break;
            case 5:
                gl.glRotatef(time * 80, 0, 1, 0);
                gl.glTranslatef((float) Math.sin(time * 3) * 0.5f, (float) Math.cos(time * 3) * 0.5f, 0);
                break;

            // NOWA ANIMACJA DLA 6 – zmiana koloru + falowanie
            case 6:
                // Zmiana koloru (tęczowa)
                float r = (float) (Math.sin(time * 2) + 1) / 2;
                float g = (float) (Math.sin(time * 2 + 2) + 1) / 2;
                float b = (float) (Math.sin(time * 2 + 4) + 1) / 2;
                textRenderer.setColor(r, g, b, 1.0f);

                // Dodatkowe falowanie
                float wave = (float) Math.sin(time * 3) * 0.1f;
                gl.glTranslatef(0, wave, 0);
                float subtleScale = 1 + 0.05f * (float) Math.sin(time * 5);
                gl.glScalef(subtleScale, subtleScale, subtleScale);
                break;

            case 7:
                gl.glRotatef(time * 40, 1, 1, 1);
                break;
            case 8:
                float z = (float) Math.sin(time * 2) * 2;
                gl.glTranslatef(0, 0, z);
                break;
            case 9:
                float pulse = 1 + 0.5f * (float) Math.abs(Math.sin(time * 10));
                gl.glScalef(pulse, pulse, pulse);
                gl.glRotatef(time * 100, 0, 1, 0);
                break;

            // NOWA ANIMACJA DLA 0 – delikatne "oddychanie" + powolna rotacja
            case 0:
                // Delikatne pulsowanie (oddychanie)
                float breathe = 1 + 0.08f * (float) Math.sin(time * 1.5);
                gl.glScalef(breathe, breathe, breathe);

                // Bardzo powolna rotacja wokół osi Y
                gl.glRotatef(time * 15, 0, 1, 0);
                break;

            default:
                break;
        }

        textRenderer.begin3DRendering();
        textRenderer.setColor(Color.WHITE); // domyślny kolor (nadpisany tylko w animacji 6)

        Rectangle2D bounds = textRenderer.getBounds(text);
        float width = (float) bounds.getWidth();
        float depth = 0.02f; // grubość 3D – możesz zwiększyć do 0.03f lub 0.04f dla większej głębi

        textRenderer.draw3D(text, -width / 2 * depth, -0.4f, 0, depth);

        textRenderer.end3DRendering();

        gl.glPopMatrix();
    }

    public void dispose(GLAutoDrawable drawable) {
        if (textRenderer != null) {
            textRenderer.dispose();
            textRenderer = null;
        }
    }
}