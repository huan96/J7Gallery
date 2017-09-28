package com.galaxy.gallery.j7gallery.flagments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.galaxy.gallery.j7gallery.activities.ShowActivity;
import com.galaxy.gallery.j7gallery.adapters.AlbumTimeAdapter;
import com.galaxy.gallery.j7gallery.adapters.ImageAdapter;
import com.galaxy.gallery.j7gallery.objects.AlbumTime;
import com.galaxy.gallery.j7gallery.objects.Image;
import com.galaxy.gallery.j7gallery.until.ManagerGalary;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by huand on 9/21/2017.
 */

public class AlbumTimeFragment extends Fragment {
    private RecyclerView recycleView;
    private AlbumTimeAdapter albumTimeAdapter;
    private ArrayList<AlbumTime> arrAlbum;
    private ArrayList<Image> arrImage;
    private ManagerGalary managerGalary;
    private ArrayList<Image> arrImageSelected;
    private ArrayList<String> arrTimeAlbum;
    private Context mContext;
    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
        managerGalary = new ManagerGalary(mContext);
        arrImage = new ArrayList<>();
        arrAlbum = new ArrayList<>();
        arrImageSelected = new ArrayList<>();
        arrImage = managerGalary.getArrImage();
        arrTimeAlbum = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        createAllAlbums();
        recycleView = (RecyclerView) rootView.findViewById(R.id.rcv_fr_image);
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 1));
        setAdapter();
    }

    private void setAdapter() {
        albumTimeAdapter = new AlbumTimeAdapter(mContext, arrAlbum, new ImageAdapter.OnClickImage() {
            @Override
            public void onClickImage(Image image) {
                ArrayList<String> pathList = new ArrayList<>();
                for (Image img : arrImage) {
                    pathList.add(img.getPath());
                }
                Intent intent = new Intent(mContext, ShowActivity.class);
                intent.putStringArrayListExtra("ALL_PATH", pathList);
                intent.putExtra("POSITION", arrImage.indexOf(image));
                mContext.startActivity(intent);
            }

            @Override
            public void onLongClickImage(Image image) {
                arrImage.get(arrImage.indexOf(image)).setAdded(true);
                changeStateToSelect();
                mContext.sendBroadcast(new Intent("ON_LONG_CLICK"));
            }

            @Override
            public void addItemImage(Image image) {
                if (!arrImageSelected.contains(image)) {
                    arrImageSelected.add(image);
                    arrImage.get(arrImage.indexOf(image)).setAdded(true);
                    Intent intent = new Intent("ADD_ITEM");
                    intent.putExtra("PATH", image.getPath());
                    mContext.sendBroadcast(intent);
                }
                Log.e("AlbumTimeFragment.size", String.valueOf(arrImageSelected.size()));
            }

            @Override
            public void removeItemImage(Image image) {
                if (arrImageSelected.contains(image)) {
                    arrImageSelected.remove(image);
                    arrImage.get(arrImage.indexOf(image)).setAdded(false);
                    Intent intent = new Intent("REMOVE_ITEM");
                    intent.putExtra("PATH", image.getPath());
                    mContext.sendBroadcast(intent);
                }
                Log.e("AlbumTimeFragment.size", String.valueOf(arrImageSelected.size()));
            }
        });
        recycleView.setAdapter(albumTimeAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction("RESET_ACTION");
        filter.addAction("DELETE_ACTION");
        filter.addAction("ADD_ALL");
        filter.addAction("REMOVE_ALL_SELECT");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "RESET_ACTION":
                        changeStateToDedaut();
                        break;
                    case "DELETE_ACTION":
                        changeStateToDedautAfterDeleted();
                        break;
                    case "ADD_ALL":
                        addAllImage();
                        break;
                    case "REMOVE_ALL_SELECT":
                        removeAllImagesSelected();
                        break;
                    default:
                        break;
                }
            }
        };
        mContext.registerReceiver(receiver, filter);
    }

    private void removeAllImagesSelected() {
        List<String> pathList = new ArrayList<>();
        for (Image image : arrImage) {
            image.setAdded(false);
            pathList.add(image.getPath());
        }
        createAllAlbums();
        albumTimeAdapter.notifyDataSetChanged();
        Intent intent = new Intent("REMOVE_SELECT_ALL_IMAGE");
        mContext.sendBroadcast(intent);
    }

    private void addAllImage() {
        ArrayList<String> pathList = new ArrayList<>();
        for (Image image : arrImage) {
            image.setAdded(true);
            pathList.add(image.getPath());
        }
        createAllAlbums();
        albumTimeAdapter.notifyDataSetChanged();
        Intent intent = new Intent("SELECT_ALL_IMAGE");
        intent.putStringArrayListExtra("ALL_IMAGES", pathList);
        mContext.sendBroadcast(intent);
    }

    private void changeStateToDedautAfterDeleted() {
        arrImage.clear();
        managerGalary = new ManagerGalary(mContext);
        arrImage = managerGalary.getArrImage();
        createAllAlbums();
        arrImageSelected.clear();
        albumTimeAdapter.notifyDataSetChanged();
    }

    private void changeStateToSelect() {
        for (Image image : arrImage) {
            image.setClicked(true);
        }
        createAllAlbums();
        albumTimeAdapter.notifyDataSetChanged();
    }

    private void changeStateToDedaut() {
        for (Image image : arrImage) {
            image.setClicked(false);
            image.setAdded(false);
        }
        createAllAlbums();
        arrImageSelected.clear();
        albumTimeAdapter.notifyDataSetChanged();
    }

    private void createAllAlbums() {
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
        sortList();
    }

    public void getTime(String time, List<String> arrTime) {
        boolean isAdd = false;
       /* for (int i = 0; i < arrTime.size(); i++) {
            if (time.equals(arrTime.get(i))) {
                isAdd = true;
                return;
            }
        }*/
        if (arrTime.contains(time))
            isAdd = true;
        if (!isAdd) {
            arrTimeAlbum.add(time);
        }
    }

    @Override
    public void onDestroy() {
        mContext.unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void sortList() {
        Comparator<AlbumTime> c = new Comparator<AlbumTime>() {

            @Override
            public int compare(AlbumTime o1, AlbumTime o2) {
                Date date1 = stringToDate(o1.getTimeTitle(),
                        "dd/MM/yyyy");
                Date date2 = stringToDate(o2.getTimeTitle(),
                        "dd/MM/yyyy");
                return date1.compareTo(date2) * -1;
            }
        };
        Collections.sort(arrAlbum, c);
    }

    private Date stringToDate(String aDate, String aFormat) {
        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }
}
