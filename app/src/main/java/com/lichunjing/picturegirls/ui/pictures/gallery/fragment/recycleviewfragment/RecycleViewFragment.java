package com.lichunjing.picturegirls.ui.pictures.gallery.fragment.recycleviewfragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lcj621400 on 2015/12/17.
 */
public class RecycleViewFragment extends RecycleViewBaseFragment{




    public static RecycleViewBaseFragment getInstance(int id){
        RecycleViewBaseFragment fragment=new RecycleViewFragment();
        Bundle argument=new Bundle();
        argument.putInt(ID_PARAMS,id);
        fragment.setArguments(argument);
        return fragment;
    }


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return linearLayoutManager;
    }
}
