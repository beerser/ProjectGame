package dogfighter;

import java.util.Random;

public class GameLogic {
    private int dogHP = 50;
    private int dogATK = 1;
    private int dogDEF = 10;

    private int monsterHP;
    private int monsterATK;
    private int monsterDEF;
    private boolean isBoss = false;
    private boolean gameEnded = false;

    private int turnCount = 1;
    private Random random = new Random();
    

    public GameLogic() {
        setupMonster(false);
        // เริ่มด้วยมอนสเตอร์ธรรมดา
    }

    // เพิ่มพลังโจมตีเป็นเปอร์เซ็นต์
    public void increaseAttackByPercentage(int percentage) {
        dogATK += (dogATK * percentage) / 100;
    }

    // เพิ่มเลือดเป็นเปอร์เซ็นต์
    public void increaseHealthByPercentage(int percentage) {
        int maxHealth = 50000; // เลือดสูงสุด
        dogHP = Math.min(maxHealth, dogHP + (dogHP * percentage) / 100);
    }

    // เพิ่มพลังป้องกันเป็นเปอร์เซ็นต์
    public void increaseDefenseByPercentage(int percentage) {
        dogDEF += (dogDEF * percentage) / 100;
    }

    // Getters
    public int getDogHP() { return dogHP; }
    public int getDogATK() { return dogATK; }
    public int getDogDEF() { return dogDEF; }
    public int getTurnCount() { return turnCount; }
    
    public int getMonsterHP() { return monsterHP; }
    public int getMonsterATK() { return monsterATK; }
    public int getMonsterDEF() { return monsterDEF; }

    public void performAttack() {
        if (gameEnded) return;

        int damageToMonster = Math.max(0, dogATK - monsterDEF);
        monsterHP -= damageToMonster;

        if (monsterHP < 0) {
            monsterHP = 0;
        }

        if (monsterHP > 0) {
            int damageToDog = Math.max(0, monsterATK - dogDEF);
            dogHP -= damageToDog;

            if (dogHP < 0) {
                dogHP = 0;
            }
        }
    }
     public boolean isBoss() {
        return isBoss;
    }

    public boolean isMonsterDefeated() {
        return monsterHP <= 0;
    }

    public boolean isDogDefeated() {
        return dogHP <= 0;
    }

    public boolean isGameWon() {
        return turnCount >= 20;
    }

    public void setupNextTurn() {
        if (gameEnded) return;
        turnCount++;
    }

    public String triggerSpecialEvent() {
        if (isGameWon()) {
            gameEnded = true;
            return "Congratulations! You have won the game!";
        }

        if (isDogDefeated()) {
            gameEnded = true;
            return "Game Over! Dog has been defeated.";
        }

        int eventType = random.nextInt(3);
        switch (eventType) {
            case 0:
                setupMonster(false); 
                return "A wild monster appears!";
            case 1:
                return "You found a skill upgrade opportunity!";
            case 2:
                int healAmount = 100;
                dogHP =dogHP + healAmount;
                return "You found a healing potion! HP +100";
            default:
                return "";
        }
    }

    public void setupMonster(boolean isBoss) {
        if (gameEnded) return;
        this.isBoss = isBoss;
        if (isBoss) {
            monsterHP = 1000;
            monsterATK = 50;
            monsterDEF = 20;
        } else {
            monsterHP = 150 + ((turnCount%2) * 2);
            monsterATK = 10 + ((turnCount%2) * 2);
            monsterDEF = 10;
        }
    }
}
