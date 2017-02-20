package de.sailmann.max.advancedmagiclifecounter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Admin on 31.12.2016.
 */

public class ActivitySettings extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private final int START_LIFEPOINTS = 20;
    private final int DEFAULT_NUMBEROFPLAYERS = 2;
    private final int MAX_NUMBEROFPLAYERS = 9;
    private final int MAX_NUMBEROFCOLORES = 6;

    private boolean numberOfPlayerChanged = false;

    private TextView textView_life;
    private TextView textView_player;
    private TextView textView_time;

    private int numberOfPlayers;
    private long time;
    private int startLifepoints;
    private int mode;
    private int playerSize;

    private boolean doublePressExit = false;
    private boolean doublePressShuffle = false;

    private RadioGroup radioGroup;
    private RadioButton radioButtonVS;
    private RadioButton radioButton2v2;
    private RadioButton radioButton2v2v2;
    private RadioButton radioButton3v3;
    private RadioButton radioButton2v2v2v2;
    private RadioButton radioButton4v4;
    private RadioButton radioButton3v3v3;

    private Button buttonShuffle;
    private Button buttonStart;

    private Game game;

    private String[] nameBuffer;
    private Boolean[][] colorBuffer;

    private ArrayList<ViewGroup> players;
    private ArrayList<ViewGroup> textviews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutsettings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initializeScreen();
        setRadioGroupGameMode();
        setPlayerFragments();
        setTextViewFragments(Mode.MODE_VS);
        controlSeekbars();
    }

    public void initializeScreen() {

        time = 0;
        numberOfPlayers = DEFAULT_NUMBEROFPLAYERS;
        startLifepoints = START_LIFEPOINTS;
        // numberOfPlayers: Anzahl der Mitspieler, wird über Seekbar geändert
        // playerSize: BufferVariable. Wenn die Spielerzahl mit der Seekbar geändert wird, müssen alle
        // vorher existierenden Namen und Decks in den Buffer geschrieben werden. Erst nach dem Buffern wird
        // playerSize der tatsächlichen Anzahl an Spielern wieder angepasst
        playerSize = DEFAULT_NUMBEROFPLAYERS;

        textView_life = (TextView) findViewById(R.id.tv_lsettings_life);
        textView_player = (TextView) findViewById(R.id.tv_lsettings_numberofplayer);
        textView_time = (TextView) findViewById(R.id.tv_lsettings_duration);

        textView_life.setTypeface(((Globals) this.getApplication()).getTypeface());
        textView_player.setTypeface(((Globals) this.getApplication()).getTypeface());
        textView_time.setTypeface(((Globals) this.getApplication()).getTypeface());

        ((TextView) findViewById(R.id.tv_lsettings_lifepoints)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lsettings_mode)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lsettings_time)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lsettings_player)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lgame_playername)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lgame_deckcolor)).setTypeface(((Globals) this.getApplication()).getTypeface());

        radioGroup = (RadioGroup) findViewById(R.id.rg_lsettings_mode);
        radioGroup.setOnCheckedChangeListener(this);

        radioButtonVS = new RadioButton(this);
        radioButtonVS.setText("VS-Match");
        radioButtonVS.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButtonVS.setId(R.id.radiobutton_vs);

        radioButton2v2 = new RadioButton(this);
        radioButton2v2.setText("2 vs 2");
        radioButton2v2.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton2v2.setId(R.id.radiobutton_2v2);

        radioButton2v2v2 = new RadioButton(this);
        radioButton2v2v2.setText("2 vs 2 vs 2");
        radioButton2v2v2.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton2v2v2.setId(R.id.radiobutton_2v2v2);

        radioButton3v3 = new RadioButton(this);
        radioButton3v3.setText("3 vs 3");
        radioButton3v3.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton3v3.setId(R.id.radiobutton_3v3);

        radioButton2v2v2v2 = new RadioButton(this);
        radioButton2v2v2v2.setText("2 vs 2 vs 2 vs 2");
        radioButton2v2v2v2.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton2v2v2v2.setId(R.id.radiobutton_2v2v2v2);

        radioButton4v4 = new RadioButton(this);
        radioButton4v4.setText("4 vs 4");
        radioButton4v4.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton4v4.setId(R.id.radiobutton_4v4);

        radioButton3v3v3 = new RadioButton(this);
        radioButton3v3v3.setText("3 vs 3 vs3");
        radioButton3v3v3.setTypeface(((Globals) this.getApplication()).getTypeface());
        radioButton3v3v3.setId(R.id.radiobutton_3v3v3);

        buttonShuffle = (Button) findViewById(R.id.b_lsettings_shuffle);
        buttonShuffle.setOnClickListener(this);
        buttonShuffle.setTypeface(((Globals) this.getApplication()).getTypeface());
        buttonShuffle.setVisibility(View.INVISIBLE);

        buttonStart = (Button) findViewById(R.id.b_lsettings_start);
        buttonStart.setTypeface(((Globals) this.getApplication()).getTypeface());
        buttonStart.setOnClickListener(this);

        nameBuffer = new String[MAX_NUMBEROFPLAYERS];
        for (int i = 0; i < nameBuffer.length; i++) {
            nameBuffer[i] = "Player" + (i + 1);
        }
        colorBuffer = new Boolean[MAX_NUMBEROFPLAYERS][MAX_NUMBEROFCOLORES];
        for (int n = 0; n < MAX_NUMBEROFPLAYERS; n++) {
            for (int i = 0; i < MAX_NUMBEROFCOLORES; i++) {
                colorBuffer[n][i] = false;
            }
        }

        players = new ArrayList<>();
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player1));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player2));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player3));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player4));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player5));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player6));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player7));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player8));
        players.add((ViewGroup) findViewById(R.id.fl_lsettings_player9));

        textviews = new ArrayList<>();
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs1));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs2));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs3));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs4));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs5));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs6));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs7));
        textviews.add((ViewGroup) findViewById(R.id.tv_lsettings_vs8));
    }

    public void setRadioGroupGameMode() {

        radioGroup.removeAllViews();

        radioButton2v2.setChecked(false);
        radioButton2v2v2.setChecked(false);
        radioButton2v2v2v2.setChecked(false);
        radioButton3v3.setChecked(false);
        radioButton3v3v3.setChecked(false);
        radioButton4v4.setChecked(false);

        switch (numberOfPlayers) {
            case 2:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                break;
            case 3:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                break;
            case 4:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                radioGroup.addView(radioButton2v2);
                break;
            case 5:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                break;
            case 6:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                radioGroup.addView(radioButton2v2v2);
                radioGroup.addView(radioButton3v3);
                break;
            case 7:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                break;
            case 8:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                radioGroup.addView(radioButton2v2v2v2);
                radioGroup.addView(radioButton4v4);
                break;
            case 9:
                radioGroup.addView(radioButtonVS);
                radioButtonVS.setChecked(true);
                radioGroup.addView(radioButton3v3v3);
                break;
            default:
                break;
        }
    }

    public void controlSeekbars() {


        SeekBar seekBar_life = (SeekBar) findViewById(R.id.sb_lsettings_life);
        seekBar_life.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                startLifepoints = START_LIFEPOINTS + progress * 10;
                textView_life.setText(Integer.toString(startLifepoints));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekBar_player = (SeekBar) findViewById(R.id.sb_lsettings_player);
        seekBar_player.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfPlayerChanged = true;
                numberOfPlayers = DEFAULT_NUMBEROFPLAYERS + progress;
                textView_player.setText(Integer.toString(numberOfPlayers));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (numberOfPlayerChanged) {
                    callNameBuffer();
                    callColorBuffer();
                    setRadioGroupGameMode();
                    setPlayerFragments();
                    setTextViewFragments(Mode.MODE_VS);
                    numberOfPlayerChanged = false;
                }
            }
        });

        SeekBar seekBar_time = (SeekBar) findViewById(R.id.sb_lsettings_time);
        seekBar_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        time = 0;
                        textView_time.setText("-- : --");
                        break;
                    case 1:
                        time = 10 * 60;
                        textView_time.setText("00 : 10");
                        break;
                    case 2:
                        time = 20 * 60;
                        textView_time.setText("00 : 20");
                        break;
                    case 3:
                        time = 30 * 60;
                        textView_time.setText("00 : 30");
                        break;
                    case 4:
                        time = 45 * 60;
                        textView_time.setText("00 : 45");
                        break;
                    case 5:
                        time = 60 * 60;
                        textView_time.setText("01 : 00");
                        break;
                    case 6:
                        time = 90 * 60;
                        textView_time.setText("01 : 30");
                        break;
                    case 7:
                        time = 120 * 60;
                        textView_time.setText("02 : 00");
                        break;
                    case 8:
                        time = 180 * 60;
                        textView_time.setText("03 : 00");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setPlayerFragments() {


        for (ViewGroup viewGroup : players) {
            viewGroup.removeAllViews();
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).addView(getLayoutInflater().inflate(R.layout.fragment_player, null));
        }
        playerSize = numberOfPlayers;

        for (int i = 0; i < numberOfPlayers; i++) {
            EditText editText = (EditText) players.get(i).findViewById(R.id.et_fplayer_name);
            editText.setText(nameBuffer[i]);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_colorless));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_white));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_blue));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_black));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_red));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_green));

            for (int n = 0; n < MAX_NUMBEROFCOLORES; n++) {
                toggleButtons.get(n).setChecked(colorBuffer[i][n]);
            }
        }
    }

    public void setTextViewFragments(int mode) {

        for (ViewGroup viewGroup : textviews) {
            viewGroup.removeAllViews();
        }
        switch (mode) {
            case 0:
                if (numberOfPlayers >= 2) {
                    textviews.get(0).addView(createTextView());
                }
                if (numberOfPlayers >= 3) {
                    textviews.get(1).addView(createTextView());
                }
                if (numberOfPlayers >= 4) {
                    textviews.get(2).addView(createTextView());
                }
                if (numberOfPlayers >= 5) {
                    textviews.get(3).addView(createTextView());
                }
                if (numberOfPlayers >= 6) {
                    textviews.get(4).addView(createTextView());
                }
                if (numberOfPlayers >= 7) {
                    textviews.get(5).addView(createTextView());
                }
                if (numberOfPlayers >= 8) {
                    textviews.get(6).addView(createTextView());
                }
                if (numberOfPlayers >= 9) {
                    textviews.get(7).addView(createTextView());
                }

                break;
            case 1:
                textviews.get(1).addView(createTextView());
                break;
            case 2:
                textviews.get(1).addView(createTextView());
                textviews.get(3).addView(createTextView());
                break;
            case 3:
                textviews.get(2).addView(createTextView());
                break;
            case 4:
                textviews.get(1).addView(createTextView());
                textviews.get(3).addView(createTextView());
                textviews.get(5).addView(createTextView());
                break;
            case 5:
                textviews.get(3).addView(createTextView());
                break;
            case 6:
                textviews.get(2).addView(createTextView());
                textviews.get(5).addView(createTextView());
                break;
            default:
                break;
        }
    }

    public TextView createTextView() {
        TextView textView = new TextView(this);
        textView.setTypeface(((Globals) this.getApplication()).getTypeface());
        textView.setText("--  VS  --");
        textView.setPadding(0, 10, 0, 10);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radiobutton_vs:
                mode = Mode.MODE_VS;
                buttonShuffle.setVisibility(View.INVISIBLE);
                break;
            case R.id.radiobutton_2v2:
                mode = Mode.MODE_2v2;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            case R.id.radiobutton_2v2v2:
                mode = Mode.MODE_2v2v2;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            case R.id.radiobutton_3v3:
                mode = Mode.MODE_3v3;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            case R.id.radiobutton_2v2v2v2:
                mode = Mode.MODE_2v2v2v2;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            case R.id.radiobutton_4v4:
                mode = Mode.MODE_4v4;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            case R.id.radiobutton_3v3v3:
                mode = Mode.MODE_3v3v3;
                buttonShuffle.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        setTextViewFragments(mode);
    }

    public void callNameBuffer() {

        for (int i = 0; i < playerSize; i++) {
            EditText editText = (EditText) players.get(i).findViewById(R.id.et_fplayer_name);
            nameBuffer[i] = editText.getText().toString();
        }
    }

    public void callColorBuffer() {
        for (int i = 0; i < playerSize; i++) {
            ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_colorless));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_white));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_blue));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_black));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_red));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_green));

            for (int n = 0; n < MAX_NUMBEROFCOLORES; n++) {
                colorBuffer[i][n] = toggleButtons.get(n).isChecked();
            }
        }
    }

    public void shuffleTeams() {

        int[] randomOrder = new int[numberOfPlayers];
        for (int i = 0; i < randomOrder.length; i++) {
            randomOrder[i] = i;
        }
        Random rnd = new Random();
        for (int i = randomOrder.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = randomOrder[index];
            randomOrder[index] = randomOrder[i];
            randomOrder[i] = a;
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            EditText editText = (EditText) players.get(i).findViewById(R.id.et_fplayer_name);
            editText.setText(nameBuffer[randomOrder[i]]);

            ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_colorless));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_white));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_blue));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_black));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_red));
            toggleButtons.add((ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_green));

            for (int n = 0; n < MAX_NUMBEROFCOLORES; n++) {
                toggleButtons.get(n).setChecked(colorBuffer[randomOrder[i]][n]);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.b_lsettings_shuffle) {
            if (doublePressShuffle) {
                callNameBuffer();
                callColorBuffer();
                shuffleTeams();
                doublePressShuffle = false;
            }
            doublePressShuffle = true;
            Toast.makeText(this, getResources().getString(R.string.clickShuffleAgain), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doublePressShuffle = false;
                }
            }, 1000);

        }
        if (v.getId() == R.id.b_lsettings_start) {

            ArrayList<Player> p = new ArrayList<>();
            ArrayList<String> n = new ArrayList<>();
            ArrayList<Deck> d = new ArrayList<>();

            for (int i = 0; i < numberOfPlayers; i++) {
                EditText editText = (EditText) players.get(i).findViewById(R.id.et_fplayer_name);
                ToggleButton toggle_c = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_colorless);
                ToggleButton toggle_w = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_white);
                ToggleButton toggle_u = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_blue);
                ToggleButton toggle_b = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_black);
                ToggleButton toggle_r = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_red);
                ToggleButton toggle_g = (ToggleButton) players.get(i).findViewById(R.id.tb_fplayer_green);

                d.add(new Deck(toggle_c.isChecked(), toggle_w.isChecked(), toggle_u.isChecked(), toggle_b.isChecked(), toggle_r.isChecked(), toggle_g.isChecked()));
                n.add(editText.getText().toString());

            }

            switch (mode) {
                case Mode.MODE_VS:
                    for (int i = 0; i < numberOfPlayers; i++) {
                        p.add(new Player(n.get(i), d.get(i)));
                    }
                    break;
                case Mode.MODE_2v2:
                    p.add(new Player(new String[]{n.get(0), n.get(1)}, new Deck[]{d.get(0), d.get(1)}));
                    p.add(new Player(new String[]{n.get(2), n.get(3)}, new Deck[]{d.get(2), d.get(3)}));
                    break;
                case Mode.MODE_2v2v2:
                    p.add(new Player(new String[]{n.get(0), n.get(1)}, new Deck[]{d.get(0), d.get(1)}));
                    p.add(new Player(new String[]{n.get(2), n.get(3)}, new Deck[]{d.get(2), d.get(3)}));
                    p.add(new Player(new String[]{n.get(4), n.get(5)}, new Deck[]{d.get(4), d.get(5)}));
                    break;
                case Mode.MODE_2v2v2v2:
                    p.add(new Player(new String[]{n.get(0), n.get(1)}, new Deck[]{d.get(0), d.get(1)}));
                    p.add(new Player(new String[]{n.get(2), n.get(3)}, new Deck[]{d.get(2), d.get(3)}));
                    p.add(new Player(new String[]{n.get(4), n.get(5)}, new Deck[]{d.get(4), d.get(5)}));
                    p.add(new Player(new String[]{n.get(6), n.get(7)}, new Deck[]{d.get(6), d.get(7)}));
                    break;
                case Mode.MODE_3v3:
                    p.add(new Player(new String[]{n.get(0), n.get(1), n.get(2)}, new Deck[]{d.get(0), d.get(1), d.get(2)}));
                    p.add(new Player(new String[]{n.get(3), n.get(4), n.get(5)}, new Deck[]{d.get(3), d.get(4), d.get(5)}));
                    break;
                case Mode.MODE_3v3v3:
                    p.add(new Player(new String[]{n.get(0), n.get(1), n.get(2)}, new Deck[]{d.get(0), d.get(1), d.get(2)}));
                    p.add(new Player(new String[]{n.get(3), n.get(4), n.get(5)}, new Deck[]{d.get(3), d.get(4), d.get(5)}));
                    p.add(new Player(new String[]{n.get(6), n.get(7), n.get(8)}, new Deck[]{d.get(6), d.get(7), d.get(8)}));
                    break;
                case Mode.MODE_4v4:
                    p.add(new Player(new String[]{n.get(0), n.get(1), n.get(2), n.get(3)}, new Deck[]{d.get(0), d.get(1), d.get(2), d.get(3)}));
                    p.add(new Player(new String[]{n.get(4), n.get(5), n.get(6), n.get(7)}, new Deck[]{d.get(4), d.get(5), d.get(6), d.get(7)}));
                    break;
                default:
                    break;
            }

            game = new Game(p, startLifepoints, time, mode);
            ((Globals) this.getApplication()).setGame(game);

            Intent intent;
            if (p.size() > 2) {
                intent = new Intent(this, ActivityGameMultiplayer.class);
            } else {
                intent = new Intent(this, ActivityGame2Player.class);
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (doublePressExit) {
            super.onBackPressed();
            return;
        }
        doublePressExit = true;
        Toast.makeText(this, getResources().getString(R.string.clickBackAgain), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doublePressExit = false;
            }
        }, 1500);
    }
}
