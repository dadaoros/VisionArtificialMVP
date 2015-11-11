package com.mugen.visionartificial.Presenter;

import android.util.Log;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.View.ViewOps;

import java.lang.ref.WeakReference;

/**
 * Created by ORTEGON on 05/11/2015.
 */
public class ImageDecoderPresenter implements PresenterOps.FullScreenOps {
    public WeakReference<ViewOps.FullScreenOps> mView;
    public static String TAG="ImagePresenter";
    public ImageDecoderPresenter(ViewOps.FullScreenOps view){
        mView = new WeakReference<>(view);

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
