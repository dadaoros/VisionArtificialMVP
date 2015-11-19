package com.mugen.visionartificial.Model;

import android.graphics.Bitmap;

import com.mugen.visionartificial.Presenter.RequiredPresenterOps;
import com.mugen.visionartificial.common.ModelOps;

import java.io.File;
import java.util.List;

/**
 * Created by ORTEGON on 10/11/2015.
 */
public interface ProvidedModelOps extends ModelOps<RequiredPresenterOps>{
    public List getPhotos();
    public String savePhoto(Bitmap image,String name,String flag);
    public File createImageFile(String flag);
}
