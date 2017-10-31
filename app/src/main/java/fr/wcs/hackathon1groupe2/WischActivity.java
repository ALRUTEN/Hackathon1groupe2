package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WischActivity extends AppCompatActivity {

    final String userName = "NameKey";
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisch);
        List<Gift> mGiftList = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(getApplicationContext(), mGiftList);
        final EditText textTitre=findViewById(R.id.textTitre);
        final EditText textDescription=findViewById(R.id.textDescription);
        Button buttonAnnuler=findViewById(R.id.annuler);
        Button buttonCreer=findViewById(R.id.buttonCreer);
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent toMainActivity=new Intent(WischActivity.this,MainActivity.class);
                startActivity(toMainActivity);
            }
        });
        buttonCreer.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User");
                refUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String sharedPrefUserName = sharedpreferences.getString(userName, "");
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            User userValues = dsp.getValue(User.class);
                            if (userValues != null && userValues.getUser_name().equals(sharedPrefUserName)) {
                                String titre=textTitre.getText().toString();
                                String description=textDescription.getText().toString();
                                Gift newGift=new Gift(titre,description);
                                userValues.addListGift(newGift);

                                String userId = refUser.push().getKey();
                                refUser.child(userId).setValue(userValues);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {}
                });
                Intent toMainActivity=new Intent(WischActivity.this,MainActivity.class);
                startActivity(toMainActivity);
            }
        });
    }
}
