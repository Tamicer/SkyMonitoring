package com.tamic.statsdkdemo;

import android.view.View;

/**
 * Created by LIUYONGKUI726 on 2017-12-07.
 */

public class ViewPath {

    View view;              //view
    String viewTree;          //view在视图树上的路径
    String specifyTag;
    int level = 0;//层级默认为0
    int filterLevelCount = 3;//需要过滤的层级

    ViewPath(View view, String viewTree) {
        this.view = view;
        this.viewTree = viewTree;
    }

}
