package com.anmol.clashking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.PictureInPictureParams;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anmol.clashking.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private validation validation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(Color.rgb(38,50,56));
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(binding.getRoot());
        validation=new validation();
        ProgressDialog dialog=new ProgressDialog(login.this);
        dialog.setCancelable(dialogshow());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage(dialogmsg());
                if(validation.validate(binding)==validation.ValidateEmail()){
                    Snackbar(binding.getRoot(),Email());
                }else if (validation.validate(binding)==validation.Validatepass()){
                    Snackbar(binding.getRoot(),pass());
                }else{
                    dialog.show();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.Email.getText().toString()
                    ,binding.Password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            dialog.dismiss();
                            Snackbar.make(binding.getRoot(),loginmsg(),BaseTransientBottomBar.LENGTH_LONG)
                                    .setBackgroundTint(Color.GREEN).show();
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  startActivity(new Intent(login.this,Home.class));
                                  finish();
                                }
                            },2000);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                          Snackbar(binding.getRoot(),e.getMessage());
                        }
                    });
                }
            }
        });
        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardView2.setVisibility(View.GONE);
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.main,new signup()).addToBackStack(null).commit();
            }
        });
        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation.validate(binding)!=validation.ValidateEmail()) {
                    dialog.setMessage(Resetdialogmsg());
                    dialog.show();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(binding.Email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                      Snackbar.make(binding.getRoot(),Resetmsg(),BaseTransientBottomBar.LENGTH_SHORT)
                              .setBackgroundTint(Color.GREEN).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Snackbar(binding.getRoot(), e.getMessage());
                        }
                    });
                }else{
                    Snackbar(binding.getRoot(),Email());
                }
            }
        });
    }
    private void Snackbar(View view,String s){
        Snackbar.make(view,s, BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(Color.RED).show();
    }
    private native String Email();
    private native String pass();
    private native boolean dialogshow();
    private native String dialogmsg();
    private  native String loginmsg();
    private native String Resetmsg();
    private native String Resetdialogmsg();
}