package de.sailmann.max.advancedmagiclifecounter;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Admin on 31.12.2016.
 */

public class Player implements Serializable {
    private String name;
    private Deck deck;

    private String[] names;
    private Deck[] decks;

    private int lifePoints;
    private int poisonCounter;
    private int energyCounter;

    private int[] manaCounter;

    private int[] color;

    public Player(String name, Deck deck) {
        Log.d(this.getClass().getSimpleName(), name);
        if (name.length() == 0) {
            this.name = "???";
        } else {
            this.name = name;
        }
        this.deck = deck;
        this.manaCounter = new int[]{0, 0, 0, 0, 0, 0};
        this.color = new int[]{0, 0, 0, 0, 0, 0};
        if (deck.getColorless()) {
            color[0]++;
        }
        if (deck.getWhite()) {
            color[1]++;
        }
        if (deck.getBlue()) {
            color[2]++;
        }
        if (deck.getBlack()) {
            color[3]++;
        }
        if (deck.getRed()) {
            color[4]++;
        }
        if (deck.getGreen()) {
            color[5]++;
        }
        Log.d(this.getClass().getSimpleName(), "Colorless: " + color[0] + " White: " + color[1]);
    }

    public Player(String[] names, Deck[] decks) {
        this.names = names;
        this.decks = decks;
        String n = "";
        for (String s : names) {
            for (int i = 0; i < s.length(); i++) {
                if (s.length() > 0) {
                    n += String.valueOf(s.charAt(i));
                }
                if (s.length() == 0) {
                    n += "???";
                }
                if (i >= 2) {
                    break;
                }
            }
        }
        this.color = new int[]{0, 0, 0, 0, 0, 0};
        for (Deck d : decks) {

            if (d.getColorless()) {
                color[0]++;
            }
            if (d.getWhite()) {
                color[1]++;
            }
            if (d.getBlue()) {
                color[2]++;
            }
            if (d.getBlack()) {
                color[3]++;
            }
            if (d.getRed()) {
                color[4]++;
            }
            if (d.getGreen()) {
                color[5]++;
            }
        }
        this.name = n;
        this.manaCounter = new int[]{0, 0, 0, 0, 0, 0};
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void setLifePoints(boolean addition, int change) {
        if (addition) {
            lifePoints += change;
        } else {
            lifePoints -= change;
            if (lifePoints < 0) {
                lifePoints = 0;
            }
        }
    }


    public void setPoisonCounter(int poisonCounter) {
        this.poisonCounter = poisonCounter;
    }

    public void setPoisonCounter(boolean addition, int change) {
        if (addition) {
            poisonCounter += change;
        } else {
            poisonCounter -= change;
            if (poisonCounter < 0) {
                poisonCounter = 0;
            }
        }
    }

    public void setEnergyCounter(int energyCounter) {
        this.energyCounter = energyCounter;
    }

    public void setEnergyCounter(boolean addition, int change) {
        if (addition) {
            energyCounter += change;
        } else {
            energyCounter -= change;
            if (energyCounter < 0) {
                energyCounter = 0;
            }
        }
    }


    public void setColorlessCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[0] += change;
        } else {
            manaCounter[0] -= change;
            if (manaCounter[0] < 0) {
                manaCounter[0] = 0;
            }
        }
    }

    public void setWhiteCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[1] += change;
        } else {
            manaCounter[1] -= change;
            if (manaCounter[1] < 0) {
                manaCounter[1] = 0;
            }
        }
    }

    public void setBlueCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[2] += change;
        } else {
            manaCounter[2] -= change;
            if (manaCounter[2] < 0) {
                manaCounter[2] = 0;
            }
        }
    }

    public void setBlackCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[3] += change;
        } else {
            manaCounter[3] -= change;
            if (manaCounter[3] < 0) {
                manaCounter[3] = 0;
            }
        }
    }

    public void setRedCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[4] += change;
        } else {
            manaCounter[4] -= change;
            if (manaCounter[4] < 0) {
                manaCounter[4] = 0;
            }
        }
    }

    public void setGreenCounter(boolean addition, int change) {
        if (addition) {
            manaCounter[5] += change;
        } else {
            manaCounter[5] -= change;
            if (manaCounter[5] < 0) {
                manaCounter[5] = 0;
            }
        }
    }

    public void deleteManaCounter() {

        for (int i = 0; i < manaCounter.length; i++) {
            manaCounter[i] = 0;
        }
    }

    public String getName() {
        return this.name;
    }

    public String[] getNames() {
        return this.names;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getPoisonCounter() {
        return poisonCounter;
    }

    public int getEnergyCounter() {
        return energyCounter;
    }

    public int getColorlessCounter() {
        return manaCounter[0];
    }

    public int getWhiteCounter() {
        return manaCounter[1];
    }

    public int getBlueCounter() {
        return manaCounter[2];
    }

    public int getBlackCounter() {
        return manaCounter[3];
    }

    public int getRedCounter() {
        return manaCounter[4];
    }

    public int getGreenCounter() {
        return manaCounter[5];
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck[] getDecks() {
        return this.decks;
    }

    public int getColorless() {
        return color[0];
    }

    public int getWhite() {
        return color[1];
    }

    public int getBlue() {
        return color[2];
    }

    public int getBlack() {
        return color[3];
    }

    public int getRed() {
        return color[4];
    }

    public int getGreen() {
        return color[5];
    }
}





