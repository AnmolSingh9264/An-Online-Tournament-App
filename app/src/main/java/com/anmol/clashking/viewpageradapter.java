package com.anmol.clashking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpageradapter extends FragmentStateAdapter {
      int tab;

    public viewpageradapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,int tabcount) {
        super(fragmentManager, lifecycle);
        tab=tabcount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new moneyadd();
            case 1:return new moneywithdraw();
            default:return null;
        }
    }

    @Override
    public int getItemCount() {
        return tab;
    }
}
