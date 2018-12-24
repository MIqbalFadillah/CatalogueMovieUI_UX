package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment.FavoriteHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment.NowPlayingHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment.UpcomingHome;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private String title;
    private static final String ARG_TITLE = "title";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new fragmentChild(getChildFragmentManager()));

        tabLayout = (TabLayout) view.findViewById(R.id.tab_head);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }

//        });
        return view;
    }


    private class fragmentChild extends FragmentPagerAdapter {
        String now_playing = getResources().getString(R.string.now_playing);
        String up_coming = getResources().getString(R.string.upcoming);
        String fav_movie = getResources().getString(R.string.Fav_movie);
        final String tabs[]={now_playing,up_coming,fav_movie};

        public fragmentChild(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NowPlayingHome();
                case 1:
                    return new UpcomingHome();
                case 2:
                    return new FavoriteHome();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length; //mengambil data tabel
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabs[position];
        }
    }
}
