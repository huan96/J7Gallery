package com.galaxy.gallery.j7gallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.flagments.AlbumFragment;
import com.galaxy.gallery.j7gallery.flagments.AlbumTimeFragment;
import com.galaxy.gallery.j7gallery.flagments.VideoFragment;
import com.galaxy.gallery.j7gallery.until.ManagerGalary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huand on 8/18/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private List<String> tabTitles;
    private Context context;
    private ManagerGalary managerGalary;
    private ArrayList arrImage;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new ArrayList<>();
        tabTitles.add(context.getString(R.string.pictures));
        tabTitles.add(context.getString(R.string.albums));
        tabTitles.add(context.getString(R.string.videos));
        tabTitles.add(context.getString(R.string.favorties));
        managerGalary = new ManagerGalary(context);
        arrImage = managerGalary.getArrImage();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        context.sendBroadcast(new Intent("SET_DEFAULT_LAYOUT"));
        switch (position) {
            case 0:
                return new AlbumTimeFragment();
            case 1:
                return new AlbumFragment();
            case 2:
                return new VideoFragment();
            default:
                return new AlbumTimeFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position);
    }

}