package com.anmol.clashking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anmol.clashking.databinding.ActivityHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends AppCompatActivity{
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(255,255,255));
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.Bnav.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.homelayout,new match())
                .addToBackStack(null).commit();
        binding.Bnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               /* if (item.getItemId()==R.id.youtube){
                  // getSupportFragmentManager().beginTransaction().replace(R.id.homelayout,new youtube())
                          // .addToBackStack(null).commit();
                  //  hidenavigation();

                }else*/ if (item.getItemId()==R.id.Lederboard){
                    getSupportFragmentManager().beginTransaction().replace(R.id.homelayout,new leaderboard())
                            .addToBackStack(null).commit();
                   hidenavigation();
                }else if (item.getItemId()==R.id.Account){
                    getSupportFragmentManager().beginTransaction().replace(R.id.homelayout,new user())
                            .addToBackStack(null).commit();
                    hidenavigation();
                }else{
                    hidenavigation();
                    getSupportFragmentManager().beginTransaction().replace(R.id.homelayout,new match())
                            .addToBackStack(null).commit();

                }
                return true;
            }
        });
    }



   /* @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(this);
        dialog.setCancelable(false);
        dialog.setMessage(Html.fromHtml(dialogmsg()));
        dialog.setPositiveButton(Html.fromHtml("<font color='#FF000000'>yes</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // super.onBackPressed();
                System.exit(0);
            }
        }).setNegativeButton(Html.fromHtml("<font color='#FF000000'>No</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.create();
     //  dialog.show();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void hidenavigation(){
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    public native String dbmatchref();
    private native String dialogmsg();
}