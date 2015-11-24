package com.mugen.visionartificial;

import android.content.Context;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Modules.InteractorModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ORTEGON on 23/11/2015.
 */
@Singleton
@Component(
        modules = {
                PhotoFilterModule.class,
                InteractorModule.class
        }
)
public interface PhotoFilterComponent {
    Context getContext();
    ImageFileManager getImageFileManagerInteractor();
}
