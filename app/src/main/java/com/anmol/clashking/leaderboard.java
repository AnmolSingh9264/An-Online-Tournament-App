package com.anmol.clashking;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.model.content.GradientColor;
import com.anmol.clashking.databinding.FragmentLeaderboardBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link leaderboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leaderboard extends Fragment {
leaderboard_adapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public leaderboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leaderboard.
     */
    // TODO: Rename and change types and number of parameters
    public static leaderboard newInstance(String param1, String param2) {
        leaderboard fragment = new leaderboard();
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
        requireActivity().getWindow().setStatusBarColor(Color.rgb(255,255,255));
        FragmentLeaderboardBinding binding=FragmentLeaderboardBinding.inflate(getLayoutInflater(),container,false);
        binding.leaderboard.setItemAnimator(null);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        //binding.leaderboard.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.leaderboard.setLayoutManager(mLayoutManager);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("kill");
        FirebaseRecyclerOptions<leaderboard_model> options =
                new FirebaseRecyclerOptions.Builder<leaderboard_model>()
                        .setQuery(reference.orderByChild("kills"), leaderboard_model.class)
                        .build();
        adapter=new leaderboard_adapter(options);
        binding.leaderboard.setHasFixedSize(true);
        binding.leaderboard.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}