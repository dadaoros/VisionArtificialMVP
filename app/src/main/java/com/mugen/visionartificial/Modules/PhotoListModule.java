package com.mugen.visionartificial.Modules;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Presenter.PhotosListPresenter;
import com.mugen.visionartificial.Presenter.PresenterOps;
import com.mugen.visionartificial.View.ViewOps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ORTEGON on 19/11/2015.
 */
@Module
public class PhotoListModule {
    ViewOps.PhotoListOps view;
    public PhotoListModule(ViewOps.PhotoListOps view){
        this.view=view;
    }
    @Provides
    public ViewOps.PhotoListOps provideView(){
        return view;
    }
    @Provides
    public PresenterOps.PhotosListOps providePresenter
            (ViewOps.PhotoListOps view,ImageFileManager interactor){
        return new PhotosListPresenter(view,interactor);
    }
}
