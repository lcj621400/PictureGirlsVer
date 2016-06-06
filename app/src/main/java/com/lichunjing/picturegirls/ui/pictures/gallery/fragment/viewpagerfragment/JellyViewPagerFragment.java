package com.lichunjing.picturegirls.ui.pictures.gallery.fragment.viewpagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.widget.jellyViewPager.JellyViewPager;


public class JellyViewPagerFragment extends ViewPagerBaseFragment {

    private JellyViewPager viewPager;

    public static ViewPagerBaseFragment newInstance(int id) {
        ViewPagerBaseFragment fragment = new JellyViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PARAMS,id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_jelly_view_pager, container, false);
        viewPager= (JellyViewPager) view.findViewById(R.id.jellyviewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setJellyViewPager(viewPager);
    }
}
