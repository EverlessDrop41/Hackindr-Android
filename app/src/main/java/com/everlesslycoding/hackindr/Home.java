package com.everlesslycoding.hackindr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.everlesslycoding.hackindr.MainContentFragments.IdeaView;
import com.everlesslycoding.hackindr.MainContentFragments.PopularIdeaLists;

public class Home extends FragmentActivity {

   public static final int NUM_PAGES = 2;

    IdeaView mIdeaView = new IdeaView();
    PopularIdeaLists mPopularIdeaLists = new PopularIdeaLists();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mIdeaView;
                case 1:
                    return mPopularIdeaLists;
                default:
                    return mIdeaView;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
