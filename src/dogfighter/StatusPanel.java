package dogfighter;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

    private int backgroundX = 0;
    private final int scrollSpeed = 12;
    private Image backgroundImage;
    private Image dogImage;
    private Image monsterImage;
    private Image bossImage;
    private Image defeatedImage;
    private Image showVictoryImage;
    

    private JLabel dogLabel;
    private JLabel monsterLabel;
    private JLabel dogHPLabel;
    private JLabel dogATKLabel;
    private JLabel dogDEFLabel;
    private JLabel monsterHPLabel;
    private JLabel monsterATKLabel;
    private JLabel monsterDEFLabel;
    private JLabel messageLabel;
    private JButton attackButton;
    private JLabel turnLabel;
    private JButton backButton;

    private GameLogic gameLogic;
    private GameScreen gameScreen;

    public StatusPanel(GameScreen gameScreen, GameLogic gameLogic) {
        this.gameScreen = gameScreen;
        this.gameLogic = gameLogic;
        loadImages();
        setupUIComponents();
        startBackgroundScroll();
    }

   private void loadImages() {
    backgroundImage = new ImageIcon("src\\Image\\Background_1.jpg").getImage();
    dogImage = new ImageIcon("src\\Image\\Dog.gif").getImage().getScaledInstance(400, 350, Image.SCALE_DEFAULT);
    monsterImage = new ImageIcon("src\\Image\\Monster1.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    bossImage = new ImageIcon("src\\Image\\Monster2.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    defeatedImage = new ImageIcon("src\\Image\\lose.png").getImage();
    showVictoryImage = new ImageIcon ("src\\Image\\win.png").getImage();
}

private void setupUIComponents() {
    setLayout(new BorderLayout());
    setBackground(Color.DARK_GRAY);

    // Initialize turn label
    turnLabel = new JLabel("Turn: " + gameLogic.getTurnCount());
    turnLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Adjust the font size as needed
    turnLabel.setHorizontalAlignment(JLabel.CENTER);

    // Add the turn label to the top of the panel
    add(turnLabel, BorderLayout.NORTH);

    // Font for status labels
    Font statusFont = new Font("Arial", Font.BOLD, 20);

    // Dog's stats panel
    JPanel dogStatsPanel = new JPanel(new FlowLayout());
    dogHPLabel = new JLabel("HP: " + gameLogic.getDogHP());
    dogATKLabel = new JLabel("ATK: " + gameLogic.getDogATK());
    dogDEFLabel = new JLabel("DEF: " + gameLogic.getDogDEF());
    dogHPLabel.setFont(statusFont);
    dogATKLabel.setFont(statusFont);
    dogDEFLabel.setFont(statusFont);
    dogStatsPanel.add(dogHPLabel);
    dogStatsPanel.add(dogATKLabel);
    dogStatsPanel.add(dogDEFLabel);

    // Dog image and stats
    dogLabel = new JLabel(new ImageIcon(dogImage));
    JPanel dogPanel = new JPanel(new BorderLayout());
    dogPanel.setOpaque(false);
    dogPanel.add(dogLabel, BorderLayout.CENTER);
    dogPanel.add(dogStatsPanel, BorderLayout.SOUTH);

    // Monster's stats panel
    JPanel monsterStatsPanel = new JPanel(new FlowLayout());
    monsterHPLabel = new JLabel("HP: " + gameLogic.getMonsterHP());
    monsterATKLabel = new JLabel("ATK: " + gameLogic.getMonsterATK());
    monsterDEFLabel = new JLabel("DEF: " + gameLogic.getMonsterDEF());
    monsterHPLabel.setFont(statusFont);
    monsterATKLabel.setFont(statusFont);
    monsterDEFLabel.setFont(statusFont);
    monsterStatsPanel.add(monsterHPLabel);
    monsterStatsPanel.add(monsterATKLabel);
    monsterStatsPanel.add(monsterDEFLabel);

    // Monster image and stats
    monsterLabel = new JLabel(new ImageIcon(monsterImage));
    JPanel monsterPanel = new JPanel(new BorderLayout());
    monsterPanel.setOpaque(false);
    monsterPanel.add(monsterLabel, BorderLayout.CENTER);
    monsterPanel.add(monsterStatsPanel, BorderLayout.SOUTH);

    // Battle panel for dog and monster
    JPanel battlePanel = new JPanel(new GridLayout(1, 2));
    battlePanel.setOpaque(false);
    battlePanel.add(dogPanel);
    battlePanel.add(monsterPanel);
    add(battlePanel, BorderLayout.CENTER);

    // Attack button
    attackButton = new JButton("Attack");
    attackButton.setFont(new Font("Arial", Font.BOLD, 35));
    attackButton.setPreferredSize(new Dimension(150, 100));
    attackButton.setBackground(new Color(190, 167, 37));
    attackButton.addActionListener(e -> performTurn());

    // Message label
    messageLabel = new JLabel("Prepare to battle!", SwingConstants.CENTER);
    messageLabel.setFont(new Font("Arial", Font.PLAIN, 35));
    JPanel footerPanel = new JPanel(new BorderLayout());
    footerPanel.add(messageLabel, BorderLayout.CENTER);
    footerPanel.add(attackButton, BorderLayout.SOUTH);
    add(footerPanel, BorderLayout.SOUTH);
}


    private void startBackgroundScroll() {
        Timer timer = new Timer(30, e -> {
            backgroundX -= scrollSpeed;
            if (backgroundX <= -backgroundImage.getWidth(null)) {
                backgroundX = 0;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageWidth = backgroundImage.getWidth(this);
        g.drawImage(backgroundImage, backgroundX, 0, imageWidth, getHeight(), this);
        g.drawImage(backgroundImage, backgroundX + imageWidth, 0, imageWidth, getHeight(), this);
    }

    private void performTurn() {
        gameLogic.performAttack();
        if (gameLogic.getMonsterHP() <= 0) {
            if (gameLogic.isGameWon()) { 
                showVictoryScreen(); 
            } else {
                messageLabel.setText("You defeated the monster!");
                setupNextTurn();
            }
        } else if (gameLogic.isDogDefeated()) {
            showDefeatedScreen();
        } else {
            updateStatusDisplay();
        }
    }

    private void showDefeatedScreen() {
        removeAll();
        JPanel defeatedPanel = new JPanel(new BorderLayout());
        defeatedPanel.setOpaque(false);

        // Label สำหรับแสดงภาพแพ้
        JLabel defeatedLabel = new JLabel(new ImageIcon(defeatedImage));
        defeatedLabel.setHorizontalAlignment(JLabel.CENTER);
        defeatedPanel.add(defeatedLabel, BorderLayout.CENTER);

        // ปุ่ม Back
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.addActionListener(e -> gameScreen.showStartScreen());
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 140, 0));
        defeatedPanel.add(backButton, BorderLayout.SOUTH);

        // เพิ่ม defeatedPanel เข้า StatusPanel
        add(defeatedPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    private void showVictoryScreen() {
        // ลบคอมโพเนนต์ที่มีอยู่ทั้งหมด
        removeAll();

        // สร้าง panel สำหรับภาพชัยชนะและปุ่ม next
        JPanel victoryPanel = new JPanel(new BorderLayout());
        victoryPanel.setOpaque(false);

        // Label สำหรับแสดงภาพชัยชนะ
        JLabel victoryLabel = new JLabel(new ImageIcon("src\\Image\\win.png")); // ตรวจสอบ path ให้ถูกต้อง
        victoryLabel.setHorizontalAlignment(JLabel.CENTER);
        victoryPanel.add(victoryLabel, BorderLayout.CENTER);

        // ปุ่ม Next
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 30));
        nextButton.addActionListener(e -> gameScreen.showStartScreen()); // เรียกหน้าเริ่มต้น
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.setBackground(new Color(255, 140, 0));
        victoryPanel.add(nextButton, BorderLayout.SOUTH);

        // เพิ่ม victoryPanel เข้า StatusPanel
        add(victoryPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }




    private void setupMonsterDisplay() {
    if (gameLogic.isBoss()) {
        monsterLabel.setIcon(new ImageIcon(bossImage));
    } else {
        monsterLabel.setIcon(new ImageIcon(monsterImage));
    }
    attackButton.setEnabled(true);
}

    private void setupNextTurn() {
        gameLogic.setupNextTurn();
        if (gameLogic.getTurnCount() % 10 == 0) {
            gameLogic.setupMonster(true);
            messageLabel.setText("A Boss monster appears!");
        } else {
            String eventMessage = gameLogic.triggerSpecialEvent();
            messageLabel.setText(eventMessage);
            if (eventMessage.contains("skill upgrade")) {
                gameScreen.showSkillSelectionScreen();
            }
        }
        monsterLabel.setVisible(true);
        setupMonsterDisplay();
        updateStatusDisplay();
    }

    private void updateStatusDisplay() {
        dogHPLabel.setText("HP: " + gameLogic.getDogHP());
        dogATKLabel.setText("ATK: " + gameLogic.getDogATK());
        dogDEFLabel.setText("DEF: " + gameLogic.getDogDEF());
        monsterHPLabel.setText("HP: " + gameLogic.getMonsterHP());
        monsterATKLabel.setText("ATK: " + gameLogic.getMonsterATK());
        monsterDEFLabel.setText("DEF: " + gameLogic.getMonsterDEF());
        turnLabel.setText("Turn: " + gameLogic.getTurnCount());
        if (gameLogic.getMonsterHP() <= 0) {
            monsterLabel.setVisible(false);
        } else {
            monsterLabel.setVisible(true);
        }
    }
}
