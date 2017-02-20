package de.sailmann.max.advancedmagiclifecounter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 12.01.2017.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Game> games;
    private SimpleDateFormat simpleDateFormat;
    private Typeface typeface;

    public MyAdapter(Context context, ArrayList<Game> games, Typeface typeface) {
        this.context = context;
        this.games = games;
        simpleDateFormat = new SimpleDateFormat("hh:mm");
        this.typeface = typeface;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Game game = games.get(games.size() - (position + 1));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_listview, null);
        TextView textView_date = (TextView) view.findViewById(R.id.tv_flistview_date);
        TextView textView_mode = (TextView) view.findViewById(R.id.tv_flistview_mode);


        String date = DateFormat.getDateInstance().format(game.getStartTime());
        //String timeStart = simpleDateFormat.format(game.getStartTime());
        //String timeEnd = simpleDateFormat.format(game.getEndTime());

        textView_date.setText(date);
        textView_date.setTypeface(typeface);
        textView_mode.setTypeface(typeface);

        switch (game.getGameMode()) {
            case Mode.MODE_2v2:
                textView_mode.setText(context.getResources().getString(R.string._2v2));
                break;
            case Mode.MODE_2v2v2:
                textView_mode.setText(context.getResources().getString(R.string._2v2v2));
                break;
            case Mode.MODE_2v2v2v2:
                textView_mode.setText(context.getResources().getString(R.string._2v2v2v2));
                break;
            case Mode.MODE_3v3:
                textView_mode.setText(context.getResources().getString(R.string._3v3));
                break;
            case Mode.MODE_3v3v3:
                textView_mode.setText(context.getResources().getString(R.string._3v3v3));
                break;
            case Mode.MODE_4v4:
                textView_mode.setText(context.getResources().getString(R.string._4v4));
                break;
            case Mode.MODE_VS:
                textView_mode.setText(context.getResources().getString(R.string._VS));
                break;
            default:
                textView_mode.setText("");
                break;
        }

        return view;
    }
}
