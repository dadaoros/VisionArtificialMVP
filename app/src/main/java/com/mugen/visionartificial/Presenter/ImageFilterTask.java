package com.mugen.visionartificial.Presenter;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Util.ImageUtil;

/**
 * Created by root on 16/04/15.
 */
public class ImageFilterTask extends AsyncTask<String, Void, Bitmap> {
    PixelImage pixelImage;
    public static final String GRAY_SCALE="GRAY_SCALE";
    public static final String NORMAL="NORMAL";
    public static final String BORDER="BORDER";
    public ImageDecoderPresenter presenter;
    public ImageFilterTask(PixelImage pixelImage, ImageDecoderPresenter presenter){
        this.pixelImage=pixelImage;
        this.presenter=presenter;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        presenter.mView.get().showProgressBar("Converting to GrayScale");
       //
    }
    @Override
    protected Bitmap doInBackground(String... params) {

        String url = pixelImage.getPhotoPath();
        Bitmap bitmap= ImageUtil.decodeSampledBitmapFromFile(url, 400, 600);
        pixelImage.setDimensions(bitmap.getWidth(), bitmap.getHeight());
        int[][][] mat= ImageUtil.BitToMat(bitmap);
        if(!url.contains("GRAY")) {
            mat = ImageUtil.grayScale(mat, bitmap.getWidth(), bitmap.getHeight());
        }
        pixelImage.setGrayScaleM(ImageUtil.softGrayScale(mat, bitmap.getWidth(), bitmap.getHeight()));
        pixelImage.setFlag(GRAY_SCALE);
        bitmap=ImageUtil.MatToBit(mat,bitmap.getWidth(),bitmap.getHeight());
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        presenter.mView.get().displayImage(result);
        presenter.mView.get().dismissProgressBar();

    }

}
