package de.sailmann.max.advancedmagiclifecounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.makeText;

/**
 * Created by Admin on 08.01.2017.
 */

public class ActivityGameMenue extends AppCompatActivity implements View.OnClickListener {

    private final boolean COIN = false;
    private final boolean DICE = true;

    private TextView tv_web;
    private TextView tv_coin;
    private TextView tv_dice;

    private ViewGroup viewGroup;

    private MyThread thread;
    int numberOfDiceAndCoin = 1;
    int numberOfPlayers;

    int rollDiceTo = 6;

    private ArrayList<String> playerName;
    private ArrayList<ViewGroup> containerDiceAndCoin;
    private ArrayList<TextView> textViewDiceAndCoin;
    private ArrayList<ImageView> imageViewDiceAndCoin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutgamemenue);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        numberOfPlayers = intent.getIntExtra("numberOfPlayers", 2);

        playerName = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String p = "Player" + i;
            playerName.add(intent.getStringExtra(p));
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(dm.widthPixels, dm.heightPixels);

        tv_coin = (TextView) findViewById(R.id.tv_lmenue_coin);
        tv_web = (TextView) findViewById(R.id.tv_lmenue_web);
        tv_dice = (TextView) findViewById(R.id.tv_lmenue_dice);
        tv_dice.setTypeface(((Globals) this.getApplication()).getTypeface());
        tv_coin.setTypeface(((Globals) this.getApplication()).getTypeface());
        tv_web.setTypeface(((Globals) this.getApplication()).getTypeface());
        tv_coin.setOnClickListener(this);
        tv_web.setOnClickListener(this);
        tv_dice.setOnClickListener(this);

        viewGroup = (ViewGroup) findViewById(R.id.fl_lmenue_container);
        loadDice();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_lmenue_dice) {
            if (thread != null) {
                thread.stop();
            }
            loadDice();
        }
        if (v.getId() == R.id.tv_lmenue_coin) {
            if (thread != null) {
                thread.stop();
            }
            loadCoin();

        }
        if (v.getId() == R.id.tv_lmenue_web) {
            if (thread != null) {
                thread.stop();
            }
            loadWeb();

        }
        if (v.getId() == R.id.rl_fdice_container) {
            if (thread != null) {
                thread.stop();
            }
            thread = new MyThread(DICE, this);
            thread.start();
        }
        if (v.getId() == R.id.rl_fcoin_container) {
            if (thread != null) {
                thread.stop();
            }
            thread = new MyThread(COIN, this);
            thread.start();
        }
        if (v.getId() == R.id.b_fweb_search) {
            EditText editText = (EditText) findViewById(R.id.et_fweb_cardname);
            if (editText.getText().toString() != "") {

                String url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?name=";
                String query = editText.getText().toString().trim();
                String[] splited = query.split("\\s+");
                StringBuilder search = new StringBuilder();
                for (String s : splited) {
                    search.append(s).append("+");
                }
                String final_url = url + search.toString().substring(0, search.toString().length() - 1);
                Uri uri = Uri.parse(final_url);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }

        }
    }

    public void loadDice() {
        tv_dice.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));
        tv_web.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
        tv_coin.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));

        viewGroup.removeAllViews();
        viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_dice, null));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_fdice_container);
        relativeLayout.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_fdice_number)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_fdice_value)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((RadioButton) findViewById(R.id.rb_fdice_all)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((RadioButton) findViewById(R.id.rb_fdice_one)).setTypeface(((Globals) this.getApplication()).getTypeface());

        loadOneImage(DICE);

        SeekBar value = (SeekBar) findViewById(R.id.sb_fdice_value);
        value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rollDiceTo = 6 + progress;
                ((TextView) findViewById(R.id.tv_fdice_value)).setText(getResources().getString(R.string.RollFromTo) + rollDiceTo);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (thread != null) {
                    thread.stop();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        RadioGroup number = (RadioGroup) findViewById(R.id.rg_fdice_number);
        number.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_fdice_all) {
                    if (thread != null) {
                        thread.stop();
                    }
                    numberOfDiceAndCoin = numberOfPlayers;
                    switch (numberOfPlayers) {
                        case 2:
                            loadTwoImages(DICE);
                            break;
                        case 3:
                            loadThreeImages(DICE);
                            break;
                        case 4:
                            loadFourImages(DICE);
                            break;
                        case 5:
                            loadFiveImages(DICE);
                            break;
                        case 6:
                            loadSixImages(DICE);
                            break;
                        case 7:
                            loadSevenImages(DICE);
                            break;
                        case 8:
                            loadEightImages(DICE);
                            break;
                        case 9:
                            loadNineImages(DICE);
                            break;
                        default:
                            break;
                    }
                    loadTextView(DICE);
                    loadImageView(DICE);
                }
                if (checkedId == R.id.rb_fdice_one) {
                    if (thread != null) {
                        thread.stop();
                    }
                    numberOfDiceAndCoin = 1;
                    loadOneImage(DICE);
                }
            }
        });
    }

    public void clearContainer() {
        if (containerDiceAndCoin != null) {
            for (ViewGroup vg : containerDiceAndCoin) {
                vg.removeAllViews();
            }
        }
    }

    public void fillContainer(int id) {
        for (ViewGroup vg : containerDiceAndCoin) {
            vg.addView(getLayoutInflater().inflate(id, null));
        }
    }

    public void loadOneImage(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        imageViewDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            fillContainer(R.layout.fragment_dice_imageview);
            ((TextView) containerDiceAndCoin.get(0).findViewById(R.id.tv_fdice_name)).setText("");
            imageViewDiceAndCoin.add((ImageView) containerDiceAndCoin.get(0).findViewById(R.id.iv_fdice_dice));
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            fillContainer(R.layout.fragment_coin_imageview);
            ((TextView) containerDiceAndCoin.get(0).findViewById(R.id.tv_fcoin_name)).setText("");
            imageViewDiceAndCoin.add((ImageView) containerDiceAndCoin.get(0).findViewById(R.id.iv_fcoin_coin));
        }

    }

    public void loadTwoImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            fillContainer(R.layout.fragment_coin_imageview);
        }
    }

    public void loadThreeImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            fillContainer(R.layout.fragment_coin_imageview);
        }

    }

    public void loadFourImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_9));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_9));
            fillContainer(R.layout.fragment_coin_imageview);
        }

    }

    public void loadFiveImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            fillContainer(R.layout.fragment_coin_imageview);
        }

    }

    public void loadSixImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_8));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_9));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_8));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_9));
            fillContainer(R.layout.fragment_coin_imageview);
        }

    }

    public void loadSevenImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_9));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_9));
            fillContainer(R.layout.fragment_coin_imageview);
        }

    }

    public void loadEightImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_9));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_9));
            fillContainer(R.layout.fragment_coin_imageview);
        }
    }

    public void loadNineImages(boolean diceOrCoin) {
        clearContainer();
        containerDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_8));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fdice_9));
            fillContainer(R.layout.fragment_dice_imageview);
        } else if (diceOrCoin == COIN) {
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_1));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_2));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_3));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_4));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_5));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_6));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_7));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_8));
            containerDiceAndCoin.add((ViewGroup) findViewById(R.id.fl_fcoin_9));
            fillContainer(R.layout.fragment_coin_imageview);
        }
    }

    public void loadTextView(boolean diceOrCoin) {
        textViewDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            for (ViewGroup vg : containerDiceAndCoin) {
                textViewDiceAndCoin.add((TextView) vg.findViewById(R.id.tv_fdice_name));
            }
        } else if (diceOrCoin == COIN) {
            for (ViewGroup vg : containerDiceAndCoin) {
                textViewDiceAndCoin.add((TextView) vg.findViewById(R.id.tv_fcoin_name));
            }
        }

        int counter = 0;
        for (TextView tv : textViewDiceAndCoin) {
            tv.setTypeface(((Globals) this.getApplication()).getTypeface());
            tv.setText(playerName.get(counter));
            counter++;
        }
    }

    public void loadImageView(boolean diceOrCoin) {
        imageViewDiceAndCoin = new ArrayList<>();
        if (diceOrCoin == DICE) {
            for (ViewGroup vg : containerDiceAndCoin) {
                imageViewDiceAndCoin.add((ImageView) vg.findViewById(R.id.iv_fdice_dice));
            }
        } else if (diceOrCoin == COIN) {
            for (ViewGroup vg : containerDiceAndCoin) {
                imageViewDiceAndCoin.add((ImageView) vg.findViewById(R.id.iv_fcoin_coin));
            }
        }
    }

    public void loadCoin() {
        tv_coin.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));
        tv_web.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
        tv_dice.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));

        viewGroup.removeAllViews();
        viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_coin, null));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_fcoin_container);
        relativeLayout.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_fcoin_number)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((RadioButton) findViewById(R.id.rb_fcoin_all)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((RadioButton) findViewById(R.id.rb_fcoin_one)).setTypeface(((Globals) this.getApplication()).getTypeface());

        loadOneImage(COIN);

        RadioGroup number = (RadioGroup) findViewById(R.id.rg_fcoin_number);
        number.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_fcoin_all) {
                    if (thread != null) {
                        thread.stop();
                    }
                    numberOfDiceAndCoin = numberOfPlayers;
                    switch (numberOfPlayers) {
                        case 2:
                            loadTwoImages(COIN);
                            break;
                        case 3:
                            loadThreeImages(COIN);
                            break;
                        case 4:
                            loadFourImages(COIN);
                            break;
                        case 5:
                            loadFiveImages(COIN);
                            break;
                        case 6:
                            loadSixImages(COIN);
                            break;
                        case 7:
                            loadSevenImages(COIN);
                            break;
                        case 8:
                            loadEightImages(COIN);
                            break;
                        case 9:
                            loadNineImages(COIN);
                            break;
                        default:
                            break;
                    }
                    loadTextView(COIN);
                    loadImageView(COIN);
                }
                if (checkedId == R.id.rb_fcoin_one) {
                    if (thread != null) {
                        thread.stop();
                    }
                    numberOfDiceAndCoin = 1;
                    loadOneImage(COIN);
                }
            }
        });


    }

    public void loadWeb() {
        tv_web.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));
        tv_dice.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
        tv_coin.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));

        viewGroup.removeAllViews();
        viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_web, null));

        ((TextView) findViewById(R.id.tv_fweb_info)).setTypeface(((Globals) this.getApplication()).getTypeface());

        findViewById(R.id.b_fweb_search).setOnClickListener(this);


    }

    class MyThread implements Runnable {
        private volatile boolean running = false;
        private Thread thread;
        private int[] randomNumber;
        private Random random = new Random();
        private String threadName = "DiceSimulator";
        private boolean dice;
        private Context context;
        private boolean finished;

        public MyThread(boolean diceOrCoin, Context context) {
            this.dice = diceOrCoin;
            randomNumber = new int[numberOfDiceAndCoin];
            this.context = context;
            finished = false;
        }

        private void print(final int diceNumber[]) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (dice) {
                        int counter = 0;
                        for (ImageView imageView : imageViewDiceAndCoin) {
                            switch (diceNumber[counter]) {
                                case 1:
                                    imageView.setImageResource(R.drawable.dice_one);
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.dice_two);
                                    break;
                                case 3:
                                    imageView.setImageResource(R.drawable.dice_three);
                                    break;
                                case 4:
                                    imageView.setImageResource(R.drawable.dice_four);
                                    break;
                                case 5:
                                    imageView.setImageResource(R.drawable.dice_five);
                                    break;
                                case 6:
                                    imageView.setImageResource(R.drawable.dice_six);
                                    break;
                                case 7:
                                    imageView.setImageResource(R.drawable.dice_seven);
                                    break;
                                case 8:
                                    imageView.setImageResource(R.drawable.dice_eight);
                                    break;
                                case 9:
                                    imageView.setImageResource(R.drawable.dice_nine);
                                    break;
                                case 10:
                                    imageView.setImageResource(R.drawable.dice_ten);
                                    break;
                                case 11:
                                    imageView.setImageResource(R.drawable.dice_eleven);
                                    break;
                                case 12:
                                    imageView.setImageResource(R.drawable.dice_twelve);
                                    break;
                                case 13:
                                    imageView.setImageResource(R.drawable.dice_thirteen);
                                    break;
                                case 14:
                                    imageView.setImageResource(R.drawable.dice_fourteen);
                                    break;
                                case 15:
                                    imageView.setImageResource(R.drawable.dice_fifteen);
                                    break;
                                case 16:
                                    imageView.setImageResource(R.drawable.dice_sixteen);
                                    break;
                                case 17:
                                    imageView.setImageResource(R.drawable.dice_seventeen);
                                    break;
                                case 18:
                                    imageView.setImageResource(R.drawable.dice_eightteen);
                                    break;
                                case 19:
                                    imageView.setImageResource(R.drawable.dice_nineteen);
                                    break;
                                case 20:
                                    imageView.setImageResource(R.drawable.dice_twenty);
                                    break;
                                default:
                                    break;
                            }
                            counter++;
                        }
                    } else {
                        int counter = 0;
                        for (ImageView imageView : imageViewDiceAndCoin) {
                            switch (diceNumber[counter]) {
                                case 1:
                                    imageView.setImageResource(R.drawable.head);
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.tail);
                                    break;
                                default:
                                    break;
                            }
                            counter++;
                        }
                    }
                    if (finished) {
                        showToast();
                        stop();
                    }
                }
            });
        }

        @Override
        public void run() {
            int counter = 200;
            while (running) {
                if (dice) {
                    for (int i = 0; i < numberOfDiceAndCoin; i++) {
                        randomNumber[i] = random.nextInt(rollDiceTo) + 1;
                    }
                } else {
                    for (int i = 0; i < numberOfDiceAndCoin; i++) {
                        randomNumber[i] = random.nextInt(2) + 1;
                    }
                }
                print(randomNumber);
                counter += 200;
                if (counter >= 1000) {

                    finished = true;
                }
                try {
                    Thread.sleep(counter);
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

                showToast();

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
    }

    public void showToast() {
        Toast toast = makeText(this, getResources().getString(R.string.Finished), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (thread != null) {
            thread.stop();
        }
    }
}
