package pl.text3d;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Text3DRenderer implements GLEventListener {

    public GLJPanel panel;
    private GLU glu = new GLU();
    private AnimatedText3D animatedText;

    public Text3DRenderer() {
        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        caps.setHardwareAccelerated(true);

        panel = new GLJPanel(caps);
        panel.addGLEventListener(this);
        animatedText = new AnimatedText3D();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if (key >= '0' && key <= '9') {
                    int animId = Character.getNumericValue(key);
                    animatedText.setAnimationType(animId);
                }
            }
        });
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glShadeModel(GL2.GL_SMOOTH);

        animatedText.initialize(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        animatedText.dispose(drawable);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(0, 0, 5,
                      0, 0, 0,
                      0, 1, 0);

        animatedText.render(gl, System.currentTimeMillis());
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = (float) width / (height == 0 ? 1 : height);
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}