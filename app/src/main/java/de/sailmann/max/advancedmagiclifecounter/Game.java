package de.sailmann.max.advancedmagiclifecounter;


import android.util.Log;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 31.12.2016.
 */

public class Game implements Serializable {
    private ArrayList<Player> players;
    private int startLifePoints;
    private long timeLimit;
    private int gameMode;
    private Date startTime;
    private Date endTime;
    private int[] colores;

    public Game(ArrayList<Player> players, int startLifePoints, long timeLimit, int gameMode) {
        this.players = players;
        this.startLifePoints = startLifePoints;
        this.timeLimit = timeLimit;
        this.gameMode = gameMode;

        startTime = new Date();

        for (Player p : players) {
            p.setLifePoints(startLifePoints);
            p.setEnergyCounter(0);
            p.setPoisonCounter(0);
        }
        setColores();
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getGameMode() {
        return this.gameMode;
    }

    public void setEndTime() {
        endTime = new Date();
    }

    public int getStartLifePoints() {
        return this.startLifePoints;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    private void setColores() {

        int colorless = 0;
        int white = 0;
        int blue = 0;
        int black = 0;
        int red = 0;
        int green = 0;
        for (Player p : players) {
            colorless += p.getColorless();
            white += p.getWhite();
            blue += p.getBlue();
            black += p.getBlack();
            red += p.getRed();
            green += p.getGreen();

        }
        colores = new int[]{colorless, white, blue, black, red, green};
    }

    public int[] getColores() {
        return colores;
    }

    public long getDuration() {

        Log.d(this.getClass().getSimpleName(), String.valueOf(endTime.getTime() - startTime.getTime()));
        return (endTime.getTime() - startTime.getTime());


    }

    public String[] getPlayerNames() {
        String[] s;
        switch (gameMode) {
            case Mode.MODE_2v2:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(1).getNames()[0], players.get(1).getNames()[1]};
                break;
            case Mode.MODE_2v2v2:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(1).getNames()[0], players.get(1).getNames()[1], players.get(2).getNames()[0], players.get(2).getNames()[1]};
                break;
            case Mode.MODE_2v2v2v2:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(1).getNames()[0], players.get(1).getNames()[1], players.get(2).getNames()[0], players.get(2).getNames()[1], players.get(3).getNames()[0], players.get(3).getNames()[1]};
                break;
            case Mode.MODE_3v3:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(0).getNames()[2], players.get(1).getNames()[0], players.get(1).getNames()[1], players.get(1).getNames()[2]};
                break;
            case Mode.MODE_3v3v3:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(0).getNames()[2], players.get(1).getNames()[0], players.get(1).getNames()[1], players.get(1).getNames()[2], players.get(2).getNames()[0], players.get(2).getNames()[1], players.get(2).getNames()[2]};
                break;
            case Mode.MODE_4v4:
                s = new String[]{players.get(0).getNames()[0], players.get(0).getNames()[1], players.get(0).getNames()[2], players.get(0).getNames()[3], players.get(1).getNames()[0], players.get(1).getNames()[1], players.get(1).getNames()[2], players.get(1).getNames()[3]};
                break;
            default:
                s = null;
                break;
        }
        return s;
    }

    public Deck[] getPlayerDecks() {
        Deck[] d;
        switch (gameMode) {
            case Mode.MODE_2v2:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(1).getDecks()[0], players.get(1).getDecks()[1]};
                break;
            case Mode.MODE_2v2v2:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(1).getDecks()[0], players.get(1).getDecks()[1], players.get(2).getDecks()[0], players.get(2).getDecks()[1]};
                break;
            case Mode.MODE_2v2v2v2:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(1).getDecks()[0], players.get(1).getDecks()[1], players.get(2).getDecks()[0], players.get(2).getDecks()[1], players.get(3).getDecks()[0], players.get(3).getDecks()[1]};
                break;
            case Mode.MODE_3v3:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(0).getDecks()[2], players.get(1).getDecks()[0], players.get(1).getDecks()[1], players.get(1).getDecks()[2]};
                break;
            case Mode.MODE_3v3v3:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(0).getDecks()[2], players.get(1).getDecks()[0], players.get(1).getDecks()[1], players.get(1).getDecks()[2], players.get(2).getDecks()[0], players.get(2).getDecks()[1], players.get(2).getDecks()[2]};
                break;
            case Mode.MODE_4v4:
                d = new Deck[]{players.get(0).getDecks()[0], players.get(0).getDecks()[1], players.get(0).getDecks()[2], players.get(0).getDecks()[3], players.get(1).getDecks()[0], players.get(1).getDecks()[1], players.get(1).getDecks()[2], players.get(1).getDecks()[3]};
                break;
            default:
                d = null;
                break;
        }
        return d;
    }




}
