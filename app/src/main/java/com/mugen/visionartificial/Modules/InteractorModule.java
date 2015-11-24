package com.mugen.visionartificial.Modules;

import com.mugen.visionartificial.Model.ImageFileManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ORTEGON on 23/11/2015.
 */
@Module
public class InteractorModule {

    @Provides
    public ImageFileManager provideImageFileManagerInteractor(){
        return new ImageFileManager();
    }

}
