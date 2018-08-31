package com.tamic.statinterface.stats.bean.body;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author LIUYONGKUI726
 * @date 2017-12-07
 */

public class ViewPath {

    private View view;
    /**
     * view在视图树上的路径
     */
    public String viewTree;
    /**
     * 文本控件的值
     * 输入框的不采集，涉及用户隐私
     */
    public String viewValue;
    public String tagValue;

    public String specifyTag;
    /**
     * 层级默认为0
     */
    public int level = 0;
    /**
     * 需要过滤的层级
     */
    public int filterLevelCount = 3;

    public ViewPath(View view, String viewTree) {
        this.view = view;
        this.viewTree = viewTree;
        getViewValue(view);
    }

    private void getViewValue(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            viewValue = button.getText().toString();
            setTag(button);
        } else if (view instanceof EditText) {
            setTag(view);
            //不采集值
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            viewValue = textView.getText().toString();
            setTag(textView);
        }
    }

    private void setTag(View view) {
        Object tag = view.getTag();
        if (tag != null) {
            tagValue = tag.toString();
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
        getViewValue(view);
    }

    @Override
    public String toString() {
        return "ViewPath{" +
                "view=" + view +
                ", viewTree='" + viewTree + '\'' +
                ", viewValue='" + viewValue + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", specifyTag='" + specifyTag + '\'' +
                ", level=" + level +
                ", filterLevelCount=" + filterLevelCount +
                '}';
    }
}
