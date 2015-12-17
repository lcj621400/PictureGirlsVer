package com.lichunjing.picturegirls.ui.gallery.fragment.viewpagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichunjing.picturegirls.R;


public class ViewPagerFragment extends ViewPagerBaseFragment {

    private ViewPager viewPager;

    public static ViewPagerBaseFragment newInstance(int  id) {
        ViewPagerBaseFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PARAMS,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViewPager(viewPager);
    }
}
