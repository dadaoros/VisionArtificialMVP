package com.mugen.visionartificial;

import android.app.Application;
import android.content.Context;

/**
 * Created by ORTEGON on 23/11/2015.
 */
public class PhotoFilterApp extends Application {
    private PhotoFilterComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    /**
     * The object graph contains all the instances of the objects
     * that resolve a dependency
     * */
    private void setupGraph() {
        component = DaggerPhotoFilterComponent
                .builder()
                .photoFilterModule(new PhotoFilterModule(this))
                .build();

    }

    public PhotoFilterComponent getComponent() {
        return component;
    }

    public static PhotoFilterApp getApp(Context context) {
        return (PhotoFilterApp) context.getApplicationContext();
    }
}
