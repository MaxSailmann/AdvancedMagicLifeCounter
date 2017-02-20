package de.sailmann.max.advancedmagiclifecounter;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Admin on 31.12.2016.
 */

public class Globals extends Application {
    private Statistic statistic = null;
    private Game game = null;

    private Typeface typeface;

    public Statistic getStatistic()
    {
        return statistic;
    }
    public void setStatistic(Statistic statistic)
    {
        this.statistic = statistic;
    }

    public Typeface getTypeface()
    {
        return typeface;
    }
    public void setTypeface(Typeface typeface)
    {
        this.typeface = typeface;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
    public Game getGame()
    {
        return this.game;
    }

}
