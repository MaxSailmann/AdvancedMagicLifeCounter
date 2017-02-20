package de.sailmann.max.advancedmagiclifecounter;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 06.01.2017.
 */

public class ActivityGame2Player extends ActivityGameInit implements View.OnClickListener {

    private final int PLAYER1 = 0;
    private final int PLAYER2 = 1;

    private boolean doublePressDeleteMana2 = false;

    //Player 2
    private TextView tv_name2;
    private TextView tv_poison2;
    private TextView tv_lifepoints2;
    private TextView tv_energy2;

    //Menü Banner 2
    private TextView tv_colorless2;
    private TextView tv_white2;
    private TextView tv_blue2;
    private TextView tv_black2;
    private TextView tv_red2;
    private TextView tv_green2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeScreen();
    }

    public void initializeScreen() {

        //Container
        viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_2player, null));
        viewGroup.setRotation(180);

        //Player 2 Name
        tv_name2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_name2);
        tv_name2.setTypeface(((Globals) this.getApplication()).getTypeface());

        //Poison Counter 2
        tv_poison2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_poison2);
        tv_poison2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.b_lgame_poisonplus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.b_lgame_poisonminus2).setOnClickListener(this);

        //Life Counter 2
        tv_lifepoints2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_lifepoints2);
        tv_lifepoints2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.b_lgame_lifeminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.b_lgame_lifeminus5_2).setOnClickListener(this);
        viewGroup.findViewById(R.id.b_lgame_lifeplus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.b_lgame_lifeplus5_2).setOnClickListener(this);

        //Energy Counter 2
        tv_energy2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_energy2);
        tv_energy2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.b_lgame_energyplus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.b_lgame_energyminus2).setOnClickListener(this);

        //Mana Counter 2
        tv_colorless2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_colorless2);
        tv_colorless2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_colorlessminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_colorlessplus2).setOnClickListener(this);

        tv_white2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_white2);
        tv_white2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_whiteminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_whiteplus2).setOnClickListener(this);

        tv_blue2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_blue2);
        tv_blue2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_blackplus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_blackminus2).setOnClickListener(this);

        tv_black2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_black2);
        tv_black2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_blueminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_blueplus2).setOnClickListener(this);

        tv_red2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_red2);
        tv_red2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_redminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_redplus2).setOnClickListener(this);

        tv_green2 = (TextView) viewGroup.findViewById(R.id.tv_lgame_green2);
        tv_green2.setTypeface(((Globals) this.getApplication()).getTypeface());
        viewGroup.findViewById(R.id.tv_lgame_greenminus2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_greenplus2).setOnClickListener(this);

        viewGroup.findViewById(R.id.tv_lgame_slidein2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_slideout2).setOnClickListener(this);
        viewGroup.findViewById(R.id.tv_lgame_delete2).setOnClickListener(this);

        tv_name.setText(game.getPlayers().get(0).getName());
        tv_lifepoints.setText(String.valueOf(game.getPlayers().get(0).getLifePoints()));
        tv_energy.setText(String.valueOf(game.getPlayers().get(0).getEnergyCounter()));
        tv_poison.setText(String.valueOf(game.getPlayers().get(0).getPoisonCounter()));

        tv_name2.setText(game.getPlayers().get(1).getName());
        tv_lifepoints2.setText(String.valueOf(game.getPlayers().get(1).getLifePoints()));
        tv_energy2.setText(String.valueOf(game.getPlayers().get(1).getEnergyCounter()));
        tv_poison2.setText(String.valueOf(game.getPlayers().get(1).getPoisonCounter()));
    }

    public void projectPlayer(int selectedPlayer) {
        switch (selectedPlayer) {
            case 0:
                tv_name.setText(game.getPlayers().get(selectedPlayer).getName());
                tv_poison.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getPoisonCounter()));
                tv_lifepoints.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getLifePoints()));
                tv_energy.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getEnergyCounter()));

                if (game.getPlayers().get(selectedPlayer).getLifePoints() >= (game.getStartLifePoints() / 2)) {
                    tv_lifepoints.setTextColor(getResources().getColor(R.color.black));
                }
                if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 2)) {
                    tv_lifepoints.setTextColor(getResources().getColor(R.color.orange));
                }
                if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 5)) {
                    tv_lifepoints.setTextColor(getResources().getColor(R.color.red));
                }

                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() <= 5) {
                    tv_poison.setTextColor(getResources().getColor(R.color.black));
                }
                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > 5) {
                    tv_poison.setTextColor(getResources().getColor(R.color.orange));
                }
                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > 7) {
                    tv_poison.setTextColor(getResources().getColor(R.color.red));
                }

                tv_colorless.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getColorlessCounter()));
                tv_white.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getWhiteCounter()));
                tv_blue.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlueCounter()));
                tv_black.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlackCounter()));
                tv_red.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getRedCounter()));
                tv_green.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getGreenCounter()));
                break;

            case 1:
                tv_name2.setText(game.getPlayers().get(selectedPlayer).getName());
                tv_poison2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getPoisonCounter()));
                tv_lifepoints2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getLifePoints()));
                tv_energy2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getEnergyCounter()));

                if (game.getPlayers().get(selectedPlayer).getLifePoints() >= (game.getStartLifePoints() / 2)) {
                    tv_lifepoints2.setTextColor(getResources().getColor(R.color.black));
                }
                if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 2)) {
                    tv_lifepoints2.setTextColor(getResources().getColor(R.color.orange));
                }
                if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 5)) {
                    tv_lifepoints2.setTextColor(getResources().getColor(R.color.red));
                }

                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() <= ((numberOfPlayers * 5) / 2)) {
                    tv_poison2.setTextColor(getResources().getColor(R.color.black));
                }
                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > ((numberOfPlayers * 5) / 2)) {
                    tv_poison2.setTextColor(getResources().getColor(R.color.orange));
                }
                if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > (numberOfPlayers * 5 - numberOfPlayers * 5 / 4)) {
                    tv_poison2.setTextColor(getResources().getColor(R.color.red));
                }

                tv_colorless2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getColorlessCounter()));
                tv_white2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getWhiteCounter()));
                tv_blue2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlueCounter()));
                tv_black2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlackCounter()));
                tv_red2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getRedCounter()));
                tv_green2.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getGreenCounter()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int bufferLife1 = game.getPlayers().get(PLAYER1).getLifePoints();
        int bufferPoison1 = game.getPlayers().get(PLAYER1).getPoisonCounter();
        int bufferEnergy1 = game.getPlayers().get(PLAYER1).getEnergyCounter();

        int bufferLife2 = game.getPlayers().get(PLAYER2).getLifePoints();
        int bufferPoison2 = game.getPlayers().get(PLAYER2).getPoisonCounter();
        int bufferEnergy2 = game.getPlayers().get(PLAYER2).getEnergyCounter();

        if (v.getId() == R.id.ib_lgame_undo) {
            game.setPlayers(undoBuffer.undo(game.getPlayers()));
            projectPlayer(undoBuffer.getElement());
        }
        if (v.getId() == R.id.ib_lgame_redo) {
            game.setPlayers(undoBuffer.redo(game.getPlayers()));
            projectPlayer(undoBuffer.getElement());
        }
        if (v.getId() == R.id.b_lgame_poisonplus) {
            game.getPlayers().get(PLAYER1).setPoisonCounter(ADDITION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, bufferLife1, bufferPoison1, game.getPlayers().get(PLAYER1).getPoisonCounter(), bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_poisonminus) {
            game.getPlayers().get(PLAYER1).setPoisonCounter(SUBTRACTION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, bufferLife1, bufferPoison1, game.getPlayers().get(PLAYER1).getPoisonCounter(), bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_lifeplus) {
            game.getPlayers().get(PLAYER1).setLifePoints(ADDITION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, game.getPlayers().get(PLAYER1).getLifePoints(), bufferPoison1, bufferPoison1, bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_lifeplus5) {
            game.getPlayers().get(PLAYER1).setLifePoints(ADDITION, 5);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, game.getPlayers().get(PLAYER1).getLifePoints(), bufferPoison1, bufferPoison1, bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_lifeminus) {
            game.getPlayers().get(PLAYER1).setLifePoints(SUBTRACTION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, game.getPlayers().get(PLAYER1).getLifePoints(), bufferPoison1, bufferPoison1, bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_lifeminus5) {
            game.getPlayers().get(PLAYER1).setLifePoints(SUBTRACTION, 5);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, game.getPlayers().get(PLAYER1).getLifePoints(), bufferPoison1, bufferPoison1, bufferEnergy1, bufferEnergy1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_energyplus) {
            game.getPlayers().get(PLAYER1).setEnergyCounter(ADDITION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, bufferLife1, bufferPoison1, bufferPoison1, bufferEnergy1, game.getPlayers().get(PLAYER1).getEnergyCounter());
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.b_lgame_energyminus) {
            game.getPlayers().get(PLAYER1).setEnergyCounter(SUBTRACTION, 1);
            undoBuffer.add(PLAYER1, game.getPlayers().get(PLAYER1).getName(), bufferLife1, bufferLife1, bufferPoison1, bufferPoison1, bufferEnergy1, game.getPlayers().get(PLAYER1).getEnergyCounter());
            projectPlayer(PLAYER1);
        }

        if (v.getId() == R.id.tv_lgame_colorlessplus) {
            game.getPlayers().get(PLAYER1).setColorlessCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_colorlessminus) {
            game.getPlayers().get(PLAYER1).setColorlessCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_whiteplus) {
            game.getPlayers().get(PLAYER1).setWhiteCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_whiteminus) {
            game.getPlayers().get(PLAYER1).setWhiteCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_blueplus) {
            game.getPlayers().get(PLAYER1).setBlueCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_blueminus) {
            game.getPlayers().get(PLAYER1).setBlueCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_blackplus) {
            game.getPlayers().get(PLAYER1).setBlackCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_blackminus) {
            game.getPlayers().get(PLAYER1).setBlackCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_redplus) {
            game.getPlayers().get(PLAYER1).setRedCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_redminus) {
            game.getPlayers().get(PLAYER1).setRedCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_greenplus) {
            game.getPlayers().get(PLAYER1).setGreenCounter(ADDITION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_greenminus) {
            game.getPlayers().get(PLAYER1).setGreenCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER1);
        }
        if (v.getId() == R.id.tv_lgame_delete) {
            if (doublePressDeleteMana) {
                game.getPlayers().get(PLAYER1).deleteManaCounter();
                projectPlayer(PLAYER1);
                doublePressDeleteMana = false;
            }
            doublePressDeleteMana = true;
            Toast.makeText(this, getResources().getString(R.string.deleteMana), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doublePressDeleteMana = false;
                }
            }, 1000);
        }

        if (v == viewGroup.findViewById(R.id.b_lgame_poisonplus2)) {
            game.getPlayers().get(PLAYER2).setPoisonCounter(ADDITION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, bufferLife2, bufferPoison2, game.getPlayers().get(PLAYER2).getPoisonCounter(), bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_poisonminus2)) {
            game.getPlayers().get(PLAYER2).setPoisonCounter(SUBTRACTION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, bufferLife2, bufferPoison2, game.getPlayers().get(PLAYER2).getPoisonCounter(), bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_lifeplus2)) {
            game.getPlayers().get(PLAYER2).setLifePoints(ADDITION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, game.getPlayers().get(PLAYER2).getLifePoints(), bufferPoison2, bufferPoison2, bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_lifeplus5_2)) {
            game.getPlayers().get(PLAYER2).setLifePoints(ADDITION, 5);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, game.getPlayers().get(PLAYER2).getLifePoints(), bufferPoison2, bufferPoison2, bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_lifeminus2)) {
            game.getPlayers().get(PLAYER2).setLifePoints(SUBTRACTION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, game.getPlayers().get(PLAYER2).getLifePoints(), bufferPoison2, bufferPoison2, bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_lifeminus5_2)) {
            game.getPlayers().get(PLAYER2).setLifePoints(SUBTRACTION, 5);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, game.getPlayers().get(PLAYER2).getLifePoints(), bufferPoison2, bufferPoison2, bufferEnergy2, bufferEnergy2);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_energyplus2)) {
            game.getPlayers().get(PLAYER2).setEnergyCounter(ADDITION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, bufferLife2, bufferPoison2, bufferPoison2, bufferEnergy2, game.getPlayers().get(PLAYER2).getEnergyCounter());
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.b_lgame_energyminus2)) {
            game.getPlayers().get(PLAYER2).setEnergyCounter(SUBTRACTION, 1);
            undoBuffer.add(PLAYER2, game.getPlayers().get(PLAYER2).getName(), bufferLife2, bufferLife2, bufferPoison2, bufferPoison2, bufferEnergy2, game.getPlayers().get(PLAYER2).getEnergyCounter());
            projectPlayer(PLAYER2);
        }


        if (v == viewGroup.findViewById(R.id.tv_lgame_colorlessplus2)) {
            game.getPlayers().get(PLAYER2).setColorlessCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_colorlessminus2)) {
            game.getPlayers().get(PLAYER2).setColorlessCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_whiteplus2)) {
            game.getPlayers().get(PLAYER2).setWhiteCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_whiteminus2)) {
            game.getPlayers().get(PLAYER2).setWhiteCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_blueplus2)) {
            game.getPlayers().get(PLAYER2).setBlueCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_blueminus2)) {
            game.getPlayers().get(PLAYER2).setBlueCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_blackplus2)) {
            game.getPlayers().get(PLAYER2).setBlackCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_blackminus2)) {
            game.getPlayers().get(PLAYER2).setBlackCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_redplus2)) {
            game.getPlayers().get(PLAYER2).setRedCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_redminus2)) {
            game.getPlayers().get(PLAYER2).setRedCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_greenplus2)) {
            game.getPlayers().get(PLAYER2).setGreenCounter(ADDITION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_greenminus2)) {
            game.getPlayers().get(PLAYER2).setGreenCounter(SUBTRACTION, 1);
            projectPlayer(PLAYER2);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_delete2)) {
            if (doublePressDeleteMana2) {
                game.getPlayers().get(PLAYER2).deleteManaCounter();
                projectPlayer(PLAYER2);
                doublePressDeleteMana2 = false;
            }
            doublePressDeleteMana2 = true;
            Toast.makeText(this, getResources().getString(R.string.deleteMana), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doublePressDeleteMana2 = false;
                }
            }, 1000);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_slideout2)) {
            LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.ll_lgame_manacounter2);

            v.setVisibility(View.INVISIBLE);
            Animation slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out);
            linearLayout.startAnimation(slide_out);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    viewGroup.findViewById(R.id.tv_lgame_slidein2).setVisibility(View.VISIBLE);
                }
            }, 1500);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        if (v == viewGroup.findViewById(R.id.tv_lgame_slidein2)) {
            LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.ll_lgame_manacounter2);

            v.setVisibility(View.INVISIBLE);
            Animation slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in);
            linearLayout.startAnimation(slide_in);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    viewGroup.findViewById(R.id.tv_lgame_slideout2).setVisibility(View.VISIBLE);
                }
            }, 1500);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
}

