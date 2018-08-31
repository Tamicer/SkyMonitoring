package com.tamic.statsdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.tamic.statinterface.stats.bean.body.ViewPath;
import com.tamic.statinterface.stats.core.TcStatInterface;


/**
 * Created by LIUYONGKUI726 on 2017-12-07.
 */

public abstract class TamicActivity extends AppCompatActivity {

    private String TAG = "Tamic";

    View rootView;

    String rootViewTree;

    String bigDataPrefix;

    String bigDataIngorePrefix;

    String bigDataEventPrefix;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        rootView = getWindow().getDecorView();
        rootViewTree = getClass().getName();
        bigDataPrefix = "Tamic_test";
        bigDataIngorePrefix = bigDataPrefix + "";
        bigDataEventPrefix = bigDataIngorePrefix + "Igmore";
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        TcStatInterface.recordPageStart(TamicActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TcStatInterface.recordPageEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            ViewPath path = findClickView(ev);
            if (path != null) {
                Log.e(TAG, "path -->" + path);
                TcStatInterface.initEvent(path);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private ViewPath findClickView(MotionEvent ev) {
        ViewPath clickView = new ViewPath(rootView, rootViewTree);
        Log.e(TAG, "bigdata-->findClickView");
        return searchClickView(clickView, ev, 0);
    }


    private ViewPath searchClickView(ViewPath myView, MotionEvent event, int index) {
        ViewPath clickView = null;
        View view = myView.getView();
        if (isInView(view, event)) {
            //遍历根view下的子view以及所有子view上的控件
            // 当第二层不为LinearLayout时，说明系统进行了改造，多了一层,需要多剔除一层
            myView.level++;
            if (myView.level == 2 && !"LinearLayout".equals(view.getClass().getSimpleName())) {
                myView.filterLevelCount++;
            }
            if (myView.level > myView.filterLevelCount) {
                myView.viewTree = myView.viewTree + "." + view.getClass().getSimpleName() + "[" + index + "]";
            }
            Log.i(TAG, "bigdata-->tag = " + view.getTag());
            // 如果Layout有设置特定的tag，则直接返回View，主要用于复合组件的点击事件
            if (view.getTag() != null) {
                // 主动标记不需要统计时，不进行自动统计
                String tag = view.getTag().toString();
                if (tag.startsWith(bigDataIngorePrefix)) {
                    return null;
                } else if (tag.startsWith(bigDataPrefix)) {
                    if (tag.startsWith(bigDataEventPrefix)) {
                        myView.specifyTag = tag.replace(bigDataEventPrefix, "");
                    }
                    return myView;
                }
            }
            if (view instanceof ViewGroup) {
                //遇到一些Layout之类的ViewGroup，继续遍历它下面的子View
                if (view instanceof AbsListView) {
                    Log.i(TAG, "bigdata-->AbsListView ");
                    return null;
                }
                ViewGroup group = (ViewGroup) view;
                int childCount = group.getChildCount();
                if (childCount == 0) {
                    return myView;
                }
                for (int i = childCount - 1; i >= 0; i--) {
                    myView.setView(group.getChildAt(i));
                    clickView = searchClickView(myView, event, i);
                    if (clickView != null) {
                        return clickView;
                    }
                }
            } else {
                clickView = myView;
            }
        }
        return clickView;
    }

    private boolean isInView(View view, MotionEvent event) {
        //能被点击的view必然是可见的
        if (view == null || view.getVisibility() != View.VISIBLE) {
            return false;
        }
        int clickX = (int) event.getRawX();
        int clickY = (int) event.getRawY();
        //如下的view表示Activity中的子View或者控件
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();
        //返回true，则判断这个view被点击了
        return clickX > x && clickX < (x + width) && clickY > y && clickY < (y + height);
    }
}
