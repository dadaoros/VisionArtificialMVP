package com.mugen.visionartificial.Presenter;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.Photo;
import com.mugen.visionartificial.R;
import com.mugen.visionartificial.View.SelfiesListAdapter;
import com.mugen.visionartificial.View.ViewOps;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public class PhotosListPresenter implements PresenterOps.PhotosListOps {
    private ImageFileManager imageFileManager;
    WeakReference<ViewOps.PhotoListOps> view;
    public PhotosListPresenter(ViewOps.PhotoListOps view) {
        this.view=new WeakReference<ViewOps.PhotoListOps>(view);
        imageFileManager=ImageFileManager.getImageFileManager();
    }

    @Override
    public void loadPhotos() {
        List<Photo> selfies=imageFileManager.getSelfies();
        if(selfies!=null && !selfies.isEmpty()) {
            view.get().onPhotosLoadSuccess(selfies, String.valueOf(R.string.photolistload_success));
        }else
            view.get().onPhotosLoadFailed(String.valueOf(R.string.photolistload_fail));
    }
}
