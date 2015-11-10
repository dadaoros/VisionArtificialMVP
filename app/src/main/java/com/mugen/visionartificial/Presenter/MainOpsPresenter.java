package com.mugen.visionartificial.Presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.View.ViewOps;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public class MainOpsPresenter implements PresenterOps.MainViewOps{
    public WeakReference<ViewOps.MainViewOps> mView;
    public static String TAG="ImagePresenter";
    ImageFileManager imageFileManager;
    public MainOpsPresenter(ViewOps.MainViewOps view){
        mView = new WeakReference<>(view);
        imageFileManager=ImageFileManager.getImageFileManager();
    }
    @Override
    public void saveActualPhoto(PixelImage p,Bitmap bitmap) {
        String fName = new File(p.getPhotoPath()).getName();
        try {
            String newPath = ImageFileManager.getImageFileManager().savePhoto(bitmap, fName, p.getFlag());
            mView.get().addPicturetoGallery(Uri.fromFile(new File(newPath)));
            mView.get().onPhotoSaveResult("Picture Saved Successfully");
        }catch (IOException e){
            mView.get().onPhotoSaveResult("Your Picture was not saved");
        }
    }

}
