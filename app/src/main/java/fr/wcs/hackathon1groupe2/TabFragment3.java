package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_tab_fragment3, container, false);
        FloatingActionButton floatingActionButton=view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent toViewOtherList=new Intent(getContext(),ViewOtherList.class);
                startActivity(toViewOtherList);
            }
        });
        return view;
    }
}
