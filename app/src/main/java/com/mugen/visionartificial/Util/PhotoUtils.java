package com.mugen.visionartificial.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by ORTEGON on 19/11/2015.
 */
public class PhotoUtils {
    public String getImagePathFromUri(Context context,Uri selectedImage){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        cursor.close();
        return filePath;
    }
}
