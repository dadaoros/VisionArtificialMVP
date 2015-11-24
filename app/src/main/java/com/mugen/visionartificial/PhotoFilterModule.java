package com.mugen.visionartificial;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ORTEGON on 23/11/2015.
 */
@Module
public class PhotoFilterModule {
    private PhotoFilterApp app;
    public PhotoFilterModule(PhotoFilterApp app) {
        this.app=app;
    }
    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides @Singleton
    public Context provideContext() {
        return app;
    }
}
