package Classes.Threads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SemaphoreSimulator extends JFrame {

    private JLabel semaphoreLabel;
    private SemaphoreThread semaphoreThread;
    private ImageIcon redIcon, yellowIcon, greenIcon;

    public SemaphoreSimulator() {
        setTitle("Semaphore Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        redIcon = new ImageIcon("assets/semaforo_vermelho.png");
        yellowIcon = new ImageIcon("assets/semaforo_amarel.png");
        greenIcon = new ImageIcon("assets/semaforo_verde.png");

        semaphoreLabel = new JLabel(redIcon);
        add(semaphoreLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (semaphoreThread == null || !semaphoreThread.isAlive()) {
                    semaphoreThread = new SemaphoreThread();
                    semaphoreThread.start();
                }
            }
        });
        add(startButton, BorderLayout.SOUTH);
    }

    private class SemaphoreThread extends Thread {
        private final ImageIcon[] icons = {redIcon, yellowIcon, greenIcon};
        private int currentIndex = 2;

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        semaphoreLabel.setIcon(icons[currentIndex]);
                    }
                });

                try {
                    Thread.sleep(2000);  // Tempo para cada cor do semáforo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                currentIndex = (currentIndex + 1) % icons.length;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SemaphoreSimulator app = new SemaphoreSimulator();
                app.setVisible(true);
            }
        });
    }
}
