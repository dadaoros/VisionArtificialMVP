package com.mugen.visionartificial.Util;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by root on 21/03/15.
 */
public class ImageFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if ((pathname.getName().contains("GRAY") || pathname.getName().contains("REGULAR"))  && pathname.length()>0){
            return true;
        }
        //If the file is blank then delete it
        if (pathname.length()==0){
            pathname.delete();
        }
        return false;
    }
}
