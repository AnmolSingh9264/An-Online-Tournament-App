package com.anmol.clashking;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.anmol.clashking.databinding.FragmentDeveloperBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link developer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class developer extends Fragment {
    FragmentDeveloperBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public developer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment developer.
     */
    // TODO: Rename and change types and number of parameters
    public static developer newInstance(String param1, String param2) {
        developer fragment = new developer();
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
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homelayout,new user()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding=FragmentDeveloperBinding.inflate(getLayoutInflater(),container,false);
        requireActivity().getWindow().setStatusBarColor(Color.rgb(255, 255, 255));
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog=new ProgressDialog(requireActivity());
                dialog.setCancelable(false);
                dialog.setMessage("Sending...");
                dialog.create();
                if (TextUtils.isEmpty(binding.name.getText())){
                    snackbar("Kindly Enter your name!");
                }else if (TextUtils.isEmpty(binding.appCompatEditText.getText())){
                    snackbar("Kindly Enter message to send!");
                } else{
                    dialog.show();
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference("msg/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child("Name")
                            .setValue(binding.name.getText().toString());
                    reference.child("msg")
                            .setValue(binding.appCompatEditText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Snackbar.make(binding.getRoot(),"Message Submitted!", BaseTransientBottomBar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.rgb(66, 245, 149)).show();
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.homelayout,new user()).addToBackStack(null).commit();
                            requireActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            requireActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            Snackbar.make(binding.getRoot(),e.getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.RED).show();
                        }
                    });
                }
            }
        });
        binding.insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getinsta());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getinsta())));
                }
            }
        });
        binding.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getyoutube());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    //startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                  //  startActivity(new Intent(Intent.ACTION_VIEW,
                        //    Uri.parse(getyoutube())));
                }
            }
        });
        return binding.getRoot();
    }
    public void snackbar(String msg){
        Snackbar.make(binding.getRoot(),msg, BaseTransientBottomBar.LENGTH_SHORT)
                .setBackgroundTint(Color.RED).show();
    }
    private native String getinsta();
    private native String getyoutube();
}