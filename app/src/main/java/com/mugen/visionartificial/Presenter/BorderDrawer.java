package com.mugen.visionartificial.Presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Util.ImageUtil;

/**
 * Created by root on 18/04/15.
 */
public class BorderDrawer extends AsyncTask<Void,Void,Bitmap> {
    private static final String TAG="bORDERdRAwER";
    private boolean drawLetters;
    PixelImage pixelImage;
    ImageDecoderPresenter presenter;

    public static int[] RED = {0,255,0,0};
    public BorderDrawer(PixelImage pixelImage, boolean drawLetters,ImageDecoderPresenter presenter) {
        this.pixelImage=pixelImage;
        this.drawLetters=drawLetters;
        this.presenter=presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        int c[]= RED;
        int[][][] matriz=null;
        if(drawLetters) {
            Log.d(TAG, "DrawLetters");
            matriz = ImageUtil.replaceWithLetters(pixelImage.getGrayScaleM(), pixelImage.getBorderedM(), c, pixelImage.getDimX(), pixelImage.getDimY());
            pixelImage.setFlag(ImageFilterTask.BORDER+"2");
        }else
            matriz = ImageUtil.replaceBorders(pixelImage.getGrayScaleM(), pixelImage.getBorderedM(), c, pixelImage.getDimX(), pixelImage.getDimY());
        return ImageUtil.MatToBit(matriz,pixelImage.getDimX(),pixelImage.getDimY());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        presenter.mView.get().displayImage(bitmap);
    }


}
