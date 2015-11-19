package com.mugen.visionartificial.Presenter;

import android.graphics.Bitmap;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.View.RequiredViewOps;
import com.mugen.visionartificial.common.PresenterOps;

import java.io.File;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public interface ProvidedPresenterOps {
    public interface PhotosListOps extends PresenterOps <RequiredViewOps.PhotoListOps>{
        public void loadPhotos();
    }
    public interface MainViewOps extends PresenterOps<RequiredViewOps.MainViewOps>{
        public void saveActualPhoto(PixelImage p,Bitmap bitmap);
        public File createImageFileBlank();
    }
    public interface FullScreenOps extends PresenterOps<RequiredViewOps.FullScreenOps>{
        public void convertToGrayScale(PixelImage pixelImage);
        public void findAndReplaceBorders(PixelImage pixelImage,boolean withLetters);
    }
}
