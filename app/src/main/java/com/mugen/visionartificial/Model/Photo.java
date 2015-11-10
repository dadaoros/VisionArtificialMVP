package com.mugen.visionartificial.Model;

import android.graphics.Bitmap;


import com.mugen.visionartificial.Util.Util;

import java.util.Date;

/**
 * Created by root on 21/03/15.
 */
public class Photo {
    private String name;
    private String dayWeek;
    private String path;
    private Bitmap mini;
    private Date datetime;
    public Photo(String name, Date datetime, Bitmap mini, String path){
        this.name = name;

        this.datetime = datetime;
        this.mini = mini;
        this.path = path;
        this.dayWeek = Util.getDayOfWeek(datetime.getDay());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getMini() {
        return mini;
    }

    public Date getDatetime() {
        return datetime;
    }
}
