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
import com.galaxy.gallery.j7gallery.adapters.ImageAdapter;
import com.galaxy.gallery.j7gallery.objects.Image;

import java.util.ArrayList;


public class ImageFragment extends Fragment {
    private RecyclerView recycleView;
    private ImageAdapter imageAdapter;
    private ArrayList<Image> arrImage = new ArrayList<>();
    private Context mContext;
    private ImageAdapter.OnClickImage onClickImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        recycleView = (RecyclerView) rootView.findViewById(R.id.rcv_fr_image);
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageAdapter = new ImageAdapter(arrImage, mContext, onClickImage);
        recycleView.setAdapter(imageAdapter);
    }

    public void notifiData(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
        imageAdapter.notifyDataSetChanged();
    }
}
