package com.anmol.clashking;

import android.text.TextUtils;

import com.anmol.clashking.databinding.ActivityLoginBinding;
import com.anmol.clashking.databinding.FragmentSignupBinding;

public class validation {
    public int validate(ActivityLoginBinding binding){
        if (TextUtils.isEmpty(binding.Email.getText())){
            return ValidateEmail();
        }else if (TextUtils.isEmpty(binding.Password.getText())){
            return Validatepass();
        }else {
            return Validatedone();
        }
    }
    public int validate(FragmentSignupBinding binding){
        if (TextUtils.isEmpty(binding.editTextPhone.getText())){
            return Validatephone();
        }else if (TextUtils.isEmpty(binding.EmailAddress.getText())){
            return ValidateEmail();
        }
        else if (TextUtils.isEmpty(binding.Password.getText())){
            return Validatepass();
        }else {
            return Validatedone();
        }
    }
    public native int ValidateEmail();
    public native int Validatepass();
    public native int Validatedone();
    public native int Validatephone();
}
