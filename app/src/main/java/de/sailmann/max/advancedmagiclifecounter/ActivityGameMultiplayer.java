package de.sailmann.max.advancedmagiclifecounter;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Admin on 03.01.2017.
 */

public class ActivityGameMultiplayer extends ActivityGameInit implements View.OnClickListener {


    //Container der Player_frames
    private ArrayList<FrameLayout> frameLayouts;
    private int selectedPlayer = 0;

    //Container der Player_inputs
    private ArrayList<ViewGroup> viewGroups;
    private ArrayList<TextView> textViewsLife;
    private ArrayList<TextView> textViewsName;
    private ArrayList<TextView> textViewsPoison;
    private ArrayList<TextView> textViewsEnergy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeScreen();
        initializeFrames();
        frameSelection();
        projectPlayer();
    }

    public void initializeScreen() {

        switch (numberOfPlayers) {

            case 3:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_3player_frame, null));
                fillFrameLayout3(viewGroup);
                break;
            case 4:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_4player_frame, null));
                fillFrameLayout4(viewGroup);
                break;
            case 5:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_5player_frame, null));
                fillFrameLayout5(viewGroup);
                break;
            case 6:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_6player_frame, null));
                fillFrameLayout6(viewGroup);
                break;
            case 7:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_7player_frame, null));
                fillFrameLayout7(viewGroup);
                break;
            case 8:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_8player_frame, null));
                fillFrameLayout8(viewGroup);
                break;
            case 9:
                viewGroup.addView(getLayoutInflater().inflate(R.layout.fragment_9player_frame, null));
                fillFrameLayout9(viewGroup);
                break;
            default:
                break;
        }
    }

    public void initializeFrames() {

        textViewsEnergy = new ArrayList<>();
        textViewsName = new ArrayList<>();
        textViewsPoison = new ArrayList<>();
        textViewsLife = new ArrayList<>();

        viewGroups = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            viewGroups.add(frameLayouts.get(i));
        }
        for (ViewGroup vg : viewGroups) {
            vg.removeAllViews();
            switch (numberOfPlayers) {
                case 3:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_3player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f3playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f3playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f3playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f3playerinput_lifepoints));

                    break;
                case 4:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_4player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f4playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f4playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f4playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f4playerinput_lifepoints));
                    break;
                case 5:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_5player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f5playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f5playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f5playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f5playerinput_lifepoints));
                    break;
                case 6:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_6player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f6playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f6playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f6playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f6playerinput_lifepoints));
                    break;
                case 7:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_7player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f7playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f7playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f7playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f7playerinput_lifepoints));
                    break;
                case 8:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_8player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f8playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f8playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f8playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f8playerinput_lifepoints));
                    break;
                case 9:
                    vg.addView(getLayoutInflater().inflate(R.layout.fragment_9player_input, null));
                    textViewsName.add((TextView) vg.findViewById(R.id.tv_f9playerinput_name));
                    textViewsPoison.add((TextView) vg.findViewById(R.id.tv_f9playerinput_poison));
                    textViewsEnergy.add((TextView) vg.findViewById(R.id.tv_f9playerinput_energy));
                    textViewsLife.add((TextView) vg.findViewById(R.id.tv_f9playerinput_lifepoints));
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            textViewsName.get(i).setTypeface(((Globals) this.getApplication()).getTypeface());
            textViewsName.get(i).setText(game.getPlayers().get(i).getName());
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            textViewsEnergy.get(i).setTypeface(((Globals) this.getApplication()).getTypeface());
            textViewsEnergy.get(i).setText(String.valueOf(game.getPlayers().get(i).getEnergyCounter()));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            textViewsLife.get(i).setTypeface(((Globals) this.getApplication()).getTypeface());
            textViewsLife.get(i).setText(String.valueOf(game.getPlayers().get(i).getLifePoints()));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            textViewsPoison.get(i).setTypeface(((Globals) this.getApplication()).getTypeface());
            textViewsPoison.get(i).setText(String.valueOf(game.getPlayers().get(i).getPoisonCounter()));
        }
    }

    public void frameSelection() {

        for (FrameLayout f : frameLayouts) {
            f.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
        }
        frameLayouts.get(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));


        for (int i = 0; i < frameLayouts.size(); i++) {
            final int number = i;
            frameLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (FrameLayout frLa : frameLayouts) {
                        frLa.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
                    }
                    v.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));
                    selectedPlayer = number;
                    projectPlayer();
                }
            });
        }
    }

    public void frameSelection(int bufferElement) {
        for (FrameLayout f : frameLayouts) {
            f.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechteck));
        }
        frameLayouts.get(bufferElement).setBackgroundDrawable(getResources().getDrawable(R.drawable.selected));
        selectedPlayer = bufferElement;
    }

    public void projectPlayer() {

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
        if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > 8) {
            tv_poison.setTextColor(getResources().getColor(R.color.red));
        }

        tv_colorless.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getColorlessCounter()));
        tv_white.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getWhiteCounter()));
        tv_blue.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlueCounter()));
        tv_black.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getBlackCounter()));
        tv_red.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getRedCounter()));
        tv_green.setText(String.valueOf(game.getPlayers().get(selectedPlayer).getGreenCounter()));
    }

    public void projectPlayer(int undoElement) {

        tv_name.setText(game.getPlayers().get(undoElement).getName());
        tv_poison.setText(String.valueOf(game.getPlayers().get(undoElement).getPoisonCounter()));
        tv_lifepoints.setText(String.valueOf(game.getPlayers().get(undoElement).getLifePoints()));
        tv_energy.setText(String.valueOf(game.getPlayers().get(undoElement).getEnergyCounter()));

        if (game.getPlayers().get(undoElement).getLifePoints() >= (game.getStartLifePoints() / 2)) {
            tv_lifepoints.setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(undoElement).getLifePoints() < (game.getStartLifePoints() / 2)) {
            tv_lifepoints.setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(undoElement).getLifePoints() < (game.getStartLifePoints() / 5)) {
            tv_lifepoints.setTextColor(getResources().getColor(R.color.red));
        }

        if (game.getPlayers().get(undoElement).getPoisonCounter() <= 5) {
            tv_poison.setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(undoElement).getPoisonCounter() > 5) {
            tv_poison.setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(undoElement).getPoisonCounter() > 8) {
            tv_poison.setTextColor(getResources().getColor(R.color.red));
        }

        tv_colorless.setText(String.valueOf(game.getPlayers().get(undoElement).getColorlessCounter()));
        tv_white.setText(String.valueOf(game.getPlayers().get(undoElement).getWhiteCounter()));
        tv_blue.setText(String.valueOf(game.getPlayers().get(undoElement).getBlueCounter()));
        tv_black.setText(String.valueOf(game.getPlayers().get(undoElement).getBlackCounter()));
        tv_red.setText(String.valueOf(game.getPlayers().get(undoElement).getRedCounter()));
        tv_green.setText(String.valueOf(game.getPlayers().get(undoElement).getGreenCounter()));
    }

    public void projectFrame() {
        textViewsName.get(selectedPlayer).setText(game.getPlayers().get(selectedPlayer).getName());
        textViewsEnergy.get(selectedPlayer).setText(String.valueOf(game.getPlayers().get(selectedPlayer).getEnergyCounter()));

        textViewsLife.get(selectedPlayer).setText(String.valueOf(game.getPlayers().get(selectedPlayer).getLifePoints()));
        textViewsPoison.get(selectedPlayer).setText(String.valueOf(game.getPlayers().get(selectedPlayer).getPoisonCounter()));

        if (game.getPlayers().get(selectedPlayer).getLifePoints() >= (game.getStartLifePoints() / 2)) {
            textViewsLife.get(selectedPlayer).setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 2)) {
            textViewsLife.get(selectedPlayer).setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(selectedPlayer).getLifePoints() < (game.getStartLifePoints() / 5)) {
            textViewsLife.get(selectedPlayer).setTextColor(getResources().getColor(R.color.red));
        }

        if (game.getPlayers().get(selectedPlayer).getPoisonCounter() <= 5) {
            textViewsPoison.get(selectedPlayer).setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > 5) {
            textViewsPoison.get(selectedPlayer).setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(selectedPlayer).getPoisonCounter() > 7) {
            textViewsPoison.get(selectedPlayer).setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void projectFrame(int undoElement) {
        textViewsName.get(undoElement).setText(game.getPlayers().get(undoElement).getName());
        textViewsEnergy.get(undoElement).setText(String.valueOf(game.getPlayers().get(undoElement).getEnergyCounter()));

        textViewsLife.get(undoElement).setText(String.valueOf(game.getPlayers().get(undoElement).getLifePoints()));
        textViewsPoison.get(undoElement).setText(String.valueOf(game.getPlayers().get(undoElement).getPoisonCounter()));

        if (game.getPlayers().get(undoElement).getLifePoints() >= (game.getStartLifePoints() / 2)) {
            textViewsLife.get(undoElement).setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(undoElement).getLifePoints() < (game.getStartLifePoints() / 2)) {
            textViewsLife.get(undoElement).setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(undoElement).getLifePoints() < (game.getStartLifePoints() / 5)) {
            textViewsLife.get(undoElement).setTextColor(getResources().getColor(R.color.red));
        }

        if (game.getPlayers().get(undoElement).getPoisonCounter() <= 5) {
            textViewsPoison.get(undoElement).setTextColor(getResources().getColor(R.color.black));
        }
        if (game.getPlayers().get(undoElement).getPoisonCounter() > 5) {
            textViewsPoison.get(undoElement).setTextColor(getResources().getColor(R.color.orange));
        }
        if (game.getPlayers().get(undoElement).getPoisonCounter() > 7) {
            textViewsPoison.get(undoElement).setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onClick(View v) {

        super.onClick(v);

        int bufferLife = game.getPlayers().get(selectedPlayer).getLifePoints();
        int bufferPoison = game.getPlayers().get(selectedPlayer).getPoisonCounter();
        int bufferEnergy = game.getPlayers().get(selectedPlayer).getEnergyCounter();

        if (v.getId() == R.id.ib_lgame_undo) {
            Log.d(this.getClass().getSimpleName(), "BufferSize = " + undoBuffer.getBufferData().size());
            game.setPlayers(undoBuffer.undo(game.getPlayers()));
            projectPlayer(undoBuffer.getElement());
            projectFrame(undoBuffer.getElement());
            frameSelection(undoBuffer.getElement());
        }
        if (v.getId() == R.id.ib_lgame_redo) {
            Log.d(this.getClass().getSimpleName(), "BufferSize = " + undoBuffer.getBufferData().size());
            game.setPlayers(undoBuffer.redo(game.getPlayers()));
            projectPlayer(undoBuffer.getElement());
            projectFrame(undoBuffer.getElement());
            frameSelection(undoBuffer.getElement());
        }

        if (v.getId() == R.id.b_lgame_poisonplus) {
            game.getPlayers().get(selectedPlayer).setPoisonCounter(ADDITION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, bufferLife, bufferPoison, game.getPlayers().get(selectedPlayer).getPoisonCounter(), bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_poisonminus) {
            game.getPlayers().get(selectedPlayer).setPoisonCounter(SUBTRACTION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, bufferLife, bufferPoison, game.getPlayers().get(selectedPlayer).getPoisonCounter(), bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_lifeplus) {
            game.getPlayers().get(selectedPlayer).setLifePoints(ADDITION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, game.getPlayers().get(selectedPlayer).getLifePoints(), bufferPoison, bufferPoison, bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_lifeplus5) {
            game.getPlayers().get(selectedPlayer).setLifePoints(ADDITION, 5);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, game.getPlayers().get(selectedPlayer).getLifePoints(), bufferPoison, bufferPoison, bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_lifeminus) {
            game.getPlayers().get(selectedPlayer).setLifePoints(SUBTRACTION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, game.getPlayers().get(selectedPlayer).getLifePoints(), bufferPoison, bufferPoison, bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_lifeminus5) {
            game.getPlayers().get(selectedPlayer).setLifePoints(SUBTRACTION, 5);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, game.getPlayers().get(selectedPlayer).getLifePoints(), bufferPoison, bufferPoison, bufferEnergy, bufferEnergy);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_energyplus) {
            game.getPlayers().get(selectedPlayer).setEnergyCounter(ADDITION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, bufferLife, bufferPoison, bufferPoison, bufferEnergy, game.getPlayers().get(selectedPlayer).getEnergyCounter());
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.b_lgame_energyminus) {
            game.getPlayers().get(selectedPlayer).setEnergyCounter(SUBTRACTION, 1);
            undoBuffer.add(selectedPlayer, game.getPlayers().get(selectedPlayer).getName(), bufferLife, bufferLife, bufferPoison, bufferPoison, bufferEnergy, game.getPlayers().get(selectedPlayer).getEnergyCounter());
            projectPlayer();
            projectFrame();
        }


        if (v.getId() == R.id.tv_lgame_colorlessplus) {
            game.getPlayers().get(selectedPlayer).setColorlessCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_colorlessminus) {
            game.getPlayers().get(selectedPlayer).setColorlessCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_whiteplus) {
            game.getPlayers().get(selectedPlayer).setWhiteCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_whiteminus) {
            game.getPlayers().get(selectedPlayer).setWhiteCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_blueplus) {
            game.getPlayers().get(selectedPlayer).setBlueCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_blueminus) {
            game.getPlayers().get(selectedPlayer).setBlueCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_blackplus) {
            game.getPlayers().get(selectedPlayer).setBlackCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_blackminus) {
            game.getPlayers().get(selectedPlayer).setBlackCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_redplus) {
            game.getPlayers().get(selectedPlayer).setRedCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_redminus) {
            game.getPlayers().get(selectedPlayer).setRedCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_greenplus) {
            game.getPlayers().get(selectedPlayer).setGreenCounter(ADDITION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_greenminus) {
            game.getPlayers().get(selectedPlayer).setGreenCounter(SUBTRACTION, 1);
            projectPlayer();
            projectFrame();
        }
        if (v.getId() == R.id.tv_lgame_delete) {
            if (doublePressDeleteMana) {
                game.getPlayers().get(selectedPlayer).deleteManaCounter();
                projectPlayer();
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
    }

    public void fillFrameLayout3(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_3player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_3player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_3player_3));
    }

    public void fillFrameLayout4(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_4player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_4player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_4player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_4player_4));
    }

    public void fillFrameLayout5(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_5player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_5player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_5player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_5player_4));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_5player_5));
    }

    public void fillFrameLayout6(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_4));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_5));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_6player_6));
    }

    public void fillFrameLayout7(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_4));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_5));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_6));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_7player_7));
    }

    public void fillFrameLayout8(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_4));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_5));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_6));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_7));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_8player_8));
    }

    public void fillFrameLayout9(ViewGroup viewGroup) {
        frameLayouts = new ArrayList<>();
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_1));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_2));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_3));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_4));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_5));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_6));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_7));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_8));
        frameLayouts.add((FrameLayout) viewGroup.findViewById(R.id.fl_9player_9));
    }
}
