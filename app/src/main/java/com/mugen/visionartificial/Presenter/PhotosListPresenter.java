package com.mugen.visionartificial.Presenter;

import android.os.AsyncTask;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.Photo;
import com.mugen.visionartificial.Model.ProvidedModelOps;
import com.mugen.visionartificial.R;
import com.mugen.visionartificial.View.RequiredViewOps;
import com.mugen.visionartificial.common.GenericPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public class PhotosListPresenter
        extends GenericPresenter<RequiredPresenterOps,ProvidedModelOps,ImageFileManager>
        implements ProvidedPresenterOps.PhotosListOps {
    WeakReference<RequiredViewOps.PhotoListOps> mView;

    @Override
    public void onCreate(RequiredViewOps.PhotoListOps view) {
        mView=new WeakReference<RequiredViewOps.PhotoListOps>(view);
    }

    @Override
    public void onConfigurationChange(RequiredViewOps.PhotoListOps view) {
        mView=new WeakReference<RequiredViewOps.PhotoListOps>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfigurations) {
        getModel().onDestroy(isChangingConfigurations);
    }
    @Override
    public void loadPhotos() {
        new AsyncTask<Void,Void,List>() {

            @Override
            protected List doInBackground(Void... voids) {
                List<Photo> selfies=getModel().getPhotos();
                return selfies;
            }
            @Override
            protected void onPostExecute(List selfies){
                if(selfies!=null && !selfies.isEmpty()) {
                    mView.get().onPhotosLoadSuccess(selfies, String.valueOf(R.string.photolistload_success));
                }else
                    mView.get().displayPhotosLoadFailed();
            }
        }.execute();

    }

}
