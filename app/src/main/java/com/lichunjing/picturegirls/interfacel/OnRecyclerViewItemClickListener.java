package com.lichunjing.picturegirls.interfacel;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/5/20.
 */
public abstract class OnRecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener{

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView mRecyclerView;

    public OnRecyclerViewItemClickListener(RecyclerView recyclerView){
        this.mRecyclerView=recyclerView;
        mGestureDetector=new GestureDetectorCompat(mRecyclerView.getContext(),new ItemTouchHelperGestureListener());
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public  void onItemLongClick(RecyclerView.ViewHolder viewHolder){}
    public  void onItemDoubleClick(RecyclerView.ViewHolder viewHolder){}

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null){
                RecyclerView.ViewHolder viewHolder=mRecyclerView.getChildViewHolder(child);
                onItemClick(viewHolder);
                return true;
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null){
                RecyclerView.ViewHolder viewHolder=mRecyclerView.getChildViewHolder(child);
                onItemLongClick(viewHolder);
            }
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            View child=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null){
                RecyclerView.ViewHolder viewHolder=mRecyclerView.getChildViewHolder(child);
                onItemDoubleClick(viewHolder);
                return true;
            }
            return false;
        }

    }
}
