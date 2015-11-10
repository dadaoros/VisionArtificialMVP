package com.mugen.visionartificial.View;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ORTEGON on 05/11/2015.
 */
public interface ViewOps {
    interface MainViewOps extends ContextView{
        void onPhotoAttempt();
        void onPhotoAttemptFailed(String Message);
        void onPickImageFromGallery();
        void displayFullScreen(String path);
        public void onPhotoSaveResult(String message);
    }
    interface FullScreenOps extends ContextView{
        public void showProgressBar(String message);
        public void dismissProgressBar();
        public void displayImage(Bitmap fullScreenImage);
        public void onImageDisplayFailed(String message);
    }
    interface PhotoListOps extends ContextView{
        public void onPhotosLoadSuccess(List photoList,String response);
        public void onPhotosLoadFailed(String response);
    }
}
