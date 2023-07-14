package com.anmol.clashking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anmol.clashking.databinding.FragmentUserBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user extends Fragment {
    String uid;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user() {
        // Required empty public constructor
    }
    public static user newInstance(String param1, String param2) {
        user fragment = new user();
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
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(requireContext());
                dialog.setCancelable(false);
                dialog.setMessage(Html.fromHtml("Are you want to exit?"));
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
                dialog.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        FragmentUserBinding binding=FragmentUserBinding.inflate(getLayoutInflater(),container,false);
        DatabaseReference leaderboard=FirebaseDatabase.getInstance().getReference("kill/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        requireActivity().getWindow().setStatusBarColor(Color.rgb(186, 194, 254));
        FirebaseStorage storage=FirebaseStorage.getInstance();
        ProgressDialog dialog=new ProgressDialog(requireContext());
        dialog.setMessage("Uploading...");
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference fdref=database.getReference("user"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profile");
        dialog.setCancelable(false);
        dialog.create();
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(),login.class));
                requireActivity().finish();
            }
        });
        StorageReference reference=storage.getReference(image()+"/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        ActivityResultLauncher<String> imagepicker=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                dialog.show();
                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Snackbar.make(binding.getRoot(),"Successfully Uploaded", BaseTransientBottomBar.LENGTH_SHORT)
                                .setBackgroundTint(Color.rgb(66, 245, 141)).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Snackbar.make(binding.getRoot(),e.getMessage().toString()+" or Image is grater than 512 kb", BaseTransientBottomBar.LENGTH_SHORT)
                                .setBackgroundTint(Color.RED).show();
                    }
                });
            }
        });
       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {

               fdref.setValue(uri.toString());
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               try {
                   Snackbar.make(binding.getRoot(),e.getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT)
                           .setBackgroundTint(Color.RED).show();
               }catch (IllegalArgumentException exception){
                   Log.d("exception",exception.getMessage().toString());
               }
           }
       });
       fdref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                 String url=snapshot.getValue().toString();
                   leaderboard.child("url").setValue(url);
                   Picasso.get().load(Uri.parse(url)).placeholder(R.drawable.freelogo2__1_).into(binding.profileimage);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               imagepicker.launch("image/*");
            }
        });
        binding.constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homelayout,new developer()).addToBackStack(null).commit();
            }
        });
        binding.constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homelayout,new Editprofile()).addToBackStack(null).commit();
            }
        });
        DatabaseReference name=database.getReference(getname()+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/name");
        name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        binding.textView8.setText(snapshot.getValue().toString());
                        leaderboard.child("name").setValue(snapshot.getValue().toString());
                    }catch (NullPointerException e){
                        Log.d("e",e.getMessage().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.constraintLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference name=database.getReference("share");
                 name.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         Intent sendIntent = new Intent();
                         sendIntent.setAction(Intent.ACTION_SEND);
                         sendIntent.putExtra(Intent.EXTRA_TEXT, snapshot.getValue().toString());
                         sendIntent.setType("text/plain");
                         startActivity(sendIntent);
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
            }
        });
        binding.constraintLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=requireActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.homelayout,new addmoney()).addToBackStack(null).commit();
            }
        });
        return binding.getRoot();
    }
    private native String image();
    private native String profile();
    private native String getname();
}