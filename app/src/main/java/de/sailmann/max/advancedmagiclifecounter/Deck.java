package de.sailmann.max.advancedmagiclifecounter;

import java.io.Serializable;

/**
 * Created by Admin on 31.12.2016.
 */

public class Deck implements Serializable {
    private boolean colorless;
    private boolean white;
    private boolean blue;
    private boolean black;
    private boolean red;
    private boolean green;

    public Deck(boolean colorless, boolean white, boolean blue, boolean black, boolean red, boolean green) {
        this.colorless = colorless;
        this.white = white;
        this.blue = blue;
        this.black = black;
        this.red = red;
        this.green = green;
    }

    public boolean isValid() {
        return colorless || white || blue || black || red || green;
    }

    public boolean getColorless() {
        return this.colorless;
    }

    public boolean getWhite() {
        return this.white;
    }

    public boolean getBlue() {
        return this.blue;
    }

    public boolean getBlack() {
        return this.black;
    }

    public boolean getRed() {
        return this.red;
    }

    public boolean getGreen() {
        return this.green;
    }

}
