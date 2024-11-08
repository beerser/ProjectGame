package dogfighter;

import javax.swing.*;
import java.awt.*;

public class StartScreenPanel extends JPanel {

    private int backgroundX = 0;
    private final int scrollSpeed = 12;
    private Timer backgroundTimer;

    public StartScreenPanel(GameScreen gameScreen) {
        setLayout(new BorderLayout());

        // Load dog GIF and resize it
        ImageIcon dogGifIcon = new ImageIcon("src\\Image\\Dog.gif"); // Update with correct path to your GIF file
        Image dogGifImage = dogGifIcon.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT); // Adjust size as needed
        JLabel dogLabel = new JLabel(new ImageIcon(dogGifImage));

        // Add the dog label to the center of the panel
        add(dogLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        buttonPanel.setOpaque(false);

        JButton startButton = new JButton(new ImageIcon("src\\Image\\bt_start.png"));
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> {
            stopBackgroundScrolling();
            gameScreen.showSkillSelectionScreen();
        });

        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startBackgroundScrolling();
    }

    private void startBackgroundScrolling() {
        backgroundTimer = new Timer(30, e -> {
            backgroundX -= scrollSpeed;
            repaint();
        });
        backgroundTimer.start();
    }

    private void stopBackgroundScrolling() {
        if (backgroundTimer != null) {
            backgroundTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundIcon = new ImageIcon("src\\Image\\Background_1.jpg");
        Image background = backgroundIcon.getImage();

        int imageWidth = background.getWidth(this);

        g.drawImage(background, backgroundX, 0, imageWidth, getHeight(), this);
        g.drawImage(background, backgroundX + imageWidth, 0, imageWidth, getHeight(), this);

        if (backgroundX <= -imageWidth) {
            backgroundX += imageWidth;
        }
    }
}
