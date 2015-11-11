package com.mugen.visionartificial.View;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ORTEGON on 05/11/2015.
 */
public interface ViewOps {
    interface MainViewOps extends ContextView{
        void navigateToDisplayOnFullScreen(String path);
        void onPhotoSaveResult(String message);
        void onPhotoAttemptFailed(String Message);
        void addPicturetoGallery(Uri uri);
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
