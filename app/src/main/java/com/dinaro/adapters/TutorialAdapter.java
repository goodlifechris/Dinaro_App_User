package com.dinaro.adapters;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dinaro.fragments.FirstFragment;
import com.dinaro.fragments.ThirdFragment;


public class TutorialAdapter extends FragmentPagerAdapter {

    private int num;

    public TutorialAdapter(final Context context, final FragmentManager fm, int num) {
        super(fm);
        this.num = num;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstFragment();

            case 1:
                return new ThirdFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return num;
    }

}


