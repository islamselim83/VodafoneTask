package com.example.image_viewer_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //initialize variables
    Activity activity;
    ArrayList<MainData> dataArrayList;

    //Create constructor

    public MainAdapter(Activity activity, ArrayList<MainData> dataArrayList) {
        this.activity = activity;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //initialize data
        MainData data = dataArrayList.get(position);
        //initialize shimmer
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder().setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();
        //initialize shimmer Drawable
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        // Set Image on Image View
        Glide.with(activity).load(data.getImage())
                .placeholder(shimmerDrawable)
                .into(holder.ivImage);
        //setup Image Click
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_show_image=new Intent(v.getContext(),ShowImageActivity.class);
                intent_show_image.putExtra("download_url",data.getImage());
                v.getContext().startActivity(intent_show_image);
            }
        });
        // Set name and url on TextView
        holder.tvName.setText(data.getName());
        holder.tvUrl.setText(data.getUrl());
        holder.iv_ads.setVisibility(View.GONE);

        // setup vodafone ads placeholder
        if((position+1)%5 ==0){
                holder.iv_ads.setVisibility(View.VISIBLE);
                holder.iv_ads.setImageResource(R.drawable.vodafone_ads_picture);
        }

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize variables
        ImageView ivImage;
        TextView tvName,tvUrl;
        ImageView iv_ads;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUrl = itemView.findViewById(R.id.tv_url);
            iv_ads = itemView.findViewById(R.id.ads_placeholder);
        }
    }
}