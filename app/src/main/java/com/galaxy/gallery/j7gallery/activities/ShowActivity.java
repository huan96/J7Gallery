package com.galaxy.gallery.j7gallery.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.adapters.PhotoViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private RelativeLayout layoutTop;
    private ImageView btnBack, btnLike, btnPlay;
    private LinearLayout layoutBottom, btnShare, btnDelete;
    private String path;
    private List<String> listPath;
    private int defaultPosition;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        initViews();
        setAuoHideBar();
    }

    private void setAuoHideBar() {

    }

    private void initViews() {
        listPath = new ArrayList<>();
        path = "";
        defaultPosition = 0;
        try {
            listPath = getIntent().getStringArrayListExtra("ALL_PATH");
            defaultPosition = getIntent().getIntExtra("POSITION", 0);
        } catch (Exception e) {
            Log.e("ShowActivity", e.toString());
        }
        viewPager = (ViewPager) findViewById(R.id.vp_show);
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(getSupportFragmentManager(), ShowActivity.this, listPath);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(defaultPosition);

        layoutTop = (RelativeLayout) findViewById(R.id.layout_top);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        btnShare = (LinearLayout) findViewById(R.id.btn_share);
        btnDelete = (LinearLayout) findViewById(R.id.btn_delete);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnLike = (ImageView) findViewById(R.id.btn_like);
        btnPlay = (ImageView) findViewById(R.id.btn_play);

        //setOnClickListener

        btnShare.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_share:
                share();
                break;
            default:
                break;
        }
    }

    private void share() {
    }

    private void delete() {
    }
}
