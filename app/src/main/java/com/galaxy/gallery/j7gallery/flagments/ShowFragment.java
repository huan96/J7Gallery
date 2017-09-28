package com.galaxy.gallery.j7gallery.flagments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.galaxy.gallery.j7gallery.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

/**
 * Created by huand on 9/21/2017.
 */

public class ShowFragment extends Fragment {
    private String path;
    private Context mContext;
    private PhotoView photoView;

    public ShowFragment(String path) {
        this.path = path;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        photoView = (PhotoView) rootView.findViewById(R.id.img_show);
        Glide.with(mContext).load(Uri.fromFile(new File(path))).into(photoView);
    }
}
