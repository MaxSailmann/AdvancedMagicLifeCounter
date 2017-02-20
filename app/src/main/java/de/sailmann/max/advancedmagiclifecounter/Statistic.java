package de.sailmann.max.advancedmagiclifecounter;

import java.util.ArrayList;

/**
 * Created by Admin on 31.12.2016.
 */

public class Statistic {
    private ArrayList<Game> games;

    public Statistic() {
        games = new ArrayList<>();
    }

    public void add(Game game) {
        games.add(game);
    }

    public int getNumberOfGames() {
        return games.size();

    }

    public ArrayList<Game> getGames() {
        return this.games;
    }

    public void deleteGames() {
        games = null;
        games = new ArrayList<>();
    }
}
