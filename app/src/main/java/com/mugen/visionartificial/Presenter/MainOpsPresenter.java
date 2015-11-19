package com.mugen.visionartificial.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Model.ProvidedModelOps;
import com.mugen.visionartificial.View.RequiredViewOps;
import com.mugen.visionartificial.common.GenericPresenter;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public class MainOpsPresenter
        extends GenericPresenter<RequiredPresenterOps,ProvidedModelOps,ImageFileManager>
        implements ProvidedPresenterOps.MainViewOps, RequiredPresenterOps{
    private WeakReference<RequiredViewOps.MainViewOps> mView;
    public static String TAG="ImagePresenter";


    @Override
    public void onCreate(RequiredViewOps.MainViewOps view) {
        mView = new WeakReference<>(view);

    }

    @Override
    public void onConfigurationChange(RequiredViewOps.MainViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfigurations) {
        // Destroy the model.
        getModel().onDestroy(isChangingConfigurations);
    }
    @Override
    public void saveActualPhoto(PixelImage p,Bitmap bitmap) {
        String fName = new File(p.getPhotoPath()).getName();
        try {
            String newPath = getModel().savePhoto(bitmap, fName, p.getFlag());
            mView.get().addPicturetoGallery(Uri.fromFile(new File(newPath)));
            mView.get().onPhotoSaveResult("Picture Saved Successfully");
        }catch (Exception e){
            mView.get().onPhotoSaveResult("Your Picture was not saved");
        }
    }

    @Override
    public File createImageFileBlank() {
        File photoFile;
        try {
            photoFile = getModel().createImageFile(ImageFileManager.NAMETAG_REGULAR);
            return photoFile;
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public Context getActivityContext() {
        return mView.get().getActivityContext();
    }

    @Override
    public Context getApplicationContext() {
        return mView.get().getApplicationContext();
    }
}
