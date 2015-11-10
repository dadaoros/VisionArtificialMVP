package com.mugen.visionartificial.View;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mugen.visionartificial.Model.ImageFileManager;
import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.R;

import java.io.File;
import java.io.IOException;



public class MainActivity extends AppCompatActivity implements ViewOps.MainViewOps,ContextView{
    static final String TAG="Main Activity";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQ_CODE_PICK_IMAGE=2;
    static final String NAMETAG_REGULAR="REGULAR";
    public static final String PHOTO_PATH_KEY = "photo path";
    PhotoListFragment listFragment;
    String mCurrentPhotoPath;
    String mCurrentPhotoName;
    FullScreenImageFragment imageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }
    private void initialize(){
        if(listFragment==null) {
            listFragment = new PhotoListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, listFragment);
            //listFragment.setRetainInstance(true);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_PICK_IMAGE:
                    displayFullScreen(getRealImagePath(data.getData()));
                    //TODO
                    break;
                case REQUEST_TAKE_PHOTO:
                    //listFragment.update();
                    displayFullScreen(mCurrentPhotoPath);
                    //TODO
                    break;
                default:
                    break;
            }
        }
    }
    public String getRealImagePath(Uri selectedImage){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public void onStop(){
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_menu_item:
                onPhotoAttempt();
                return true;
            case R.id.action_pick_photo:
                onPickImageFromGallery();
                return true;
            case R.id.action_save_item:
                onSaveActualPhoto();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void onSaveActualPhoto() {

        if(imageFragment!=null) {
            PixelImage p = imageFragment.getPixelImage();
            String fName = new File(p.getPhotoPath()).getName();
            try {
                String newPath = ImageFileManager.getImageFileManager().savePhoto(imageFragment.getActualBitmap(), fName, p.getFlag());
                galleryAddPic(newPath);
                Toast.makeText(this, "File succesfully saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPhotoAttempt() {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            dispatchTakePictureIntent();
            galleryAddPic(mCurrentPhotoPath);
        }else{
            onPhotoAttemptFailed("You need a Camera if you want to take a picture");
        }
    }

    @Override
    public void onPhotoAttemptFailed(String message) {
        Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onPickImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
    }

    @Override
    public void displayFullScreen(String path) {
        Bundle bundle=new Bundle();
        bundle.putString(MainActivity.PHOTO_PATH_KEY,path);
        imageFragment= new FullScreenImageFragment();
        imageFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, imageFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onPhotoSaveResult(String message) {

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = ImageFileManager.getImageFileManager().createImageFile(NAMETAG_REGULAR);
                mCurrentPhotoPath=photoFile.getAbsolutePath();
                mCurrentPhotoName = photoFile.getName();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    /**
     * Return the Activity context.
     */
    @Override
    public Context getActivityContext() {
        return this;
    }

    /**
     * Return the Application context.
     */
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    public void galleryAddPic(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


}
