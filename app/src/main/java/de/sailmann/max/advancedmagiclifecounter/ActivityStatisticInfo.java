package de.sailmann.max.advancedmagiclifecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 12.01.2017.
 */

public class ActivityStatisticInfo extends AppCompatActivity {

    private Game game;
    private ArrayList<ViewGroup> players;
    private ArrayList<ViewGroup> textviews;
    int index = 0;

    private TextView tv_name;
    private TextView tv_life;
    private TextView tv_poison;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutstatisticinfo);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(dm.widthPixels, dm.heightPixels);

        game = ((Globals) this.getApplication()).getGame();

        initializeScreen();

    }

    public void initializeScreen() {
        ((TextView) findViewById(R.id.tv_lstatisticinfo_game)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_gameinfo)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_detail)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_result)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_player)).setTypeface(((Globals) this.getApplication()).getTypeface());

        tv_life = (TextView) findViewById(R.id.tv_lstatisticinfo_lifes);
        tv_name = (TextView) findViewById(R.id.tv_lstatisticinfo_names);
        tv_poison = (TextView) findViewById(R.id.tv_lstatisticinfo_poisons);
        tv_poison.setTypeface(((Globals) this.getApplication()).getTypeface());
        tv_name.setTypeface(((Globals) this.getApplication()).getTypeface());
        tv_life.setTypeface(((Globals) this.getApplication()).getTypeface());

        ((TextView) findViewById(R.id.tv_lstatisticinfo_name)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_poison)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatisticinfo_life)).setTypeface(((Globals) this.getApplication()).getTypeface());

        String sname = "";
        String spoison = "";
        String slife = "";

        for(Player p : game.getPlayers())
        {
            sname += p.getName() + "\n";
            spoison += p.getPoisonCounter() + "\n";
            slife += p.getLifePoints() + "\n";
        }

        tv_life.setText(slife);
        tv_poison.setText(spoison);
        tv_name.setText(sname);

        players = new ArrayList<>();
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player1));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player2));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player3));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player4));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player5));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player6));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player7));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player8));
        players.add((ViewGroup) findViewById(R.id.fl_lstatisticinfo_player9));

        textviews = new ArrayList<>();
        textviews.add((ViewGroup) findViewById(R.id.tv_lstatisticinfo_team1));
        textviews.add((ViewGroup) findViewById(R.id.tv_lstatisticinfo_team2));
        textviews.add((ViewGroup) findViewById(R.id.tv_lstatisticinfo_team3));
        textviews.add((ViewGroup) findViewById(R.id.tv_lstatisticinfo_team4));
        textviews.add((ViewGroup) findViewById(R.id.tv_lstatisticinfo_team5));

        for (ViewGroup viewGroup : players) {
            viewGroup.removeAllViews();
        }
        for (ViewGroup viewGroup : textviews) {
            viewGroup.removeAllViews();
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        long duration = game.getDuration();
        int seconds = (int) (duration / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        String details = DateFormat.getDateInstance().format(game.getStartTime()) + "\n" + simpleDateFormat.format(game.getStartTime()) + "\n" + simpleDateFormat.format(game.getEndTime()) + "\n" + String.format("%02d", hours%60) + ":" + String.format("%02d", minutes%60) + ":" + String.format("%02d", seconds%60) + "\n";

        switch (game.getGameMode()) {

            case Mode.MODE_VS:
                for (int i = 0; i < game.getPlayers().size(); i++) {

                    players.get(i).addView(getLayoutInflater().inflate(R.layout.fragment_playerstatistic, null));
                    ((TextView) players.get(i).findViewById(R.id.tv_fplayerstatistic_name)).setTypeface(((Globals) this.getApplication()).getTypeface());
                    ((TextView) players.get(i).findViewById(R.id.tv_fplayerstatistic_name)).setText(game.getPlayers().get(i).getName());
                    index = 0;
                    if (game.getPlayers().get(i).getDeck().isValid()) {
                        if (game.getPlayers().get(i).getDeck().getColorless()) {
                            setMagicSymbol(index, R.drawable.color_colorless, players.get(i));
                            index++;
                        }
                        if (game.getPlayers().get(i).getDeck().getWhite()) {
                            setMagicSymbol(index, R.drawable.color_white, players.get(i));
                            index++;
                        }
                        if (game.getPlayers().get(i).getDeck().getBlue()) {
                            setMagicSymbol(index, R.drawable.color_blue, players.get(i));
                            index++;
                        }
                        if (game.getPlayers().get(i).getDeck().getBlack()) {
                            setMagicSymbol(index, R.drawable.color_black, players.get(i));
                            index++;
                        }
                        if (game.getPlayers().get(i).getDeck().getRed()) {
                            setMagicSymbol(index, R.drawable.color_red, players.get(i));
                            index++;
                        }
                        if (game.getPlayers().get(i).getDeck().getGreen()) {
                            setMagicSymbol(index, R.drawable.color_green, players.get(i));
                            index++;
                        }
                    }
                }
                details += "VS.";
                break;
            case Mode.MODE_2v2:
                for (int i = 0; i < 4; i++) {
                    fillPlayers(i);
                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(1).addView(createTextView(getResources().getString(R.string.team2)));
                details += "2v2";
                break;
            case Mode.MODE_2v2v2:
                for (int i = 0; i < 6; i++) {
                    fillPlayers(i);
                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(1).addView(createTextView(getResources().getString(R.string.team2)));
                textviews.get(3).addView(createTextView(getResources().getString(R.string.team3)));
                details += "2v2v2";
                break;
            case Mode.MODE_2v2v2v2:
                for (int i = 0; i < 8; i++) {
                    fillPlayers(i);

                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(1).addView(createTextView(getResources().getString(R.string.team2)));
                textviews.get(3).addView(createTextView(getResources().getString(R.string.team3)));
                textviews.get(4).addView(createTextView(getResources().getString(R.string.team4)));
                details += "2v2v2v2";
                break;
            case Mode.MODE_3v3:
                for (int i = 0; i < 6; i++) {
                    fillPlayers(i);

                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(2).addView(createTextView(getResources().getString(R.string.team2)));
                details += "3v3";
                break;
            case Mode.MODE_3v3v3:
                for (int i = 0; i < 9; i++) {
                    fillPlayers(i);

                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(2).addView(createTextView(getResources().getString(R.string.team2)));
                textviews.get(4).addView(createTextView(getResources().getString(R.string.team3)));
                details += "3v3v3";
                break;
            case Mode.MODE_4v4:
                for (int i = 0; i < 8; i++) {
                    fillPlayers(i);
                }
                textviews.get(0).addView(createTextView(getResources().getString(R.string.team1)));
                textviews.get(3).addView(createTextView(getResources().getString(R.string.team2)));
                details += "4v4";
                break;
            default:

                break;
        }

        if(game.getTimeLimit()==0)
        {
            details+="\n--:--";
        }
        else
        {
            int s =(int)( game.getTimeLimit());
            int m = s/60;
            int h = m / 60;
            details+="\n" + String.format("%02d", h) + ":" + String.format("%02d", m%60);
        }


        ((TextView) findViewById(R.id.tv_lstatisticinfo_detail)).setText(details);


    }

    public void fillPlayers(int i) {

        players.get(i).addView(getLayoutInflater().inflate(R.layout.fragment_playerstatistic, null));
        ((TextView) players.get(i).findViewById(R.id.tv_fplayerstatistic_name)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) players.get(i).findViewById(R.id.tv_fplayerstatistic_name)).setText(game.getPlayerNames()[i]);

        if (game.getPlayerDecks()[i].isValid()) {
            fillDecks(i);
        }
    }

    public void fillDecks(int i) {

        index = 0;
        if (game.getPlayerDecks()[i].getColorless()) {
            setMagicSymbol(index, R.drawable.color_colorless, players.get(i));
            index++;
        }
        if (game.getPlayerDecks()[i].getWhite()) {
            setMagicSymbol(index, R.drawable.color_white, players.get(i));
            index++;
        }
        if (game.getPlayerDecks()[i].getBlue()) {
            setMagicSymbol(index, R.drawable.color_blue, players.get(i));
            index++;
        }
        if (game.getPlayerDecks()[i].getBlack()) {
            setMagicSymbol(index, R.drawable.color_black, players.get(i));
            index++;
        }
        if (game.getPlayerDecks()[i].getRed()) {
            setMagicSymbol(index, R.drawable.color_red, players.get(i));
            index++;
        }
        if (game.getPlayerDecks()[i].getGreen()) {
            setMagicSymbol(index, R.drawable.color_green, players.get(i));
            index++;
        }
    }

    public TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setTypeface(((Globals) this.getApplication()).getTypeface());
        textView.setPadding(0, 10, 0, 10);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void setMagicSymbol(int index, int drawable, View view) {
        ImageView imageView;
        switch (index) {
            case 0:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_1);
                break;
            case 1:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_2);
                break;
            case 2:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_3);
                break;
            case 3:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_4);
                break;
            case 4:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_5);
                break;
            case 5:
                imageView = (ImageView) view.findViewById(R.id.iv_fplayerstatistic_6);
                break;
            default:
                imageView = null;
                break;
        }
        if (imageView != null) {
            imageView.setImageResource(drawable);
        }
    }
}
