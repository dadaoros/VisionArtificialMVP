package com.mugen.visionartificial.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mugen.visionartificial.Model.PixelImage;
import com.mugen.visionartificial.Presenter.ImageDecoderPresenter;
import com.mugen.visionartificial.Presenter.ImageFilterTask;
import com.mugen.visionartificial.R;


public class FullScreenImageFragment extends Fragment implements ViewOps.FullScreenOps{
    private static final String TAG = "FullScreenF";
    public ProgressDialog pDialog;
    ImageView imageView;
    ImageButton btnClose;
    private PixelImage pixelImage;
    private ImageButton bordersBtn;
    private ImageButton lettersBtn;
    ImageDecoderPresenter presenter;
    public FullScreenImageFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new ImageDecoderPresenter(this);

        setHasOptionsMenu(true);

        if (getArguments() != null)
            pixelImage= new PixelImage(
                    getArguments().getString(MainActivity.PHOTO_PATH_KEY),
                    ImageFilterTask.NORMAL);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_full_screen_image, container, false);
        btnClose= (ImageButton)view.findViewById(R.id.btnExit);
        bordersBtn=(ImageButton)view.findViewById(R.id.button_borders);
        lettersBtn=(ImageButton)view.findViewById(R.id.button_letters);
        bordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.findBorders(pixelImage, false);

            }
        });
        lettersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.findBorders(pixelImage, true);

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view ;
    }
    @Override
    public void onResume(){
        super.onResume();
        if(imageView==null) {
            imageView = (ImageView) getActivity().findViewById(R.id.full_screen_imageview);

            presenter.toGrayScale(pixelImage);
        }
    }

    public void onPause(){
        super.onPause();
        dismissProgressBar();
    }
    public void onStop(){
        super.onStop();
        imageView.destroyDrawingCache();
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save_item).setVisible(true);
        menu.findItem(R.id.action_pick_photo).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void displayImage(Bitmap fullScreenImage) {
        imageView.setImageBitmap(fullScreenImage);
    }

    @Override
    public void onImageDisplayFailed(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public Context getActivityContext() {
        return this.getActivity();
    }

    @Override
    public Context getApplicationContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showProgressBar(String message) {
        pDialog= ProgressDialog.show(getActivity(), "", message);
    }

    @Override
    public void dismissProgressBar() {
        if(pDialog!=null)pDialog.dismiss();
    }

    public PixelImage getPixelImage() {
        return pixelImage;
    }

    public Bitmap getActualBitmap() {
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }
}
