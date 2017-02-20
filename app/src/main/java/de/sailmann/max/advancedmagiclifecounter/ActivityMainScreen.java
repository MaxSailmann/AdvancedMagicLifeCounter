package de.sailmann.max.advancedmagiclifecounter;

/*Copyright 2008 Google Inc.
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
*/


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityMainScreen extends AppCompatActivity implements View.OnClickListener {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layoutmainscreen);
        initializeViews();
        initializeStatistic();
    }

    public void initializeViews() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IMMORTAL.ttf");
        ((Globals) this.getApplication()).setTypeface(typeface);

        ((TextView) findViewById(R.id.tv_lmainscreen_title)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lmainscreen_title)).setMaxLines(2);

        ((Button) findViewById(R.id.b_lmainscreen_start)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((Button) findViewById(R.id.b_lmainscreen_statistic)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((Button) findViewById(R.id.b_lmainscreen_websearch)).setTypeface(((Globals) this.getApplication()).getTypeface());

        findViewById(R.id.b_lmainscreen_websearch).setOnClickListener(this);
        findViewById(R.id.b_lmainscreen_statistic).setOnClickListener(this);
        findViewById(R.id.b_lmainscreen_start).setOnClickListener(this);
    }

    public void initializeStatistic() {
        dbHandler = new DBHandler(this);
        ((Globals) this.getApplication()).setStatistic(new Statistic());

        Gson gson = new Gson();
        ArrayList<String> games = dbHandler.getGames();

        for (String s : games) {
            ((Globals) this.getApplication()).getStatistic().add(gson.fromJson(s, Game.class));
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.b_lmainscreen_start) {
            Intent intent = new Intent(this, ActivitySettings.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.b_lmainscreen_statistic) {
            Intent intent = new Intent(this, ActivityStatistic.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.b_lmainscreen_websearch) {
            Intent intent = new Intent(this, ActivityTutorial.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dbHandler.close();
        if (isFinishing()) {
            dbHandler.deleteGames();

            Gson gson = new Gson();
            ArrayList<String> json = new ArrayList<>();

            for (Game game : ((Globals) this.getApplication()).getStatistic().getGames()) {
                json.add(gson.toJson(game));
            }
            for (String s : json) {
                dbHandler.insert(s);
            }
        }
    }
}
