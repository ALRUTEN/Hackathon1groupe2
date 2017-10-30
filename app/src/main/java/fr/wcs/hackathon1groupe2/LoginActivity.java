package fr.wcs.hackathon1groupe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    final String userName = "NameKey";
    final String userPassword = "PasswordKey";
    private String mUserId = "UserKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText editTextUserName = findViewById(R.id.connexionUserName);
        final EditText editTextUserPassword = findViewById(R.id.connexionUserPassword);
        Button buttonSend = findViewById(R.id.buttonConnexionSend);

        // On recupere les Shared  Preferences
        final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String sharedPrefUserName = sharedpreferences.getString(userName, "");
        final String sharedPrefUserPassword = sharedpreferences.getString(userPassword, "");
        final ProgressBar simpleProgressBar = findViewById(R.id.simpleProgressBar);

        //On rempli les editText avec les sharedPreferences si c'est pas notre premiere connexion
        if (!sharedPrefUserName.isEmpty() && !sharedPrefUserPassword.isEmpty()) {
            editTextUserName.setText(sharedPrefUserName);
            editTextUserPassword.setText(sharedPrefUserPassword);
        }

        // Au clic du bouton, c'est la que tout se passe !!!!!!!!
        buttonSend.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                simpleProgressBar.setVisibility(View.VISIBLE);

                //On recupere le contenu des edit text
                final String userNameContent = editTextUserName.getText().toString();
                final String userPasswordContent = editTextUserPassword.getText().toString();

                // Toast si les champs ne sont pas remplis
                if (TextUtils.isEmpty(userNameContent) || TextUtils.isEmpty(userPasswordContent)) {
                    Toast.makeText(getApplicationContext(), "REMPLIR LES CHAMPS", Toast.LENGTH_SHORT).show();
                } else {
                    // Sinon on recupere tous les users
                    final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("User");
                    refUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                User userValues = dsp.getValue(User.class);

                                //On compare le contenu des edit text avec Firebase grâce au user_name
                                if (userValues != null && userValues.getUser_name().equals(userNameContent)) {
                                    // On verifie le password
                                    if (userValues.getUser_password().equals(mEncrypt(userPasswordContent))) {

                                        // La clé de l'utilisateur qu'on va utiliser partout dans l'application.
                                        mUserId = dsp.getKey();
                                        // On sauvegarde l'utilisateur connu dans les sharedPreferences
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(userName, userNameContent);
                                        editor.putString(userPassword, userPasswordContent);
                                        editor.putString("mUserId", mUserId);
                                        editor.apply();

                                        // If user is known : if he has no quest => LobbyActivity; if he has => PlayerActivity
                                        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("User");
                                        DatabaseReference db2 = db1.child(mUserId).child("user_quest");
                                        db2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    } else {
                                        // Si le mot de passe ou le pseudo ne concordent pas
                                        Toast.makeText(getApplicationContext(), "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            // Utilisateur nouveau : le compte n'existe pas, on le créer !
                            User user = new User(userNameContent, userPasswordContent);
                            user.setUser_name(userNameContent);
                            user.setUser_password(mEncrypt(userPasswordContent));
                            String userId = refUser.push().getKey();
                            refUser.child(userId).setValue(user);

                            // La clé de l'utilisateur qu'on va utiliser partout dans l'application.
                            mUserId = userId;

                            // On enregistre dans les shared Preferences
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(userName, userNameContent);
                            editor.putString(userPassword, userPasswordContent);
                            editor.putString("mUserId", userId);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        // Encryptage du mot de passe
                        String mEncrypt(String userPassword) {
                            try {
                                Key clef = new SecretKeySpec("AES".getBytes("ISO-8859-2"), "Blowfish");
                                Cipher cipher = Cipher.getInstance("Blowfish");
                                cipher.init(Cipher.ENCRYPT_MODE, clef);
                                return new String(cipher.doFinal(userPassword.getBytes()));
                            } catch (Exception e) {
                                return null;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}

