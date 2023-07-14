package com.anmol.clashking;
import android.app.Activity;
import android.widget.Toast;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class P  implements PaymentResultListener {
    Activity activity;
    public P(Activity activity){
        this.activity=activity;
    }
    Checkout checkout=new Checkout();
    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
    private void jsondata() throws JSONException {
        JSONObject options = new JSONObject();
        options.put("name", "Dark");
        options.put("description", "Payment for joining PUBG tournament");
        options.put("send_sms_hash", true);
        options.put("allow_rotation", false);
        options.put("currency", "INR");
        options.put("amount","2000");
        JSONObject preFill = new JSONObject();
        preFill.put("email", "email");
        preFill.put("contact", "phone");
    }
}
