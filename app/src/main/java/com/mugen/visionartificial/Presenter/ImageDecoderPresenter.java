package com.mugen.visionartificial.Presenter;

import android.util.Log;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Model.ProvidedModelOps;
import com.mugen.visionartificial.View.RequiredViewOps;
import com.mugen.visionartificial.common.GenericPresenter;

import java.lang.ref.WeakReference;

/**
 * Created by ORTEGON on 05/11/2015.
 */
public class ImageDecoderPresenter
        extends GenericPresenter<RequiredPresenterOps,ProvidedModelOps,ImageFileManager>
        implements ProvidedPresenterOps.FullScreenOps {

    public WeakReference<RequiredViewOps.FullScreenOps> mView;
    public static String TAG="ImagePresenter";

    @Override
    public void onCreate(RequiredViewOps.FullScreenOps view) {
        mView = new WeakReference<>(view);
    }
    @Override
    public void onConfigurationChange(RequiredViewOps.FullScreenOps view) {
        mView = new WeakReference<>(view);

    }

    @Override
    public void onDestroy(boolean isChangingConfigurations) {
        getModel().onDestroy(isChangingConfigurations);
    }
    @Override
    public void convertToGrayScale(PixelImage image){
        Log.d(TAG, "togray");

        try {
            ImageFilterTask imageLoader = new ImageFilterTask(image,this);
            imageLoader.execute(ImageFilterTask.GRAY_SCALE);
        } catch (Exception e){

        }
    }
    @Override
    public void findAndReplaceBorders(PixelImage pixelImage, boolean withLetters) {
        BorderFinder bF=new BorderFinder(pixelImage,withLetters,this);
        bF.execute();
    }

}
