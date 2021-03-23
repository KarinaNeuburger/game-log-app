package com.tep.gamelog.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tep.gamelog.R;
import com.tep.gamelog.model.GameSQLite;

import java.util.List;

/**
 * Created by Karina on 02/11/2018.
 */
/*

public class GameListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GameSQLite> gameList;


    public GameListAdapter (Context context, int layout, List<GameSQLite> gameList) {

        this.context = context;
        this.layout = layout;
        this.gameList = gameList;
    }

    @Override
    public int getCount(){ return gameList.size(); }

    @Override
    public Object getItem(int position) { return gameList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    private class ViewHolder {
        ImageView imageViewCover;
        TextView textViewTitle;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageViewCover = row.findViewById(R.id.imageViewCover);
            holder.textViewTitle = row.findViewById(R.id.textViewTitle);
            row.setTag(holder);

        }else{
            holder = (ViewHolder) row.getTag();
        }

        FilmSQLite filmSQLite = filmList.get(position);

        holder.textViewTitle.setText(filmSQLite.getTitle());

        byte[] filmCover = filmSQLite.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(filmCover, 0, filmCover.length);
        holder.imageViewCover.setImageBitmap(bitmap);

        return row;
    }
}*/