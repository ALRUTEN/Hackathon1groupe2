package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_tab_fragment1, container, false);
        FloatingActionButton floatingActionButton=view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent toWischActivity=new Intent(getContext(),WischActivity.class);
                startActivity(toWischActivity);
            }
        });
        return view;

        }
    }
