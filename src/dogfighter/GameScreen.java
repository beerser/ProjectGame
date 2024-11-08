package dogfighter;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameLogic gameLogic; // เพิ่ม GameLogic

    public GameScreen() {
        setTitle("Dog Adventure Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // สร้างอินสแตนซ์ของ GameLogic
        gameLogic = new GameLogic();

        // Create start screen, skill selection screen, and status screen
        StartScreenPanel startScreen = new StartScreenPanel(this);
        SkillSelectionPanel skillSelectionScreen = new SkillSelectionPanel(this, gameLogic); // ส่ง GameLogic ไปยัง SkillSelectionPanel
        StatusPanel statusPanel = new StatusPanel(this, gameLogic); // ส่ง GameLogic ไปยัง StatusPanel

        // Add screens to the main panel with CardLayout
        mainPanel.add(startScreen, "StartScreen");
        mainPanel.add(skillSelectionScreen, "SkillSelectionScreen");
        mainPanel.add(statusPanel, "StatusPanel");

        add(mainPanel);
        cardLayout.show(mainPanel, "StartScreen"); // Show the start screen initially
    }

    public void showSkillSelectionScreen() {
        cardLayout.show(mainPanel, "SkillSelectionScreen");
    }

    public void showStatusScreen() {
        cardLayout.show(mainPanel, "StatusPanel");
    }
    public void showStartScreen() {
    cardLayout.show(mainPanel, "StartScreen");
}
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameScreen gameScreen = new GameScreen();
            gameScreen.setVisible(true);
        });
    }
}
