package com.gama.mutamer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.interfaces.IGalleryClickListener;
import com.gama.mutamer.viewModels.utils.ImageItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 8/24/16.
 * Release the GEEK
 */
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportViewHolder> {

    /***
     * Vars
     */
    private ArrayList<ImageItem> mImages;
    private IGalleryClickListener mListener;
    private int layout;

    public ReportsAdapter(ArrayList<ImageItem> images, IGalleryClickListener listener, int layout) {
        this.mImages = images;
        this.mListener = listener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        try {
            ImageItem image = mImages.get(position);
            if (image != null) {
                holder.ivGalleryType.setImageResource(image.isVideo() ? R.drawable.ic_gallery_video : R.drawable.ic_gallery_image);
                holder.ivReport.setImageBitmap(image.getBitmap());
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        /***
         * Vars
         */
        @BindView(R.id.ivReport) ImageView ivReport;
        @BindView(R.id.ivGalleryType) ImageView ivGalleryType;
        @BindView(R.id.ivDeleteIcon) ImageView ivDeleteIcon;

        ReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            ivDeleteIcon.setOnClickListener(v -> {
                if (mListener != null) {
                    ImageItem item = mImages.get(getAdapterPosition());
                    mListener.deleteIconClicked(item.getFilePath());
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                ImageItem item = mImages.get(getAdapterPosition());
                mListener.itemClicked(item.isVideo(), item.getFilePath());
            }

        }




    }
}