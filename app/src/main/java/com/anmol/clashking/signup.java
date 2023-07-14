package com.anmol.clashking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.anmol.clashking.databinding.FragmentSignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signup() {
        // Required empty public constructor
    }
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(requireActivity(),login.class));
                requireActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignupBinding binding=FragmentSignupBinding.inflate(getLayoutInflater()
        ,container,false);
        validation Validate=new validation();
        ProgressDialog dialog=new ProgressDialog(requireActivity());
        dialog.setCancelable(false);
        dialog.setMessage(dialogmsg());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (Validate.validate(binding)==Validate.Validatephone()){
                   Snackbar(binding.getRoot(),phone());
               }else if (Validate.validate(binding)==Validate.ValidateEmail()){
                   Snackbar(binding.getRoot(),Email());
               }else if ((binding.editTextPhone.getText().length()<10)){
                   Snackbar(binding.getRoot(),"Enter valid phone number");
               }else if (Validate.validate(binding)==Validate.Validatepass()){
                   Snackbar(binding.getRoot(),pass());
               }else {
                   dialog.show();
                   FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.EmailAddress.getText().toString()
                   ,binding.Password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           dialog.dismiss();
                           Snackbar(binding.getRoot(),e.getMessage());
                       }
                   }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           FirebaseDatabase database=FirebaseDatabase.getInstance();
                           DatabaseReference reference=database.getReference(DBref()+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                           reference.child(child()).setValue(binding.editTextPhone.getText().toString());
                           reference.child(child2()).setValue(binding.EmailAddress.getText().toString());
                           reference.child(child3()).setValue(binding.Password.getText().toString());
                           reference.child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                           dialog.dismiss();
                            Snackbar.make(binding.getRoot(),signupresponse(),BaseTransientBottomBar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.GREEN).show();
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(requireActivity(),Home.class));
                                    requireActivity().finish();
                                }
                            },3000);
                       }
                   });
               }
            }
        });
        return binding.getRoot();
    }
    public void Snackbar(View view,String msg){
        Snackbar.make(view,msg, BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(Color.RED).show();
    }
    private native String Email();
    private native String pass();
    private native String phone();
    private native String dialogmsg();
    private native String signupresponse();
    private native String DBref();
    private native String child();
    private native String child2();
    private native String child3();
}