package com.mugen.visionartificial.Presenter;

import android.graphics.Bitmap;

import com.mugen.visionartificial.Model.PixelImage;

import java.io.File;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public interface PresenterOps {
    public interface PhotosListOps{
        public void loadPhotos();
    }
    public interface MainViewOps{
        public void saveActualPhoto(PixelImage p,Bitmap bitmap);
        public File createImageFileBlank();
    }
    public interface FullScreenOps{
        public void convertToGrayScale(PixelImage pixelImage);
        public void findAndReplaceBorders(PixelImage pixelImage,boolean withLetters);
    }
}
