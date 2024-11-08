package dogfighter;

import javax.swing.*;
import java.awt.*;

public class SkillSelectionPanel extends JPanel {

    private GameScreen gameScreen;
    private GameLogic gameLogic;

    public SkillSelectionPanel(GameScreen gameScreen, GameLogic gameLogic) {
        this.gameScreen = gameScreen;
        this.gameLogic = gameLogic;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel dogLabel = new JLabel(new ImageIcon("src\\Image\\Dog.png"));
        dogLabel.setHorizontalAlignment(JLabel.CENTER);
        add(dogLabel, BorderLayout.NORTH);

        JPanel skillPanel = new JPanel();
        skillPanel.setLayout(new BoxLayout(skillPanel, BoxLayout.Y_AXIS));
        skillPanel.setOpaque(false);

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Dimension buttonSize = new Dimension(500, 100);
        Color buttonColor = new Color(255, 140, 0); // สีส้ม

        // ใช้ glue เพื่อให้ skillPanel จัดเรียงปุ่มให้อยู่ตรงกลางในแนวตั้ง
        skillPanel.add(Box.createVerticalGlue());

        // ปุ่มเพิ่มพลังโจมตี
        JButton attackBoostButton = new JButton("Increase Attack Power by 10%");
        setupButton(attackBoostButton, buttonFont, buttonSize, buttonColor);
        attackBoostButton.addActionListener(e -> {
            gameLogic.increaseAttackByPercentage(10);
            gameScreen.showStatusScreen();
        });
        skillPanel.add(attackBoostButton);

        skillPanel.add(Box.createVerticalStrut(15)); // เว้นระยะห่างระหว่างปุ่ม

        // ปุ่มเพิ่มเลือด
        JButton healButton = new JButton("Heal by 10%");
        setupButton(healButton, buttonFont, buttonSize, buttonColor);
        healButton.addActionListener(e -> {
            gameLogic.increaseHealthByPercentage(10);
            gameScreen.showStatusScreen();
        });
        skillPanel.add(healButton);

        skillPanel.add(Box.createVerticalStrut(15));

        // ปุ่มเพิ่มพลังป้องกัน
        JButton defenseBoostButton = new JButton("Increase Defense by 10%");
        setupButton(defenseBoostButton, buttonFont, buttonSize, buttonColor);
        defenseBoostButton.addActionListener(e -> {
            gameLogic.increaseDefenseByPercentage(10);
            gameScreen.showStatusScreen();
        });
        skillPanel.add(defenseBoostButton);

        skillPanel.add(Box.createVerticalGlue());

        add(skillPanel, BorderLayout.CENTER);
    }

    private void setupButton(JButton button, Font font, Dimension size, Color color) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMaximumSize(size); // ให้ทุกปุ่มมีขนาดเท่ากัน
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // จัดให้ปุ่มอยู่กลางแนวนอน
    }
}
