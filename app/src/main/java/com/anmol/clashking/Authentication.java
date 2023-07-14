package com.anmol.clashking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anmol.clashking.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authentication extends AppCompatActivity {
    public int checkauth(FirebaseAuth auth){
        if (auth.getCurrentUser()!=null){
            return Validator();
        }else{
            return Validator2();
        }
    }
    public void checkforupdate(DatabaseReference reference, Dialog dialog){
        dialog.setContentView(R.layout.updatedialog);
        dialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(dialogshow());
        TextView about= dialog.findViewById(R.id.textView7);
        about.setText(aboutupdate());
        TextView aboutupdate=dialog.findViewById(R.id.textView2);
         TextView version0 = dialog.findViewById(R.id.version);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String versioncode = snapshot.child(parm1()).getValue().toString();
                String version = snapshot.child(parm2()).getValue().toString();
                String about = snapshot.child(parm4()).getValue().toString();
                if (Integer.parseInt(versioncode) > BuildConfig.VERSION_CODE) {
                   version0.setText(version);
                   aboutupdate.setText(about);
                   dialog.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public native int Validator();
    public native int Validator2();
    private native boolean dialogshow();
    private native String parm1();
    private native String parm2();
    public native String parm3();
    private native String parm4();
    private native String aboutupdate();
    public native String whennotupdate();
}
