package com.mugen.visionartificial.Presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Util.ImageUtil;

import java.util.concurrent.ExecutionException;

/**
 * Created by root on 18/04/15.
 */
public class BorderFinder {
    ImageDecoderPresenter presenter;
    PixelImage pixelImage;
    private int[][] mA;
    private int[][] mB;
    private int[][] mF;
    private boolean drawLetters;
    private static final String VERTICAL = "VERTICAL";
    private static final String HORIZONTAL = "HORIZONTAL";

    public BorderFinder(PixelImage pixelImage, boolean drawLetters,ImageDecoderPresenter presenter) {
        this.drawLetters = drawLetters;
        this.pixelImage = pixelImage;
        this.presenter=presenter;
    }

    public void findBorders(int[][] mat, int dimX, int dimY) {
        TotalConvolution totalConvolution=new TotalConvolution();
        totalConvolution.execute();
    }

    public void execute() {
        int[][] mat = pixelImage.getGrayScaleM();
        if (pixelImage.getFlag() == ImageFilterTask.GRAY_SCALE) {
            findBorders(mat, pixelImage.getDimX(), pixelImage.getDimY());
            pixelImage.setFlag(ImageFilterTask.BORDER);
        }
    }

    private class TotalConvolution extends AsyncTask<String, String, Object> {
        private String TAG ="Total Convolution";
        private PartialConvolution verticalConv, horizontalConv;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            presenter.mView.get().showProgressBar("Searching for borders");
            verticalConv = new PartialConvolution(VERTICAL);
            horizontalConv = new PartialConvolution(HORIZONTAL);
            horizontalConv.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            verticalConv.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }

        @Override
        protected Object doInBackground(String[] params) {
            try {
                horizontalConv.get();
                verticalConv.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mF = ImageUtil.totalConvolution(mA, mB, pixelImage.getDimX(), pixelImage.getDimY());
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            pixelImage.setBorderedM(mF);
            BorderDrawer bD =new BorderDrawer(pixelImage,drawLetters,presenter);
            bD.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            presenter.mView.get().dismissProgressBar();
        }
    }

    private class PartialConvolution extends AsyncTask<String, String, Object> {
        private String TAG="Partial Convolution";
        String direction;
        PartialConvolution(String direction) {
            this.direction = direction;
        }

        @Override
        protected Object doInBackground(String[] params) {
            if (direction == VERTICAL)
                mA = ImageUtil.verticalConvolutionPow(pixelImage.getGrayScaleM(), pixelImage.getDimX(), pixelImage.getDimY());
            if (direction == HORIZONTAL)
                mB = ImageUtil.horizontalConvolutionPow(pixelImage.getGrayScaleM(), pixelImage.getDimX(), pixelImage.getDimY());
            return null;
        }
    }
}
