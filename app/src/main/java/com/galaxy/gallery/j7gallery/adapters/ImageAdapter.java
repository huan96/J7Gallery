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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemImage> {
    private ArrayList<Image> imageList;
    private Context mContext;
    private OnClickImage onClickImage;
    private boolean isLongClick;

    public ImageAdapter(ArrayList<Image> imageList, Context mContext, OnClickImage onClickImage) {
        this.imageList = imageList;
        this.mContext = mContext;
        this.onClickImage = onClickImage;
    }

    @Override
    public ItemImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        return new ItemImage(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemImage holder, final int position) {
        Glide.with(mContext).load(new File(imageList.get(position).getPath())).into(holder.thumbnail);
        if (imageList.get(position).isClicked()) {
            isLongClick = true;
            holder.isChecked.setVisibility(View.VISIBLE);
        } else {
            holder.isChecked.setVisibility(View.GONE);
        }
        if (imageList.get(position).isAdded()) {
            holder.isChecked.setChecked(true);
        }
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLongClick) {
                    if (holder.isChecked.isChecked()) {
                        holder.isChecked.setChecked(false);
                    } else {
                        holder.isChecked.setChecked(true);
                    }
                } else {
                    onClickImage.onClickImage(imageList.get(position));
                }
            }
        });

        holder.isChecked.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (!isChecked) {
                    onClickImage.removeItemImage(imageList.get(position));
                } else {
                    onClickImage.addItemImage(imageList.get(position));
                }
            }
        });

        holder.thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Image image = imageList.get(position);
                onClickImage.onLongClickImage(image);
                onClickImage.addItemImage(image);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageList.size();
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

    public interface OnClickImage {

        void onClickImage(Image image);

        void onLongClickImage(Image image);

        void addItemImage(Image image);

        void removeItemImage(Image image);
    }
}
