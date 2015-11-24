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

import com.mugen.visionartificial.Presenter.MainOpsPresenter;
import com.mugen.visionartificial.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ViewOps.MainViewOps,ContextView{
    static final String TAG="Main Activity";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQ_CODE_PICK_IMAGE=2;
    public static final String PHOTO_PATH_KEY = "photo path";
    PhotoListFragment listFragment;
    String mCurrentPhotoPath;
    String mCurrentPhotoName;
    FullScreenImageFragment imageFragment;
    MainOpsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }
    private void initialize(){
        presenter=new MainOpsPresenter(this);
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
                    navigateToDisplayOnFullScreen(getImagePathFromUri(data.getData()));
                    break;
                case REQUEST_TAKE_PHOTO:
                    navigateToDisplayOnFullScreen(mCurrentPhotoPath);
                    break;
                default:
                    break;
            }
        }
    }
    public String getImagePathFromUri(Uri selectedImage){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
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
                navigateToPhotoAttempt();
                return true;
            case R.id.action_pick_photo:
                navigateToPickImageFromGallery();
                return true;
            case R.id.action_save_item:
                saveActualPhoto();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void saveActualPhoto() {
        if(imageFragment!=null) {
            presenter.saveActualPhoto(imageFragment.getPixelImage(),imageFragment.getActualBitmap());
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
    public void displayPhotoAttemptFailed(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }

    public void navigateToPickImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
    }
    public void navigateToPhotoAttempt() {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            dispatchTakePictureIntent();
            addPicturetoGallery(Uri.fromFile(new File(mCurrentPhotoPath)));
        }else{
            displayPhotoAttemptFailed("You need a Camera if you want to take a picture");
        }
    }
    @Override
    public void navigateToDisplayOnFullScreen(String path) {
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
    public void displayPhotoSaveResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addPicturetoGallery(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = presenter.createImageFileBlank();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mCurrentPhotoPath=photoFile.getAbsolutePath();
                mCurrentPhotoName = photoFile.getName();
                takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    @Override
    public Context getActivityContext() {
        return this;
    }
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }


}
