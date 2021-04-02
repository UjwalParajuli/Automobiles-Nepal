package com.example.automobilesnepal.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public VerticalSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        outRect.left = space;
//        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
//        if(parent.getChildAdapterPosition(view) == 0) {
//            outRect.left = 0;
//        }
        int childCount = parent.getAdapter().getItemCount();
        if (parent.getChildAdapterPosition(view) == childCount - 1){
            outRect.bottom = 200;
        }
    }
}
