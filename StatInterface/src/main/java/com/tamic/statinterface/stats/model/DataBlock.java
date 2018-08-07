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
package com.tamic.statinterface.stats.model;


import java.util.List;

/**
 * 上传数据对象bean
 * Created by tamic on 2016-03-24.
 */
public class DataBlock {
    private List<AppAction> app_action ;
    private List<Page> page ;
    private List<Event> event ;
    private List<ExceptionInfo>exceptionInfos;


    public List<ExceptionInfo> getExceptionInfos() {
        return exceptionInfos;
    }

    public void setExceptionInfos(List<ExceptionInfo> exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public List<AppAction> getApp_action() {
        return app_action;
    }

    public void setApp_action(List<AppAction> app_action) {
        this.app_action = app_action;
    }

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "DataBlock{" +
                "app_action=" + app_action +
                ", page=" + page +
                ", event=" + event +
                ", exceptionInfos=" + exceptionInfos +
                '}';
    }
}
