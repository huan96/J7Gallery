package com.galaxy.gallery.j7gallery.flagments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.adapters.AlbumAdapter;
import com.galaxy.gallery.j7gallery.objects.Album;
import com.galaxy.gallery.j7gallery.objects.Image;
import com.galaxy.gallery.j7gallery.until.ManagerGalary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peih Gnaoh on 8/20/2017.
 */

public class AlbumFragment extends Fragment {
    private RecyclerView recycleView;
    private AlbumAdapter albumAdapter;
    private ArrayList<Album> arrAlbum = new ArrayList<>();
    private ArrayList<Image> arrImage = new ArrayList<>();
    private ArrayList<String> arrBucketAlbum;
    private ArrayList<Album> arrAlbumSelect;
    private Context mContext;
    private boolean islongClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
        ManagerGalary managerGalary = new ManagerGalary(mContext);
        arrImage = managerGalary.getArrImage();
        arrBucketAlbum = new ArrayList<>();
        arrAlbumSelect = new ArrayList<>();
        islongClick = false;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        arrAlbum.clear();
        for (int i = 0; i < arrImage.size(); i++) {
            getBucket(arrImage.get(i).getBucket(), arrBucketAlbum);
        }
        for (int i = 0; i < arrBucketAlbum.size(); i++) {
            Album album = new Album(arrBucketAlbum.get(i), arrImage.get(0).getPath(), new ArrayList<Image>());
            for (int j = 0; j < arrImage.size(); j++) {
                if (arrBucketAlbum.get(i).equals(arrImage.get(j).getBucket())) {
                    album.addImage(arrImage.get(j));
                }
            }
            arrAlbum.add(album);
        }
        recycleView = (RecyclerView) rootView.findViewById(R.id.rcv_fr_album);
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 1));
        albumAdapter = new AlbumAdapter(mContext, arrAlbum, new AlbumAdapter.OnClickAlbum() {
            @Override
            public void onClickAlbum(int position) {
                Log.e("xxxxx", "xxxsx");
            }

            @Override
            public void onLongClickAlbum(int position) {

            }

            @Override
            public void onAddAlbumSelect(int position) {

            }

            @Override
            public void onRemoteAlbumSelect(int position) {

            }
        });
        recycleView.setAdapter(albumAdapter);
    }

    public void getBucket(String bucket, List<String> arrBucket) {
        boolean isAdd = false;
        for (int i = 0; i < arrBucket.size(); i++) {
            if (bucket.equals(arrBucket.get(i))) {
                isAdd = true;
                return;
            }
        }
        if (!isAdd) {
            arrBucketAlbum.add(bucket);
        }
    }

    public List<Album> getArrAlbum() {
        return arrAlbum;
    }

    public void setImagesAlbum(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }

}
