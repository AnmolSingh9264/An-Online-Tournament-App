package com.anmol.clashking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anmol.clashking.databinding.FragmentMoneyaddBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;


public class moneyadd extends Fragment{
    FragmentMoneyaddBinding binding;
    Checkout checkout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public moneyadd() {
    }
    public static moneyadd newInstance(String param1, String param2) {
        moneyadd fragment = new moneyadd();
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
        binding=FragmentMoneyaddBinding.inflate(getLayoutInflater(),container,false);
        binding.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.amountbox.getText())) {
                    Intent intent = new Intent(requireContext(), payment.class);
                    intent.putExtra(getkey(), binding.amountbox.getText().toString());
                    startActivity(intent);
                }else{
                    binding.amountbox.setError("Error");
                }
            }
        });
        return binding.getRoot();
    }
    private native String getkey();
}