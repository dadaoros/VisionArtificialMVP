package com.mugen.visionartificial.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.mugen.visionartificial.Model.Photo;
import com.mugen.visionartificial.Util.SelfieFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 21/03/15.
 */
public class ImageFileManager {
    private static ImageFileManager imageFileManager=new ImageFileManager();
    public static final int THUMB_DIM =50;

    private static File STORAGE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    private static final String TAG = "ImageFileManager";

    public List<Photo> getSelfies() {

        List<Photo> photoList = new ArrayList<Photo>();
        if (STORAGE_DIR.exists()) {
            for (File file : STORAGE_DIR.listFiles(new SelfieFileFilter())) {
                photoList.add(
                        new Photo(
                                file.getName()
                                , new Date(file.lastModified())
                                , getThumbnail(file.getAbsolutePath())
                                , file.getAbsolutePath()
                        )
                );
            }
        }
        return photoList;
    }

    private Bitmap getThumbnail(String photoPath) {
// Get the dimensions of the View
        int targetW = THUMB_DIM;
        int targetH = THUMB_DIM;
// Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
// Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
// Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }


    public File createImageFile(String flag) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_"+flag+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    public String savePhoto(Bitmap bitmap,String name,String flag) throws IOException {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        OutputStream fOut = null;
        name=name.replace(".jpg","");
        File file = new File(path, name+"_"+flag+".jpg"); // the File to save to
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap pictureBitmap = bitmap; // obtaining the Bitmap
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush();
        fOut.close(); // do not forget to close the stream
        return file.getAbsolutePath();
        //MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
    }


    public static ImageFileManager getImageFileManager(){
        return imageFileManager;
    }
}
