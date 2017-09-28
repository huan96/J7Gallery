package com.galaxy.gallery.j7gallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galaxy.gallery.j7gallery.R;
import com.galaxy.gallery.j7gallery.objects.Image;
import com.galaxy.gallery.j7gallery.until.SmoothCheckBox;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Peih Gnaoh on 8/20/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ItemImage> {
    private ArrayList<Image> imageList;
    private Context mContext;
    private OnClickImage onClickImage;

    public VideoAdapter(ArrayList<Image> imageList, Context mContext, OnClickImage onClickImage) {
        this.imageList = imageList;
        this.mContext = mContext;
        this.onClickImage = onClickImage;
    }

    @Override
    public ItemImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new ItemImage(itemView);
    }

    @Override
    public void onBindViewHolder(ItemImage holder, final int position) {
        Glide.with(mContext).load(new File(imageList.get(position).getPath())).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage.onClickImage(position);
            }
        });
        if (imageList.get(position).isClicked()) {
            holder.isChecked.setVisibility(View.VISIBLE);
        } else {
            holder.isChecked.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnClickImage {
        void onClickImage(int position);
    }

    public class ItemImage extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        SmoothCheckBox isChecked;

        public ItemImage(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            isChecked = (SmoothCheckBox) itemView.findViewById(R.id.check_item);
        }
    }
}
