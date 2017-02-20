package de.sailmann.max.advancedmagiclifecounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Admin on 13.01.2017.
 */

public class ActivityTutorial extends AppCompatActivity implements View.OnClickListener {

    private final boolean FOREWARDS = true;
    private final boolean BACKWARDS = false;

    private boolean clickable = true;

    private ImageView imageViewActual;
    private TextView tvPage;
    private TextView tvActual;
    private TextView tvNext;

    private Animation slide_left_to_middle;
    private Animation slide_middle_to_right;
    private Animation slide_middle_to_left;
    private Animation slide_right_to_middle;

    private ImageButton ff;
    private ImageButton rew;

    private ArrayList<Integer> ids;
    private ArrayList<String> tutorialMsg;

    private int page = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouttutorial);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ff = (ImageButton) findViewById(R.id.ib_ltutorial_ff);
        ff.setOnClickListener(this);

        rew = (ImageButton) findViewById(R.id.ib_ltutorial_rew);
        rew.setOnClickListener(this);
        rew.setVisibility(View.INVISIBLE);

        imageViewActual = (ImageView) findViewById(R.id.iv_ltutorial_imageactual);

        tvPage = (TextView) findViewById(R.id.tv_ltutorial_page);
        tvPage.setTypeface(((Globals) this.getApplication()).getTypeface());
        tvPage.setText("< 1 of 22 >");

        slide_left_to_middle = AnimationUtils.loadAnimation(this, R.anim.slide_left_to_middle);
        slide_middle_to_right = AnimationUtils.loadAnimation(this, R.anim.slide_middle_to_right);
        slide_middle_to_left = AnimationUtils.loadAnimation(this, R.anim.slide_middle_to_left);
        slide_right_to_middle = AnimationUtils.loadAnimation(this, R.anim.slide_right_to_middle);


        findViewById(R.id.b_ltutorial_guide).setOnClickListener(this);
        ((Button) findViewById(R.id.b_ltutorial_guide)).setTypeface(((Globals) this.getApplication()).getTypeface());

        tvActual = (TextView) findViewById(R.id.tv_ltutorial_actual);
        tvNext = (TextView) findViewById(R.id.tv_ltutorial_next);

        tvNext.setTypeface(((Globals) this.getApplication()).getTypeface());
        tvActual.setTypeface(((Globals) this.getApplication()).getTypeface());

        tvActual.setText(R.string.about);

        ids = new ArrayList<>();
        ids.add(R.drawable.tutorial_mainscreen1);
        ids.add(R.drawable.tutorial_mainscreen2);
        ids.add(R.drawable.tutorial_mainscreen3);
        ids.add(R.drawable.tutorial_settings1);
        ids.add(R.drawable.tutorial_settings2);
        ids.add(R.drawable.tutorial_settings3);
        ids.add(R.drawable.tutorial_settings4);
        ids.add(R.drawable.tutorial_settings5);
        ids.add(R.drawable.tutorial_game1);
        ids.add(R.drawable.tutorial_game2);
        ids.add(R.drawable.tutorial_game3);
        ids.add(R.drawable.tutorial_game4);
        ids.add(R.drawable.tutorial_game5);
        ids.add(R.drawable.tutorial_game6);
        ids.add(R.drawable.tutorial_game7);
        ids.add(R.drawable.tutorial_game8);
        ids.add(R.drawable.tutorial_menue1);
        ids.add(R.drawable.tutorial_menue2);
        ids.add(R.drawable.tutorial_menue3);
        ids.add(R.drawable.tutorial_save);
        ids.add(R.drawable.tutorial_statistic1);
        ids.add(R.drawable.tutorial_statistic2);

        tutorialMsg = new ArrayList<>();
        tutorialMsg.add(getString(R.string.tutorial1));
        tutorialMsg.add(getString(R.string.tutorial2));
        tutorialMsg.add(getString(R.string.tutorial3));
        tutorialMsg.add(getString(R.string.tutorial4));
        tutorialMsg.add(getString(R.string.tutorial5));
        tutorialMsg.add(getString(R.string.tutorial6));
        tutorialMsg.add(getString(R.string.tutorial7));
        tutorialMsg.add(getString(R.string.tutorial8));
        tutorialMsg.add(getString(R.string.tutorial9));
        tutorialMsg.add(getString(R.string.tutorial10));
        tutorialMsg.add(getString(R.string.tutorial11));
        tutorialMsg.add(getString(R.string.tutorial12));
        tutorialMsg.add(getString(R.string.tutorial13));
        tutorialMsg.add(getString(R.string.tutorial14));
        tutorialMsg.add(getString(R.string.tutorial15));
        tutorialMsg.add(getString(R.string.tutorial16));
        tutorialMsg.add(getString(R.string.tutorial17));
        tutorialMsg.add(getString(R.string.tutorial18));
        tutorialMsg.add(getString(R.string.tutorial19));
        tutorialMsg.add(getString(R.string.tutorial20));
        tutorialMsg.add(getString(R.string.tutorial21));
        tutorialMsg.add(getString(R.string.tutorial22));

        imageViewActual.setImageResource(ids.get(0));
        tvActual.setText(tutorialMsg.get(0));

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.b_ltutorial_guide) {
            String url = "http://magic.wizards.com/en/gameplay/how-to-play";
            Uri uri = Uri.parse(url);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        if (clickable) {

            if (v.getId() == R.id.ib_ltutorial_ff) {

                clickable = false;
                startAnimation(FOREWARDS);
                page++;
                tvPage.setText("< " + page + " of 22 >");
                if (page >= 22) {
                    v.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.ib_ltutorial_rew).setVisibility(View.VISIBLE);


            }
            if (v.getId() == R.id.ib_ltutorial_rew) {
                clickable = false;
                startAnimation(BACKWARDS);
                page--;
                tvPage.setText("< " + page + " of 22 >");
                if (page <= 1) {
                    v.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.ib_ltutorial_ff).setVisibility(View.VISIBLE);


            }
        }
    }

    public void startAnimation(boolean forewards) {

        if (forewards) {

            imageViewActual.setImageResource(ids.get(page));

            tvNext.setText(tutorialMsg.get(page));
            tvNext.setVisibility(View.VISIBLE);

            tvActual.setVisibility(View.INVISIBLE);
            tvActual.setAnimation(slide_middle_to_left);
            tvNext.setAnimation(slide_right_to_middle);

            tvActual.getAnimation().start();
            tvNext.getAnimation().start();


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    clickable = true;
                    tvActual.setText(tvNext.getText());
                    tvActual.setVisibility(View.VISIBLE);
                    tvNext.setVisibility(View.INVISIBLE);

                }
            }, 500);
        } else if (!forewards) {


            imageViewActual.setImageResource(ids.get(page-2));

            tvNext.setText(tutorialMsg.get(page-2));
            tvNext.setVisibility(View.VISIBLE);

            tvActual.setVisibility(View.INVISIBLE);

            tvActual.setAnimation(slide_middle_to_right);
            tvNext.setAnimation(slide_left_to_middle);

            tvActual.getAnimation().start();
            tvNext.getAnimation().start();


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    clickable = true;
                    tvActual.setText(tvNext.getText());
                    tvActual.setVisibility(View.VISIBLE);
                    tvNext.setVisibility(View.INVISIBLE);

                }
            }, 500);
        }
    }
}
