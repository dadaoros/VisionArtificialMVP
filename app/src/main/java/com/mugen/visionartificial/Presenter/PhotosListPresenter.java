package com.mugen.visionartificial.Presenter;

import android.os.AsyncTask;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.Photo;
import com.mugen.visionartificial.R;
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
        new AsyncTask<Void,Void,List>() {

            @Override
            protected List doInBackground(Void... voids) {
                List<Photo> selfies=imageFileManager.getPhotos();
                return selfies;
            }
            @Override
            protected void onPostExecute(List selfies){
                if(selfies!=null && !selfies.isEmpty()) {
                    view.get().onPhotosLoadSuccess(selfies, String.valueOf(R.string.photolistload_success));
                }else
                    view.get().displayPhotosLoadFailed();
            }
        }.execute();

    }

}
