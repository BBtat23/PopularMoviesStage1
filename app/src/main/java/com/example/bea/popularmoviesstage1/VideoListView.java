package com.example.bea.popularmoviesstage1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


public class VideoListView extends ListView {

    public VideoListView  (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoListView  (Context context) {
        super(context);
    }

    public VideoListView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}

