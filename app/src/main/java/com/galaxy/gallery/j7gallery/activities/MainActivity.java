package com.galaxy.gallery.j7gallery.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.adapters.PagerAdapter;
import com.galaxy.gallery.j7gallery.dialogs.DeleteFileDialog;
import com.galaxy.gallery.j7gallery.dialogs.RateAppDialog;
import com.galaxy.gallery.j7gallery.until.FontCache;
import com.galaxy.gallery.j7gallery.until.SmoothCheckBox;
import com.galaxy.gallery.j7gallery.until.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSION =
            {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUESTCODE = 1;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout layoutAds;
    private BroadcastReceiver receiver;
    private RelativeLayout actionBar, defaultBar;
    private SmoothCheckBox checkBoxSelectAll;
    private TextView tvSelectItem;
    private List<String> listSelectPath;
    private int sizeSelected;
    private TextView btnDelete, btnShare;
    private boolean onLongClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnPermiss();
        initViews();
    }

    public void turnPermiss() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Utils.checkPermission(PERMISSION, this) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSION, REQUESTCODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Utils.checkPermission(PERMISSION, this) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(PERMISSION, REQUESTCODE);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void initViews() {
        onLongClick = false;
        sizeSelected = 0;
        listSelectPath = new ArrayList<>();
        layoutAds = (RelativeLayout) findViewById(R.id.layout_ads);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this));
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont();
        actionBar = (RelativeLayout) findViewById(R.id.layout_action_bar);
        defaultBar = (RelativeLayout) findViewById(R.id.layout_default_bar);
        checkBoxSelectAll = (SmoothCheckBox) findViewById(R.id.checkbox_select_all);
        tvSelectItem = (TextView) findViewById(R.id.tv_select_item);
        btnDelete = (TextView) findViewById(R.id.btn_delete);
        btnShare = (TextView) findViewById(R.id.btn_share);

        //setOnClickListener
        btnShare.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        checkBoxSelectAll.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    sendBroadcast(new Intent("ADD_ALL"));
                } else {
                    sendBroadcast(new Intent("REMOVE_ALL_SELECT"));
                }
            }
        });
        addReceiver();
    }

    private void addReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("ON_LONG_CLICK");
        filter.addAction("ADD_ITEM");
        filter.addAction("REMOVE_ITEM");
        filter.addAction("SET_DEFAULT_LAYOUT");
        filter.addAction("REMOVE_SELECT_ALL_IMAGE");
        filter.addAction("SELECT_ALL_IMAGE");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "ON_LONG_CLICK":
                        setLayoutAction();
                        break;
                    case "ADD_ITEM":
                        if (intent.getStringExtra("PATH") != null) {
                            listSelectPath.add(intent.getStringExtra("PATH"));
                            ++sizeSelected;
                            tvSelectItem.setText(String.valueOf(sizeSelected));
                            if (btnShare.getVisibility() == View.GONE) {
                                btnDelete.setVisibility(View.VISIBLE);
                                btnShare.setVisibility(View.VISIBLE);
                            }
                            Log.e("MainAtyvity.ADD_ITEM", String.valueOf(listSelectPath.size()));
                        }
                        break;
                    case "REMOVE_ITEM":
                        if (intent.getStringExtra("PATH") != null) {
                            listSelectPath.remove(intent.getStringExtra("PATH"));
                            --sizeSelected;
                            if (sizeSelected > 0) {
                                tvSelectItem.setText(String.valueOf(sizeSelected));
                            } else {
                                tvSelectItem.setText(R.string.select_item);
                                btnDelete.setVisibility(View.GONE);
                                btnShare.setVisibility(View.GONE);
                            }
                            Log.e("MainAtyvity.REMOVE_ITEM", String.valueOf(listSelectPath.size()));
                        }
                        break;
                    case "SET_DEFAULT_LAYOUT":
                        setLayoutDefaut();
                        break;
                    case "SELECT_ALL_IMAGE":
                        listSelectPath.clear();
                        listSelectPath = intent.getStringArrayListExtra("ALL_IMAGES");
                        Log.e("SELECT_ALL_IMAGE", String.valueOf(listSelectPath.size()));
                        break;
                    case "REMOVE_SELECT_ALL_IMAGE":
                        listSelectPath.clear();
                        break;
                    default:
                        break;
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(FontCache.getTypeface("Clock2017L.ttf", this));
                    if (j == 0) {
                        ((TextView) tabViewChild).setTypeface(((TextView) tabViewChild).getTypeface(), Typeface.BOLD);
                    }
                }
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabLayout1 = (LinearLayout) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayout1.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLayout1 = (LinearLayout) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLayout1.getChildAt(1);
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                deleteImages();
                break;
            case R.id.btn_share:
                shareImages();
                break;
        }
    }

    private void shareImages() {
        shareImagesDialogShow();
    }

    private void deleteImages() {
        deleteImagesDialogShow();
    }

    private void setLayoutAction() {
        onLongClick = true;
        actionBar.setVisibility(View.VISIBLE);
        defaultBar.setVisibility(View.GONE);
    }

    private void setLayoutDefaut() {
        sendBroadcast(new Intent("RESET_ACTION"));
        listSelectPath.clear();
        sizeSelected = 0;
        actionBar.setVisibility(View.GONE);
        defaultBar.setVisibility(View.VISIBLE);
    }

    private void deleteImagesDialogShow() {
        DeleteFileDialog deleteFileDialog = new DeleteFileDialog(this, new DeleteFileDialog.OnButtonClicked() {
            @Override
            public void onDeleteClicked() {
                try {
                    sizeSelected = 0;
                    actionBar.setVisibility(View.GONE);
                    defaultBar.setVisibility(View.VISIBLE);
                    sendBroadcast(new Intent("DELETE_ACTION"));
                    for (String path : listSelectPath) {
                        File file = new File(path);
                        Log.e("MainActivity", path);
                        file.delete();
                    }
                    listSelectPath.clear();
                } catch (Exception e) {
                    Log.e("Loi", e.toString());
                }
            }

            @Override
            public void onCancelClicked() {
                setLayoutDefaut();
            }
        });
        deleteFileDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        deleteFileDialog.show();
    }

    private void shareImagesDialogShow() {
        ArrayList<Uri> arrayUri = new ArrayList<>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("image/*");
        for (String path : listSelectPath) {
            Uri gifUri = Uri.fromFile(new File(path));
            arrayUri.add(gifUri);
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, arrayUri);
        startActivity(Intent.createChooser(shareIntent, "Share Images!"));
        setLayoutDefaut();
    }


    @Override
    public void onBackPressed() {
        if (onLongClick) {
            onLongClick = false;
            setLayoutDefaut();
        } else {
            RateAppDialog rateAppDialog = new RateAppDialog(MainActivity.this, new RateAppDialog.OnButtonClicked() {
                @Override
                public void onRateClicked() {
                    Utils.rateApp(MainActivity.this);
                }

                @Override
                public void onCancelClicked() {
                    finish();
                }
            });
            rateAppDialog.setCancelable(false);
            rateAppDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
