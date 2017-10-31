package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {

    final String userName = "NameKey";

    private List<Gift> mGiftList;
    private RecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_tab_fragment1, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        mGiftList = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(view.getContext(),mGiftList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ViewOtherList.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareGiftData();

        FloatingActionButton floatingActionButton=view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent toWischActivity=new Intent(getContext(),WischActivity.class);
                startActivity(toWischActivity);
            }
        });
        return view;
    }
    private void prepareGiftData() {
        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User");
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final String sharedPrefUserName = sharedpreferences.getString(userName, "");
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    User userValues = dsp.getValue(User.class);
                    if (userValues != null && userValues.getUser_name().equals(sharedPrefUserName)) {
                        for(Gift gift:userValues.getListGift()){
                            if (!gift.getGived()) {
                                mGiftList.add(gift);
                            }
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
