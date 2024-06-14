package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas {
    private JFrame frame;

    public Display(int width, int height, String title) {
        frame = new JFrame(title);
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);

        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if  (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();

        // Clear Screen
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, getWidth(), getHeight());

        // Draw something
        graphics.setColor(Color.WHITE);
        graphics.drawString("Hello, Game Engine!", 100, 100);

        graphics.dispose();
        bufferStrategy.show();
    }
}
