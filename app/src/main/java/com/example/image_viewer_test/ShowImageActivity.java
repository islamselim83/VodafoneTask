package com.example.image_viewer_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;


public class ShowImageActivity extends AppCompatActivity {
    String show_image;
    ImageView imageView;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imageView = findViewById(R.id.iv_show_image);
        relativeLayout = findViewById(R.id.relative_image_show);
        Intent intent_reciever = getIntent();
        show_image = intent_reciever.getStringExtra("download_url");
        Glide.with(ShowImageActivity.this).load(show_image)
                .into(imageView);
        /*Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            int vibrant = palette.getVibrantColor(0x000000); // <=== color you want
            //int vibrantLight = palette.getLightVibrantColor(0x000000);
            //int vibrantDark = palette.getDarkVibrantColor(0x000000);
            //int muted = palette.getMutedColor(0x000000);
            //int mutedLight = palette.getLightMutedColor(0x000000);
            //int mutedDark = palette.getDarkMutedColor(0x000000);
            relativeLayout.setBackgroundColor(vibrant);
        }); */



    }
}