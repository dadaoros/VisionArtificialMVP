package com.mugen.visionartificial.View;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mugen.visionartificial.Components.DaggerPhotoListComponent;
import com.mugen.visionartificial.Model.Photo;
import com.mugen.visionartificial.Modules.PhotoListModule;
import com.mugen.visionartificial.PhotoFilterApp;
import com.mugen.visionartificial.Presenter.PhotosListPresenter;
import com.mugen.visionartificial.Presenter.PresenterOps;
import com.mugen.visionartificial.R;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements ViewOps.PhotoListOps{
    public static final String BUNDLE_KEY="bundle key";
    private static final String TAG = "PhotoList_Fragment";
    ListView selfieListView;
    @Inject
    PresenterOps.PhotosListOps presenter;
    public PhotoListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPhotoListComponent.builder().photoFilterComponent(PhotoFilterApp.getApp(getActivity())
                .getComponent())
                .photoListModule(new PhotoListModule(this))
                .build().inject(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_photo_list, container, false);
        /*ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); */
        return view;
    }
    public void onResume() {
        super.onResume();
        presenter.loadPhotos();
    }

    @Override
    public void onPhotosLoadSuccess(List photoList, String response) {
        selfieListView=(ListView) getActivity().findViewById(R.id.list_selfies);
        SelfiesListAdapter adapter = new SelfiesListAdapter(photoList, getActivity());
        adapter.setNotifyOnChange(true);
        selfieListView.setAdapter(adapter);
        setListener(selfieListView);
    }

    @Override
    public void displayPhotosLoadFailed() {
        Toast.makeText(getActivity(),R.string.photolistload_fail,Toast.LENGTH_SHORT).show();
    }
    public void setListener(final ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).navigateToDisplayOnFullScreen(((Photo) listView.getAdapter().getItem(position)).getPath());
            }
        });
    }
    @Override
    public Context getActivityContext() {
        return getActivityContext();
    }
    @Override
    public Context getApplicationContext() {
        return getApplicationContext();
    }
}
