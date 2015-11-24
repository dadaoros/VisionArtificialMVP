package com.mugen.visionartificial.Components;

import android.app.ListFragment;

import com.mugen.visionartificial.ActivityScope;
import com.mugen.visionartificial.Modules.PhotoListModule;
import com.mugen.visionartificial.PhotoFilterComponent;
import com.mugen.visionartificial.Presenter.PresenterOps;
import com.mugen.visionartificial.View.PhotoListFragment;

import dagger.Component;

/**
 * Created by ORTEGON on 19/11/2015.
 */
@ActivityScope
@Component(
        dependencies = PhotoFilterComponent.class,
        modules = PhotoListModule.class
)
public interface PhotoListComponent {

    void inject(PhotoListFragment searchFragment);

    PresenterOps.PhotosListOps getPresenter();
}

