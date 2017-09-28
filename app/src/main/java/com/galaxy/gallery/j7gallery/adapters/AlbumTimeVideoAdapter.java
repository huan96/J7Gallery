package com.galaxy.gallery.j7gallery.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.objects.AlbumTime;
import com.galaxy.gallery.j7gallery.until.SmoothCheckBox;

import java.util.ArrayList;

/**
 * Created by huand on 9/21/2017.
 */

public class AlbumTimeVideoAdapter extends RecyclerView.Adapter<AlbumTimeVideoAdapter.ItemAlbum> {
    private Context mContext;
    private ArrayList<AlbumTime> albumList;
    private boolean isOnlongClick;

    public AlbumTimeVideoAdapter(Context mContext, ArrayList<AlbumTime> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        isOnlongClick = false;
    }

    @Override
    public ItemAlbum onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_abum_time, parent, false);
        return new ItemAlbum(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemAlbum holder, final int position) {
        holder.title.setText(albumList.get(position).getTimeTitle());
        VideoAdapter videoAdapter = new VideoAdapter(albumList.get(position).getArrImage(), mContext, new VideoAdapter.OnClickImage() {
            @Override
            public void onClickImage(int position) {

            }
        });
        holder.rvImage.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rvImage.setAdapter(videoAdapter);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ItemAlbum extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView rvImage;
        SmoothCheckBox checkBox;

        public ItemAlbum(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            checkBox = (SmoothCheckBox) itemView.findViewById(R.id.check_all_image_time);
            rvImage = (RecyclerView) itemView.findViewById(R.id.rcv_item_pictures);
        }
    }

}
