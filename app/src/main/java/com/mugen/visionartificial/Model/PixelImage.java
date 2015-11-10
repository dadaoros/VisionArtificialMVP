package com.mugen.visionartificial.Model;

/**
 * Created by root on 17/04/15.
 */
public class PixelImage {
    private String photoPath;
    private String flag;
    private int[][] grayScaleM=null;
    private int[][] borderedM=null;
    private int width;
    private int height;
    public PixelImage(String path,String flag){
        photoPath=path;
        this.flag=flag;
    }

    public PixelImage() {

    }
    public void setFlag(String flag){
        this.flag=flag;
    }
    public String getFlag(){
        return flag;
    }
    public String getPhotoPath() {
        return photoPath;
    }

    public int[][] getGrayScaleM() {
        return grayScaleM;
    }

    public void setGrayScaleM(int[][] grayScaleM) {
        this.grayScaleM = grayScaleM;
    }

    public void setBorderedM(int[][] mat) {
        this.borderedM=mat;
    }

    public int getDimX() {
        return width;
    }

    public int getDimY() {
        return height;
    }
    public void setDimensions(int x, int y){
        height=y;
        width=x;
    }

    public int[][] getBorderedM() {
        return borderedM;
    }
}
