package com.galaxy.gallery.j7gallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.objects.Album;
import com.galaxy.gallery.j7gallery.until.SmoothCheckBox;

import java.io.File;
import java.util.ArrayList;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ItemAlbum> {
    private Context mContext;
    private ArrayList<Album> albumList;
    private OnClickAlbum onClickAlbum;
    private boolean isOnlongClick;

    public AlbumAdapter(Context mContext, ArrayList<Album> albumList, OnClickAlbum onClickAlbum) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.onClickAlbum = onClickAlbum;
        isOnlongClick = false;
    }

    @Override
    public ItemAlbum onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);
        return new ItemAlbum(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemAlbum holder, final int position) {
        holder.title.setText(albumList.get(position).getBucket());
        holder.size.setText(albumList.get(position).getArrImage().size() + "");
        Glide.with(mContext).load(new File(albumList.get(position).getArrImage().get(0).getPath())).into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnlongClick) {
                    onClickAlbum.onClickAlbum(position);
                } else {
                    onClickAlbum.onAddAlbumSelect(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isOnlongClick = true;
                onClickAlbum.onLongClickAlbum(position);
                return true;
            }
        });
        if (isOnlongClick) {
            holder.checkBox.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft).duration(500).playOn(holder.checkBox);
        }
        holder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                Log.d("SmoothCheckBox", String.valueOf(isChecked));
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ItemAlbum extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, size;
        SmoothCheckBox checkBox;

        public ItemAlbum(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            size = (TextView) itemView.findViewById(R.id.count);
            checkBox = (SmoothCheckBox) itemView.findViewById(R.id.check_item);
        }
    }

    public interface OnClickAlbum {
        void onClickAlbum(int position);

        void onLongClickAlbum(int position);

        void onAddAlbumSelect(int position);

        void onRemoteAlbumSelect(int position);
    }

}
