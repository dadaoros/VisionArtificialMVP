package com.mugen.visionartificial.View;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ORTEGON on 05/11/2015.
 */
public interface ViewOps {
    interface MainViewOps extends ContextView{
        void attemptPhoto();
        void photoAttemptFailed();
        void pickImage();
        void displayFullScreen(String path);
    }
    interface FullScreenOps extends ContextView{
        public void showProgressBar(String message);
        public void dismissProgressBar();
        public void displayImage(Bitmap fullScreenImage);
    }
    interface PhotoListOps extends ContextView{
        public void onPhotosLoadSuccess(List photoList,String response);
        public void onPhotosLoadFailed(String response);
    }
}
