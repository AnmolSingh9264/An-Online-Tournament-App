package com.anmol.clashking;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anmol.clashking.databinding.FragmentMatchBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link match#newInstance} factory method to
 * create an instance of this fragment.
 */
public class match extends Fragment {
 FragmentMatchBinding binding;
    matchadapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public match() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment match.
     */
    // TODO: Rename and change types and number of parameters
    public static match newInstance(String param1, String param2) {
        match fragment = new match();
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
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(requireContext());
                dialog.setCancelable(false);
                dialog.setMessage(Html.fromHtml("Are you want to exit?"));
                dialog.setPositiveButton(Html.fromHtml("<font color='#FF000000'>yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
        Home home=new Home();
        requireActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        binding=FragmentMatchBinding.inflate(getLayoutInflater(),container,false);
        requireActivity().getWindow().setStatusBarColor(Color.rgb(255,255,255));
        binding.shimmerViewContainer.startShimmer();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("match");
        try {
            binding.Rview.setItemAnimator(null);
            binding.Rview.setLayoutManager(new LinearLayoutManager(requireContext()));
        }catch (IndexOutOfBoundsException e){
            Toast.makeText(requireContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        FirebaseRecyclerOptions<matchmodel> options =
                new FirebaseRecyclerOptions.Builder<matchmodel>()
                        .setQuery(reference, matchmodel.class)
                        .build();
        adapter=new matchadapter(options);
        binding.Rview.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.Rview.setVisibility(View.VISIBLE);
            }
        },3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}