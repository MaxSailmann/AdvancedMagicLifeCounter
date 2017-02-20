package de.sailmann.max.advancedmagiclifecounter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by Admin on 31.12.2016.
 */

public class ActivityStatistic extends AppCompatActivity implements View.OnClickListener {

    private Statistic statistic;
    private ViewGroup[] viewGroups;
    private CanvasBars[] cannvas;
    private double[] barHeight;
    private boolean delete = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutstatistic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        statistic = ((Globals) this.getApplication()).getStatistic();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeScreen();
        calculateBars();
        initializeListView();
    }

    public void initializeScreen() {

        findViewById(R.id.ib_lstatistic_delete).setOnClickListener(this);
        findViewById(R.id.ib_lstatistic_share).setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_lstatistic_gameduration)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_gamesplayed)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_numberofgameduration)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_numberofplayedgames)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_mostcolor)).setTypeface(((Globals) this.getApplication()).getTypeface());

        ((TextView) findViewById(R.id.tv_lstatistic_colorless)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_white)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_blue)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_black)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_red)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_green)).setTypeface(((Globals) this.getApplication()).getTypeface());

        ((TextView) findViewById(R.id.tv_lstatistic_mode)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_date)).setTypeface(((Globals) this.getApplication()).getTypeface());
        ((TextView) findViewById(R.id.tv_lstatistic_gamelist)).setTypeface(((Globals) this.getApplication()).getTypeface());

        ((TextView) findViewById(R.id.tv_lstatistic_numberofplayedgames)).setText(String.valueOf(statistic.getNumberOfGames()));

        //Berechnen der durchschnittlichen Spielzeit
        if (statistic.getNumberOfGames() > 0) {
            long duration = 0;
            for (Game g : statistic.getGames()) {
                duration += g.getDuration();
            }
            int seconds = (int) (duration / statistic.getNumberOfGames()) / 1000;

            int avg_seconds = seconds % 60;
            int avg_minutes = seconds / 60;
            int avg_hours = avg_minutes / 60;


            ((TextView) findViewById(R.id.tv_lstatistic_numberofgameduration)).setText(String.format("%02d", avg_hours) + ":" + String.format("%02d", avg_minutes % 60) + ":" + String.format("%02d", avg_seconds));

        } else {
            ((TextView) findViewById(R.id.tv_lstatistic_numberofgameduration)).setText("--:--:--");

        }
    }

    public void calculateBars() {
        cannvas = new CanvasBars[]{null, null, null, null, null, null};
        viewGroups = new ViewGroup[]{null, null, null, null, null, null};
        barHeight = new double[]{0, 0, 0, 0, 0, 0};

        viewGroups[0] = (ViewGroup) findViewById(R.id.fl_lstatistic_colorless);
        viewGroups[1] = (ViewGroup) findViewById(R.id.fl_lstatistic_white);
        viewGroups[2] = (ViewGroup) findViewById(R.id.fl_lstatistic_blue);
        viewGroups[3] = (ViewGroup) findViewById(R.id.fl_lstatistic_black);
        viewGroups[4] = (ViewGroup) findViewById(R.id.fl_lstatistic_red);
        viewGroups[5] = (ViewGroup) findViewById(R.id.fl_lstatistic_green);

        int[] colores = new int[]{0, 0, 0, 0, 0, 0};

        for (Game game : statistic.getGames()) {
            colores[0] += game.getColores()[0];
            colores[1] += game.getColores()[1];
            colores[2] += game.getColores()[2];
            colores[3] += game.getColores()[3];
            colores[4] += game.getColores()[4];
            colores[5] += game.getColores()[5];
        }

        ((TextView) findViewById(R.id.tv_lstatistic_colorless)).setText(String.valueOf(colores[0]));
        ((TextView) findViewById(R.id.tv_lstatistic_white)).setText(String.valueOf(colores[1]));
        ((TextView) findViewById(R.id.tv_lstatistic_blue)).setText(String.valueOf(colores[2]));
        ((TextView) findViewById(R.id.tv_lstatistic_black)).setText(String.valueOf(colores[3]));
        ((TextView) findViewById(R.id.tv_lstatistic_red)).setText(String.valueOf(colores[4]));
        ((TextView) findViewById(R.id.tv_lstatistic_green)).setText(String.valueOf(colores[5]));

        int sum = 0;
        for (int i = 0; i < colores.length; i++) {
            sum += colores[i];
        }
        boolean doubleHeight = true;
        for (int i = 0; i < colores.length; i++) {
            barHeight[i] = (double) colores[i] / (double) sum;
            if (barHeight[i] >= 0.5) {
                doubleHeight = false;
            }
        }

        if (doubleHeight) {
            for (int i = 0; i < barHeight.length; i++) {
                barHeight[i] *= 2;
            }
        }

        for (int i = 0; i < colores.length; i++) {
            cannvas[i] = new CanvasBars(this, i, barHeight[i], false);
            viewGroups[i].addView(cannvas[i], ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }


    }

    public void initializeListView() {
        if (statistic.getNumberOfGames() > 0) {
            ListView listView = (ListView) findViewById(R.id.lv_lstatistic_lv);
            MyAdapter myAdapter = new MyAdapter(this, statistic.getGames(), ((Globals) this.getApplication()).getTypeface());
            listView.setAdapter(myAdapter);

            registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setGame(position);


                }
            });
        }
    }

    public void setGame(int position) {
        ((Globals) this.getApplication()).setGame(statistic.getGames().get(statistic.getNumberOfGames() - position - 1));
        Intent intent = new Intent(this, ActivityStatisticInfo.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ib_lstatistic_delete) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.warning))
                    .setMessage(getResources().getString(R.string.deleteStatistic))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            delete = true;
                            finish();

                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        }
        if (v.getId() == R.id.ib_lstatistic_share) {

            Intent intent = new Intent(this, ActivityBluetooth.class);
            startActivity(intent);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lv_lstatistic_lv) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(getResources().getString(R.string.deleteGame));
            menu.add(Menu.NONE, 0, 0, getResources().getString(R.string.delete));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        statistic.getGames().remove(statistic.getNumberOfGames() - info.position - 1);

        if (statistic.getGames().size() > 0) {
            initializeScreen();
            calculateBars();
            initializeListView();
        } else {
            finish();
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (delete) {
            ((Globals) this.getApplication()).getStatistic().deleteGames();
        }
    }
}
