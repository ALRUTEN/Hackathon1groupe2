package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabFragment2 extends Fragment {

    private List<Gift> mGiftList;
    private RecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_tab_fragment2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        mGiftList = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(view.getContext(),mGiftList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ViewOtherList.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareGiftData();

        return view;
    }

    private void prepareGiftData() {
        //todo modifier les gifts affichés
        int[] covers = new int[]{
                /*
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3
                */
        };

        Gift gift = new Gift("Mad Max: Fury Road", "Action & Adventure");
        mGiftList.add(gift);

        gift = new Gift("Inside Out", "Animation, Kids & Family");
        mGiftList.add(gift);

        gift = new Gift("Inside Out", "Animation, Kids & Family");
        mGiftList.add(gift);

        gift = new Gift("Inside Out", "Animation, Kids & Family");
        mGiftList.add(gift);

        gift = new Gift("Inside Out", "Animation, Kids & Family");
        mGiftList.add(gift);

        mAdapter.notifyDataSetChanged();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}