package com.mugen.visionartificial.Presenter;

/**
 * Created by ORTEGON on 09/11/2015.
 */
public interface PresenterOps {
    public interface PhotosListOps{
        public void loadPhotos();
    }
    public interface MainViewOps{
        public void saveActualPhoto();
    }
}
