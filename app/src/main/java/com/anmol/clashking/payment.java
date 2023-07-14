package com.anmol.clashking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class payment extends AppCompatActivity implements PaymentResultListener {
    Intent intent;
    ProgressDialog dialog;
    Checkout checkout;
    DataSnapshot snapshot2;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+getkey());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(55,255,255));
        setContentView(R.layout.activity_payment);
        intent=getIntent();
         checkout= new Checkout();
        checkout.setKeyID(getkeyid());
        checkout.setImage(R.mipmap.ic_launcher);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Processing...");
        Button enter=findViewById(R.id.enter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    dialog.dismiss();
                    snapshot2=snapshot;
                }else{
                    snapshot2=null;
                    dialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        startPayment(String.valueOf(getint()*Integer.parseInt(intent.getStringExtra(getkey()))));
    }
    public void startPayment(String  amount) {
        final Activity activity = this;
        try {
            dialog.show();
            JSONObject options = new JSONObject();
            options.put("name", "D乛Ark");
            options.put("description", "Payment for joining PUBG tournament");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", false);
            options.put("currency", "INR");
            options.put("amount", amount);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "email");
            preFill.put("contact", "phone");
            checkout.open(payment.this, options);
        } catch (Exception e) {
            dialog.dismiss();
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
      ftransaciton();
    }
    private void ftransaciton(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(payment.this,Home.class));
                finish();
            }
        },2000);
    }
    @Override
    public void onPaymentSuccess(String s) {
        dialog.dismiss();
        if (snapshot2.getValue()!=null){
            reference.setValue(String.valueOf(Integer.parseInt(snapshot2.getValue().toString())+Integer.parseInt(intent.getStringExtra(getkey()))));
        }else{
            reference.setValue(intent.getStringExtra(getkey()));
        }
        Toast.makeText(payment.this, "Amount add successfully", Toast.LENGTH_SHORT).show();
        ftransaciton();
    }
    @Override
    public void onPaymentError(int i, String s) {
        dialog.dismiss();
        Toast.makeText(payment.this, s, Toast.LENGTH_SHORT).show();
        ftransaciton();
    }
    private native String  getkeyid();
    private native String getkey();
    private native int getint();
}