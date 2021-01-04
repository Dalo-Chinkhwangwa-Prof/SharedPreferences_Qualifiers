package com.fishco.mythirdapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fishco.mythirdapplication.R;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.fishco.mythirdapplication.util.Constants.COUNT_KEY;

public class MainActivity extends AppCompatActivity {

    /*
    *
    * SharedPreferences
    *
    * Database
    *
    * Internal Storage (in app storage)
    *
    * External Storage (shared directory)
    *
    * */

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesEnc;

    private String alias = "";

    private int count = 0;

    public static final String TAG = "TAG_X";
    private TextView clickMe;
    private Button countButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getPackageName());
        //Regular sharedPreferences
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        //EncryptedSharedPreferences
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

                sharedPreferencesEnc = EncryptedSharedPreferences.create(
                    getPackageName()+".part_two",
                    alias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

            }


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        clickMe = findViewById(R.id.click_me);
        countButton = findViewById(R.id.click_button);

        //Read from sharedPreferences
        count = sharedPreferences.getInt(COUNT_KEY, count);

        updateCount();

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;

                //Write to SharedPreferences
                sharedPreferences
                        .edit()
                        .putInt(COUNT_KEY, count)
                        .apply();

                sharedPreferencesEnc
                        .edit()
                        .putInt(COUNT_KEY, count)
                        .apply();

                updateCount();
            }
        });

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity2.class));
            }
        });

        Log.d(TAG, "Activity1: onCreate()");
    }

    private void updateCount() {
        clickMe.setText("Count : " + count);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity1: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity1: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity1: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity1: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Activity1: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity1: onDestroy()");
    }
}