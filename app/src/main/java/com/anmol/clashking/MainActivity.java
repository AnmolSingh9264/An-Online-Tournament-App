package com.anmol.clashking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anmol.clashking.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("clashking");
    }
    private ActivityMainBinding binding;
    private Authentication authentication;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(21, 20, 20));
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        authentication=new Authentication();
        setContentView(binding.getRoot());
        OneSignal.initWithContext(this);
        OneSignal.setAppId("5ba42248-3455-4424-8d8a-e76cd032471a");
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        reference=database.getReference(databaseref());
        Dialog dialog=new Dialog(this);
        authentication.checkforupdate(reference, dialog);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!dialog.isShowing()){
                         if (authentication.checkauth(FirebaseAuth.getInstance())==authentication.Validator()){
                    startActivity(new Intent(MainActivity.this,Home.class));
                   finish();
                }else{
                    startActivity(new Intent(MainActivity.this,login.class));
                    finish();
                }
                }
            }
        },5000);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String  url= snapshot.child(authentication.parm3()).getValue().toString();
                Button update=dialog.findViewById(R.id.button2);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         launchweb(url);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void launchweb(String url){
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
private native String databaseref();
}