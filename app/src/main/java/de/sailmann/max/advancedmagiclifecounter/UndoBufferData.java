package de.sailmann.max.advancedmagiclifecounter;

/**
 * Created by Admin on 09.01.2017.
 */

public class UndoBufferData {

    private int playerNumber;
    private String playerName;

    private int lifeCounterOld;
    private int lifeCounterNew;

    private int poisonCounterOld;
    private int poisonCounterNew;

    private int energyCounterOld;
    private int energyCounterNew;

    public UndoBufferData(int playerNumber, String playerName, int lifeCounterOld, int lifeCounterNew, int poisonCounterOld, int poisonCounterNew, int energyCounterOld, int energyCounterNew) {
        this.playerNumber = playerNumber;
        this.lifeCounterOld = lifeCounterOld;
        this.lifeCounterNew = lifeCounterNew;
        this.poisonCounterOld = poisonCounterOld;
        this.poisonCounterNew = poisonCounterNew;
        this.energyCounterOld = energyCounterOld;
        this.energyCounterNew = energyCounterNew;
        this.playerName = playerName;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public int getLifeCounterOld() {
        return this.lifeCounterOld;
    }

    public int getLifeCounterNew() {
        return this.lifeCounterNew;
    }

    public int getPoisonCounterOld() {
        return this.poisonCounterOld;
    }

    public int getPoisonCounterNew() {
        return this.poisonCounterNew;
    }

    public int getEnergyCounterOld() {
        return this.energyCounterOld;
    }

    public int getEnergyCounterNew() {
        return this.energyCounterNew;
    }

    public String getPlayerName()
    {
        return this.playerName;
    }
}
