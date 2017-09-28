package com.galaxy.gallery.j7gallery.flagments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.adapters.AlbumTimeVideoAdapter;
import com.galaxy.gallery.j7gallery.objects.AlbumTime;
import com.galaxy.gallery.j7gallery.objects.Image;
import com.galaxy.gallery.j7gallery.until.ManagerGalary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huand on 9/21/2017.
 */

public class VideoFragment extends Fragment {
    private RecyclerView recycleView;
    private AlbumTimeVideoAdapter albumTimeAdapter;
    private ArrayList<AlbumTime> arrAlbum = new ArrayList<>();
    private ArrayList<Image> arrImage = new ArrayList<>();
    private ArrayList<String> arrTimeAlbum;
    private ArrayList<AlbumTime> arrAlbumSelect;
    private Context mContext;
    private boolean islongClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
        ManagerGalary managerGalary = new ManagerGalary(mContext);
        arrImage = managerGalary.getAllVideo();
        arrTimeAlbum = new ArrayList<>();
        arrAlbumSelect = new ArrayList<>();
        islongClick = false;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        arrAlbum.clear();
        for (int i = 0; i < arrImage.size(); i++) {
            getTime(arrImage.get(i).getDate(), arrTimeAlbum);
        }
        for (int i = 0; i < arrTimeAlbum.size(); i++) {
            AlbumTime album = new AlbumTime(arrTimeAlbum.get(i), new ArrayList<Image>());
            for (int j = 0; j < arrImage.size(); j++) {
                if (arrTimeAlbum.get(i).equals(arrImage.get(j).getDate())) {
                    album.addImage(arrImage.get(j));
                }
            }
            arrAlbum.add(album);
        }
        recycleView = (RecyclerView) rootView.findViewById(R.id.rcv_fr_image);
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 1));
        albumTimeAdapter = new AlbumTimeVideoAdapter(mContext, arrAlbum);
        recycleView.setAdapter(albumTimeAdapter);
    }

    public void getTime(String time, List<String> arrTime) {
        boolean isAdd = false;
        for (int i = 0; i < arrTime.size(); i++) {
            if (time.equals(arrTime.get(i))) {
                isAdd = true;
                return;
            }
        }
        if (!isAdd) {
            arrTimeAlbum.add(time);
        }
    }

    public List<AlbumTime> getArrAlbum() {
        return arrAlbum;
    }

    public void setImagesAlbum(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }

}
