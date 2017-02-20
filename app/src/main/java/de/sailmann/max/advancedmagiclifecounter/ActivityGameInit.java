package de.sailmann.max.advancedmagiclifecounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 08.01.2017.
 */

public class ActivityGameInit extends AppCompatActivity implements View.OnClickListener {

    //Allgemein
    protected final boolean ADDITION = true;
    protected final boolean SUBTRACTION = false;
    protected boolean doublePressExit = false;

    //Aktuelles Spiel
    protected Game game;
    protected int numberOfPlayers;
    protected UndoBuffer undoBuffer;
    protected boolean saveGame = false;

    //Container
    protected ViewGroup viewGroup;

    //Menübanner
    protected ImageButton ib_gameMenue;
    protected ImageButton ib_undo;
    protected ImageButton ib_redo;
    protected TextView tv_time;
    protected long remainingSeconds = 0;
    protected MyTimer timer;
    protected boolean timerUp = false;

    //Player
    protected TextView tv_name;
    protected TextView tv_poison;
    protected TextView tv_lifepoints;
    protected TextView tv_energy;

    //Manacounter
    protected TextView tv_colorless;
    protected TextView tv_white;
    protected TextView tv_blue;
    protected TextView tv_black;
    protected TextView tv_red;
    protected TextView tv_green;
    protected boolean doublePressDeleteMana = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutgame);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        game = ((Globals) this.getApplication()).getGame();
        numberOfPlayers = game.getPlayers().size();
        undoBuffer = new UndoBuffer(getApplicationContext());

        if (game.getTimeLimit() == 0) {
            timerUp = true;
            remainingSeconds = 0;
        } else {
            timerUp = false;
            remainingSeconds = game.getTimeLimit();
        }
        initializeViews();
    }

    public void initializeViews() {
        //Container
        viewGroup = (ViewGroup) findViewById(R.id.fl_lgame_container);
        viewGroup.removeAllViews();

        //Menü Banner
        ib_gameMenue = (ImageButton) findViewById(R.id.ib_lgame_menue);
        ib_gameMenue.setOnClickListener(this);
        ib_undo = (ImageButton) findViewById(R.id.ib_lgame_undo);
        ib_undo.setOnClickListener(this);
        ib_redo = (ImageButton) findViewById(R.id.ib_lgame_redo);
        ib_redo.setOnClickListener(this);
        tv_time = (TextView) findViewById(R.id.tv_lgame_time);
        tv_time.setTypeface(((Globals) this.getApplication()).getTypeface());

        //Player Name
        tv_name = (TextView) findViewById(R.id.tv_lgame_name);
        tv_name.setTypeface(((Globals) this.getApplication()).getTypeface());

        //Poison Counter
        tv_poison = (TextView) findViewById(R.id.tv_lgame_poison);
        tv_poison.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.b_lgame_poisonplus).setOnClickListener(this);
        findViewById(R.id.b_lgame_poisonminus).setOnClickListener(this);

        //Life Counter
        tv_lifepoints = (TextView) findViewById(R.id.tv_lgame_lifepoints);
        tv_lifepoints.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.b_lgame_lifeminus).setOnClickListener(this);
        findViewById(R.id.b_lgame_lifeminus5).setOnClickListener(this);
        findViewById(R.id.b_lgame_lifeplus).setOnClickListener(this);
        findViewById(R.id.b_lgame_lifeplus5).setOnClickListener(this);

        //Energy Counter
        tv_energy = (TextView) findViewById(R.id.tv_lgame_energy);
        tv_energy.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.b_lgame_energyplus).setOnClickListener(this);
        findViewById(R.id.b_lgame_energyminus).setOnClickListener(this);

        //Mana Counter
        tv_colorless = (TextView) findViewById(R.id.tv_lgame_colorless);
        tv_colorless.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_colorlessminus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_colorlessplus).setOnClickListener(this);

        tv_white = (TextView) findViewById(R.id.tv_lgame_white);
        tv_white.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_whiteminus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_whiteplus).setOnClickListener(this);

        tv_blue = (TextView) findViewById(R.id.tv_lgame_blue);
        tv_blue.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_blackplus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_blackminus).setOnClickListener(this);

        tv_black = (TextView) findViewById(R.id.tv_lgame_black);
        tv_black.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_blueminus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_blueplus).setOnClickListener(this);

        tv_red = (TextView) findViewById(R.id.tv_lgame_red);
        tv_red.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_redminus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_redplus).setOnClickListener(this);

        tv_green = (TextView) findViewById(R.id.tv_lgame_green);
        tv_green.setTypeface(((Globals) this.getApplication()).getTypeface());
        findViewById(R.id.tv_lgame_greenminus).setOnClickListener(this);
        findViewById(R.id.tv_lgame_greenplus).setOnClickListener(this);

        findViewById(R.id.tv_lgame_slidein).setOnClickListener(this);
        findViewById(R.id.tv_lgame_slideout).setOnClickListener(this);
        findViewById(R.id.tv_lgame_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_lgame_menue) {
            Intent intent = new Intent(this, ActivityGameMenue.class);
            intent.putExtra("numberOfPlayers", numberOfPlayers);
            for (int i = 0; i < numberOfPlayers; i++) {
                String p = "Player" + i;
                intent.putExtra(p, game.getPlayers().get(i).getName());
            }
            startActivity(intent);
        }

        if (v.getId() == R.id.tv_lgame_slideout) {

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_lgame_manacounter);
            v.setVisibility(View.INVISIBLE);
            Animation slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out);
            linearLayout.startAnimation(slide_out);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    findViewById(R.id.tv_lgame_slidein).setVisibility(View.VISIBLE);
                }
            }, 1500);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        if (v.getId() == R.id.tv_lgame_slidein) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_lgame_manacounter);
            v.setVisibility(View.INVISIBLE);
            Animation slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in);
            linearLayout.startAnimation(slide_in);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    findViewById(R.id.tv_lgame_slideout).setVisibility(View.VISIBLE);
                }
            }, 1500);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (doublePressExit) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.saveGame))
                    .setMessage(getResources().getString(R.string.addGameToStatistic))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveGame = true;
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveGame = false;
                            finish();
                        }
                    })
                    .show();
        }
        doublePressExit = true;
        Toast.makeText(this,getResources().getString(R.string.clickBackAgain), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doublePressExit = false;
            }
        }, 1500);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (timer != null) {
            remainingSeconds = timer.getTime();
            timer.stop();
        }
        if (isFinishing()) {
            if (saveGame) {
                game.setEndTime();
                ((Globals) this.getApplication()).getStatistic().add(game);
            }
        }
    }

    public void vibration() {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new MyTimer(remainingSeconds, this.getClass().getSimpleName() + ".Thread");
        timer.start();
    }

    class MyTimer implements Runnable {

        private volatile boolean running = false;
        private Thread thread;
        private String threadName;
        private long time;
        private boolean timerFinished = false;


        MyTimer(long time, String threadName) {
            this.time = time;
            this.threadName = threadName;
        }

        private void print(final long time) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int seconds = (int) (time % 60);
                    int minutes = (int) (time / 60);
                    int hours = minutes / 60;
                    tv_time.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", minutes%60) + ":" + String.format("%02d", seconds));
                    if (timerFinished) {
                        tv_time.setTextColor(Color.RED);
                        vibration();
                        stop();
                    }
                }
            });
        }

        @Override
        public void run() {
            while (running) {

                if (timerUp) {
                    time++;
                    if (time >= 35999) {
                        timerFinished = true;
                    }
                } else {
                    time--;
                    if (time <= 0) {
                        timerFinished = true;
                    }
                }
                print(time);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void start() {
            running = true;
            thread = new Thread(this);
            thread.setName(threadName);
            thread.start();
        }

        public void stop() {
            if (running) {
                running = false;
                while (true) {
                    try {
                        thread.join();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public long getTime() {
            return this.time;
        }
    }
}