package com.chat.saketmayank.chatapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MyGallery extends AppCompatActivity {
    private RecyclerView imageRecycler;
    private final List<Messages> messagesList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private GridLayoutManager gridLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    private String mCurrentUserId;
    private String mChatUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gallery);
        imageRecycler = findViewById(R.id.gallery_view);
        int mNoOfColumns = calculateNoOfColumns(getApplicationContext());
        imageAdapter = new ImageAdapter(messagesList,this);
        gridLayoutManager = new GridLayoutManager(this,mNoOfColumns);
        imageRecycler.setHasFixedSize(true);
        imageRecycler.setLayoutManager(gridLayoutManager);
        int spanCount = 3; // 3 columns
        int spacing = 10; // 50px
        boolean includeEdge = true;
        imageRecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        imageRecycler.setAdapter(imageAdapter);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mChatUser = getIntent().getStringExtra("user_id");
        String userName = getIntent().getStringExtra("user_name");

        loadImages();
    }

        public  int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 120);
            return noOfColumns;
        }


    private void loadImages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser);
        Query query = messageRef;
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages message = dataSnapshot.getValue(Messages.class);
                if(message.getType().equals("image")){
                messagesList.add(message);
                imageAdapter.notifyDataSetChanged();}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
