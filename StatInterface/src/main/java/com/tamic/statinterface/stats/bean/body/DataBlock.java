/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tamic.statinterface.stats.bean.body;


import android.text.TextUtils;

import com.tamic.statinterface.stats.bean.db.TcNote;
import com.tamic.statinterface.stats.util.JsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yandeqing
 * @version v3.7.1
 * 备注:整合数据集合一次性传给后台
 * @date 2018/8/14
 */
public class DataBlock {
    private List<AppAction> appActions;
    private List<Page> page;
    private List<Event> event;
    private List<ExceptionInfo> exceptionInfos;

    private DataBlock(Builder builder) {
        appActions = builder.appActions;
        page = builder.page;
        event = builder.event;
        exceptionInfos = builder.exceptionInfos;
    }

    public List<AppAction> getAppActions() {
        return appActions;
    }

    public List<Page> getPage() {
        return page;
    }

    public List<Event> getEvent() {
        return event;
    }

    public List<ExceptionInfo> getExceptionInfos() {
        return exceptionInfos;
    }

    @Override
    public String toString() {
        return "DataBlock{" +
                "appActions=" + appActions +
                ", page=" + page +
                ", event=" + event +
                ", exceptionInfos=" + exceptionInfos +
                '}';
    }

    public static final class Builder {
        private List<AppAction> appActions;
        private List<Page> page;
        private List<Event> event;
        private List<ExceptionInfo> exceptionInfos;

        public Builder(List<TcNote> list) {
            appActions = new ArrayList<>();
            page = new ArrayList<>();
            event = new ArrayList<>();
            exceptionInfos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getFirstCloumn())) {
                    AppAction appAction = JsonUtil.parseObject(list.get(i).getFirstCloumn(), AppAction.class);
                    appActions.add(appAction);
                }
                if (!TextUtils.isEmpty(list.get(i).getSecondCloumn())) {
                    Page page1 = JsonUtil.parseObject(list.get(i).getSecondCloumn(), Page.class);
                    page.add(page1);
                }
                if (!TextUtils.isEmpty(list.get(i).getThirdCloumn())) {
                    Event event1 = JsonUtil.parseObject(list.get(i).getThirdCloumn(), Event.class);
                    event.add(event1);
                }
                if (!TextUtils.isEmpty(list.get(i).getFourCloumn())) {
                    ExceptionInfo exceptionInfo = JsonUtil.parseObject(list.get(i).getFourCloumn(), ExceptionInfo.class);
                    exceptionInfos.add(exceptionInfo);
                }
            }
            Collections.sort(page, new Comparator<Page>() {

                @Override
                public int compare(Page lhs, Page rhs) {
                    return lhs.getPage_start_time().compareTo(rhs.getPage_start_time());
                }
            });
            Collections.sort(event, new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {
                    return lhs.getAction_time().compareTo(rhs.getAction_time());
                }
            });
        }

        public Builder appActions(List<AppAction> appActions) {
            this.appActions = appActions;
            return this;
        }

        public Builder page(List<Page> page) {
            this.page = page;
            return this;
        }

        public Builder event(List<Event> event) {
            this.event = event;
            return this;
        }

        public Builder exceptionInfos(List<ExceptionInfo> exceptionInfos) {
            this.exceptionInfos = exceptionInfos;
            return this;
        }

        public DataBlock build() {
            return new DataBlock(this);
        }
    }
}
