package com.galaxy.gallery.j7gallery.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.galaxy.gallery.j7gallery.flagments.ShowFragment;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huand on 9/28/2017.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {

    private List<String> listPath;
    private Context mContext;
    private PhotoView photoView;

    public PhotoViewPagerAdapter(FragmentManager fm, Context context, List<String> paths) {
        super(fm, context);
        listPath = new ArrayList<>();
        listPath = paths;
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        return new ShowFragment(listPath.get(position));
    }

    @Override
    public int getCount() {
        return listPath.size();
    }

}
