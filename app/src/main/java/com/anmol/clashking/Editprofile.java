package com.anmol.clashking;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anmol.clashking.databinding.FragmentEditprofileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Editprofile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Editprofile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Editprofile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Editprofile.
     */
    // TODO: Rename and change types and number of parameters
    public static Editprofile newInstance(String param1, String param2) {
        Editprofile fragment = new Editprofile();
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
        requireActivity().getWindow().setStatusBarColor(Color.BLACK);
        requireActivity().getWindow().setStatusBarColor(Color.rgb(255, 255, 255));
        requireActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        FragmentEditprofileBinding binding=FragmentEditprofileBinding.inflate(getLayoutInflater(),container,false);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference(getname()+"/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/name");
        DatabaseReference reference2=database.getReference(getname()+"/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/bio");
        ProgressDialog dialog=new ProgressDialog(requireContext());
        dialog.setCancelable(false);
        dialog.setMessage("Updating...");
        dialog.create();
        binding.update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (TextUtils.isEmpty(binding.name.getText())){
                   Snackbar.make(binding.getRoot(),"Name is Empty", BaseTransientBottomBar.LENGTH_SHORT)
                           .setBackgroundTint(Color.RED).show();
               }else if (TextUtils.isEmpty(binding.bio.getText())){
                   Snackbar.make(binding.getRoot(),"Bio is Empty", BaseTransientBottomBar.LENGTH_SHORT)
                           .setBackgroundTint(Color.RED).show();
               }else{
                   dialog.show();
                   reference.setValue(binding.name.getText().toString());
                   reference2.setValue(binding.bio.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           dialog.dismiss();
                           Snackbar.make(binding.getRoot(),"Profile Successfully updated",BaseTransientBottomBar.LENGTH_SHORT)
                                   .setBackgroundTint(Color.rgb(66, 245, 149)).show();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           dialog.dismiss();
                           Snackbar.make(binding.getRoot(),"Something went wrong",BaseTransientBottomBar.LENGTH_SHORT)
                                   .setBackgroundTint(Color.RED).show();
                       }
                   });
               }
           }
       });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                       String name=snapshot.getValue().toString();
                        binding.name.setText(name);
                    }catch (NullPointerException exception){
                        Log.d("exception",exception.getMessage().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        String bio=snapshot.getValue().toString();
                        binding.bio.setText(bio);
                    }catch (NullPointerException exception){
                        Log.d("exception",exception.getMessage().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
    private native String getname();
}