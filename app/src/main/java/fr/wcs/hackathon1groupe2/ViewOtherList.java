package fr.wcs.hackathon1groupe2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewOtherList extends AppCompatActivity {
    private List<Gift> giftList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new RecyclerViewAdapter(ViewOtherList.this,giftList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareGiftData();
    }

    private void prepareGiftData() {
        Gift gift = new Gift("Mad Max: Fury Road", "Action & Adventure");
        giftList.add(gift);

        gift = new Gift("Inside Out", "Animation, Kids & Family");
        giftList.add(gift);

        mAdapter.notifyDataSetChanged();
    }
}
