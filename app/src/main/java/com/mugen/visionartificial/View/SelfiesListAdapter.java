package com.mugen.visionartificial.View;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mugen.visionartificial.Model.Photo;

import java.util.List;

import com.mugen.visionartificial.R;

/**
 * Created by root on 21/03/15.
 */
public class SelfiesListAdapter extends ArrayAdapter {
    List<Photo> selfies;
    Context ctx;
    public SelfiesListAdapter(List selfies, Context ctx) {
        super(ctx, R.layout.selfie_item,selfies);
        this.selfies=selfies;
        this.ctx=ctx;

    }
    @Override
    public int getCount() {
        return selfies.size();
    }

    @Override
    public Object getItem(int position) {
        return selfies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Photo> getSelfiesList(){
        return selfies;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) view = ((Activity)ctx).getLayoutInflater().inflate(R.layout.selfie_item, null);

        TextView selfieName= (TextView) view.findViewById(R.id.photo_name);
        TextView selfieDayWeek= (TextView) view.findViewById(R.id.day_week);
        TextView selfieDate= (TextView) view.findViewById(R.id.photo_date);
        ImageView selfieMini=(ImageView)view.findViewById(R.id.image_view);
        Photo photo =selfies.get(position);
        selfieName.setText(photo.getName());
        selfieDayWeek.setText(" "+ photo.getDayWeek());
        selfieDate.setText(photo.getDatetime().toString());
        selfieMini.setImageBitmap(photo.getMini());

        return view;
    }
}
