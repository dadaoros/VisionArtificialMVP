package com.mugen.visionartificial.Util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mugen.visionartificial.Model.Letter;

/**
 * Created by root on 16/04/15.
 */

public class ImageUtil {
    static public int[][][] BitToMat(Bitmap bmp)
    {
        int picw = bmp.getWidth(); int pich = bmp.getHeight();
        int[] pix = new int[picw * pich];
        bmp.getPixels(pix, 0, picw, 0, 0, picw, pich);
        int matriz[][][] = new int[picw][pich][4];
        for (int y = 0; y < pich; y++)
            for (int x = 0; x < picw; x++)
            {
                int index = y * picw + x;
                matriz[x][y][0] = (pix[index] >> 24) & 0xff;
                matriz[x][y][1] = (pix[index] >> 16) & 0xff;
                matriz[x][y][2] = (pix[index] >> 8) & 0xff;
                matriz[x][y][3] = pix[index] & 0xff;
            }
        return matriz;
    }
    static public int[][][] grayScale(int mat[][][], int w, int h)
    {
        int matriz[][][] = new int[w][h][4];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
            {
                int r = mat[x][y][1];
                int g = mat[x][y][2];
                int b = mat[x][y][3];
                int R = (int)(0.299*r + 0.587*g + 0.114*b);
                matriz[x][y][1] = R; matriz[x][y][2] = R; matriz[x][y][3] = R;
            }
        return matriz;
    }
    static public int[][] softGrayScale(int mat[][][], int w, int h)
    {
        int matriz[][] = new int[w][h];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
            {
                int r = mat[x][y][1];
                int g = mat[x][y][2];
                int b = mat[x][y][3];
                int R = (int)(0.299*r + 0.587*g + 0.114*b);
                matriz[x][y]= R;
            }
        return matriz;
    }
    static public Bitmap MatToBit(int mat[][][], int w, int h)
    {
        int[] pix = new int[w * h];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
            {
                int index = y * w + x;
                pix[index] = 0xff000000 | (mat[x][y][1] << 16) | (mat[x][y][2] << 8) | mat[x][y][3];
            }
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        bm.setPixels(pix, 0, w, 0, 0, w, h);
        return bm;
    }
    public static Bitmap decodeSampledBitmapFromFile(String imagePath,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Calculate inSampleSize
        int scaleFactor = Math.min(photoW / reqWidth, photoH / reqHeight);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    public static int[][] horizontalConvolutionPow(int[][] mat,int w, int h){
        int[][] convolution;
        convolution = new int[w][h];

        for (int y = 0; y < h; y++){

            for (int x = 0; x < w; x++)
            {
                if(x+1==w)
                    //convolution[x][y]= (int)Math.pow(mat[x][y]-0,2);
                    convolution[x][y]=0;
                else
                    convolution[x][y]= (int) Math.pow(mat[x][y] - mat[x + 1][y], 2);
            }
        }

        Log.d("position x", "Convolucionada");
        return convolution;
    }
    public static int[][] verticalConvolutionPow(int[][] mat,int w, int h){
        int[][] convolution;
        convolution = new int[w][h];

        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
            {
                if(y+1==h)
                    //convolution[x][y]= (int)Math.pow(mat[x][y]-0,2);
                    convolution[x][y]=0;
                else
                    convolution[x][y]= (int) Math.pow(mat[x][y] - mat[x][y + 1], 2);
            }
        Log.d("position y", "Convolucionada");
        return convolution;
    }
    public static int[][] totalConvolution(int[][] matA,int[][] matB,int w, int h) {
        int [][] convolution= new int[w][h];
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
            {
                convolution[x][y]= (int) Math.sqrt(matA[x][y] + matB[x][y]);
            }
        Log.d("convolucion total", "Convolucionada");
        return convolution;
    }
    public static int[][][] replaceBorders(int[][] grayM,int[][] borders,int[] borderColor,int w, int h) {
        int[][][] mat=new int[w][h][4];
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
            {
                if(borders[x][y]>20) {
                    mat[x][y][0] = borderColor[0];
                    mat[x][y][1] = borderColor[1];
                    mat[x][y][2] = borderColor[2];
                    mat[x][y][3] = borderColor[3];
                }else{
                    int color= grayM[x][y];
                    mat[x][y][0] = 0;
                    mat[x][y][1] = color;
                    mat[x][y][2] = color;
                    mat[x][y][3] = color;
                }

            }
        Log.d("state", "bordes reemplazados");
        return mat;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int[][][] replaceWithLetters(int[][] grayScaleM, int[][] borderedM, int[] c, int dimX, int dimY) {
        int[][][] mat=new int[dimX][dimY][4];
        boolean isA=false;
        for (int y = 0; y < dimY; ){
            for (int x = 0; x < dimX; ) {
                if (isValidSpace(borderedM,x,y,dimX,dimY)) {
                    char l;
                    if(isA)l='a';else l='j';
                    addLetter(mat,x,y,dimX,dimY,l);
                    isA=!isA;
                }else{
                    addLastState(mat,grayScaleM,x,y,dimX,dimY);
                }
                x += 4;
             }
            y+=4;
        }
        Log.d("state", "letras añadidas");
        return mat;

    }

    private static void addLastState(int[][][] mat, int[][] grayScaleM, int x, int y, int dimX, int dimY) {
        int valorInicialX=x,valorInicialY=y;

            for (int j = y; j < valorInicialY+4 ; j++) {

                for (int i = x; i < valorInicialX + 4; i++) {
                    int color=0;
                    if(i+1<dimX && j+1<dimY) {
                        try {
                        color = grayScaleM[i][j];
                        mat[i][j][0] = 0;
                        mat[i][j][1] = color;
                        mat[i][j][2] = color;
                        mat[i][j][3] = color;
                        }catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }

                }
            }

    }

    private static void addLetter(int[][][] mat,int x, int y,int dimX,int dimY,char l) {
        int valorInicialX=x,valorInicialY=y;

            for (int j = y; j < valorInicialY + 4; j++) {
                for (int i = x; i < valorInicialX + 4; i++) {
                    if(i+1<dimX && j+1<dimY) {
                        if (Letter.isEqual(i % 4, j % 4, l)) {
                            mat[i][j][0] = 0;
                            mat[i][j][1] = 0;
                            mat[i][j][2] = 255;
                            mat[i][j][3] = 0;
                        } else {
                            mat[i][j][0] = 0;
                            mat[i][j][1] = 0;
                            mat[i][j][2] = 0;
                            mat[i][j][3] = 0;
                        }
                    }
                }
            }


    }

    private static boolean isValidSpace(int[][] borderedM,int x, int y,int dimX,int dimY) {
        int valorInicialX = x, valorInicialY = y;
        int valorTotal = 0;
        int valorFiltro = 20;

            for (int j = y; j < valorInicialY + 4; j++) {
                for (int i = x; i < valorInicialX + 4; i++) {
                    try {
                        if (i + 1 < dimX && j + 1 < dimY) {
                            if (borderedM[i][j] > valorFiltro)
                                valorTotal++;

                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            }


        if(valorTotal>3)
            return true;
        else
            return false;
    }
}
