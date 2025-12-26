package pl.text3d;

import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("3D Animowany Tekst");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            Text3DRenderer renderer = new Text3DRenderer();
            frame.add(renderer.panel, BorderLayout.CENTER);

            JLabel info = new JLabel("Naciśnij klawisze 0-9 aby zmienić animację. Tekst można zmienić w klasie AnimatedText3D.");
            info.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(info, BorderLayout.SOUTH);

            frame.setVisible(true);

            // Animator 60 FPS
            FPSAnimator animator = new FPSAnimator(renderer.panel, 60, true);
            animator.start();

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    animator.stop();
                }
            });
        });
    }
}