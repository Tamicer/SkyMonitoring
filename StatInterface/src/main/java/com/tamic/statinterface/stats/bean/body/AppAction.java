package com.tamic.statinterface.stats.bean.body;

/**

 * Created by Tamic 2016-03-24.
 */
public class AppAction {
    private String action_time ;
    private int app_action_type ;
    private String app_action_desc ;

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public int getApp_action_type() {
        return app_action_type;
    }

    public void setApp_action_type(int app_action_type) {
        this.app_action_type = app_action_type;
    }

    public String getApp_action_desc() {
        return app_action_desc;
    }

    public void setApp_action_desc(String app_action_desc) {
        this.app_action_desc = app_action_desc;
    }

    @Override
    public String toString() {
        return "AppAction{" +
                "action_time='" + action_time + '\'' +
                ", app_action_type='" + app_action_type + '\'' +
                '}';
    }
}
