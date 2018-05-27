package com.chat.saketmayank.chatapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.service.autofill.ImageTransformation;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context context;
    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;


    public ImageAdapter(List<Messages> mMessageList,Context context) {
        this.mMessageList = mMessageList;
        this.context=context;
    }


    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_single_layout ,parent, false);

        return new ImageViewHolder(v);
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView messageImage;
        public ImageViewHolder(View itemView) {
            super(itemView);
            messageImage = (ImageView) itemView.findViewById(R.id.message_image);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder viewHolder, int i) {
        Messages c = mMessageList.get(i);
        String from_user = c.getFrom();
        String message_type = c.getType();
        if (message_type.equals("image")) {
            Picasso.with(context).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar)
                    .transform(getTransformation(viewHolder.messageImage))

                    .into(viewHolder.messageImage);
        }
        else {
            viewHolder.messageImage.setVisibility(View.GONE);
        }
//        viewHolder.messageImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
    public static Transformation getTransformation(final ImageView imageView) {
        return new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = 400;
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
