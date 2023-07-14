package com.anmol.clashking;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anmol.clashking.databinding.FragmentMoneyaddBinding;
import com.anmol.clashking.databinding.FragmentMoneywithdrawBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class moneywithdraw extends Fragment {
    FragmentMoneywithdrawBinding binding;
    DatabaseReference reference,account,name,reference2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public moneywithdraw() {
        // Required empty public constructor
    }
    public static moneywithdraw newInstance(String param1, String param2) {
        moneywithdraw fragment = new moneywithdraw();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMoneywithdrawBinding.inflate(getLayoutInflater(),container,false);
        reference= FirebaseDatabase.getInstance().getReference("withdraw/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference2= FirebaseDatabase.getInstance().getReference("kill/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        ProgressDialog dialog=new ProgressDialog(requireContext());
        dialog.setCancelable(false);
        dialog.setMessage("Processing...");
        dialog.create();
        account= FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/amount");
        name=FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/name");
        binding.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                dialog.show();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (snapshot.child("amount").exists()){
                                            dialog.dismiss();
                                            Toast.makeText(requireContext(), "Previous withdraw request pending", Toast.LENGTH_SHORT).show();
                                        }else
                                            account.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                                    ///////////////////
                                                    if (snapshot2.exists()){
                                                            if (!TextUtils.isEmpty(binding.amountbox.getText())&&!TextUtils.isEmpty(binding.upiid.getText())){
                                                                if ((Integer.parseInt(snapshot2.getValue().toString())<Integer.parseInt(binding.amountbox.getText().toString()))) {
                                                                    Toast.makeText(requireContext(), "Insufficient amount", Toast.LENGTH_SHORT).show();
                                                                    dialog.dismiss();
                                                                }else{
                                                                reference.child("amount").setValue(binding.amountbox.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(@NonNull Void unused) {
                                                                        binding.amountboxlayout.setErrorEnabled(false);
                                                                        account.setValue(String.valueOf(Integer.parseInt(snapshot2.getValue().toString())-Integer.parseInt(binding.amountbox.getText().toString()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(@NonNull Void unused) {
                                                                                dialog.dismiss();
                                                                                reference.child("amount").setValue(binding.amountbox.getText().toString());
                                                                                name.addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                        if (snapshot.exists()){
                                                                                            reference.child("name").setValue(snapshot.getValue().toString());
                                                                                        }else{
                                                                                            reference.child("name").setValue("D乛Ark");
                                                                                        }

                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                    }
                                                                                });
                                                                                reference.child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                                                                reference.child("upi").setValue(binding.upiid.getText().toString());
                                                                                Toast.makeText(requireContext(), "Withdraw request submitted", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                dialog.dismiss();
                                                                                Toast.makeText(requireContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }
                                                            }else{
                                                                dialog.dismiss();
                                                                binding.amountboxlayout.setError(".");
                                                        }
                                                    }else{
                                                        Toast.makeText(requireContext(), "You haven't added any cash", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                   ///////////////
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                    }
                                },3000);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                 dialog.dismiss();
                            }
                        });
            }
        });
        return binding.getRoot();
    }
}