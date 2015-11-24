package com.mugen.visionartificial.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.View.ViewOps;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public class MainOpsPresenter implements PresenterOps.MainViewOps{
    public WeakReference<ViewOps.MainViewOps> mView;
    public ImageFileManager imageFileManager;
    public static String TAG="ImagePresenter";

    public MainOpsPresenter(ViewOps.MainViewOps view){
        mView = new WeakReference<>(view);
        imageFileManager=ImageFileManager.getImageFileManager();
    }
    @Override
    public void saveActualPhoto(PixelImage p,Bitmap bitmap) {
        String fName = new File(p.getPhotoPath()).getName();
        try {
            String newPath = imageFileManager.savePhoto(bitmap, fName, p.getFlag());
            mView.get().addPicturetoGallery(Uri.fromFile(new File(newPath)));
            mView.get().displayPhotoSaveResult("Picture Saved Successfully");
        }catch (Exception e){
            mView.get().displayPhotoSaveResult("Your Picture was not saved");
        }
    }

    @Override
    public File createImageFileBlank() {
        File photoFile;
        try {
            photoFile = imageFileManager.createImageFile(ImageFileManager.NAMETAG_REGULAR);
            return photoFile;
        }catch (Exception e){
            return null;
        }
    }

}
