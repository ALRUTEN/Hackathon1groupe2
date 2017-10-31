package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WischActivity extends AppCompatActivity {

    ImageView appPhoto;
    ImageView galleriePhoto;

    String mCurrentPhotoPath;
    Uri mCurrentPhotoUri;
    private static final int TAKE_IMAGE_REQUEST = 4;
    private static final int PICK_IMAGE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisch);
        appPhoto = findViewById(R.id.imgAppPhoto);
        galleriePhoto = findViewById(R.id.imgGallerieTofs);
         final ImageView imgCadeau = findViewById(R.id.imageView);

        appPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              dispatchTakePictureIntent();

            }
        });

        galleriePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), PICK_IMAGE_REQUEST);
            }
        });




        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Button boutonCreer =findViewById(R.id.buttonCreer);
        boutonCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Gift gift=new Gift(titre,description);
                DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User");
                refUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            User userValues = dsp.getValue(User.class);
                            if (userValues != null && userValues.getUser_name().equals(sharedPreferences.getString("NameKey",""))) {
                                userValues.addListGift(gift);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
// Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
// Error occurred while creating the File
                ;
            }
// Continue only if the File was successfully created
            if (photoFile != null) {
                mCurrentPhotoUri = FileProvider.getUriForFile(this,
                        "fr.wcs.hackathon1groupe2",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                startActivityForResult(takePictureIntent, TAKE_IMAGE_REQUEST);
            }
        }
    }


    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

// Save a file: path for use with ACTION_VIEW intents

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



}
