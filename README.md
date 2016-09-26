# TcStatInterface
#Android StaticFrameWork
自定义统计SDK, 完全放弃第三方平台，让app拥有自主的数据统计功能
>支持页面统计
>自定义事件统计
>APP启动退出统计，crsah日志统计




客户端SDK功能概述
-------
在使用统计服务前，开发者先要拿到本身APPID。其中AppId是客户端的身份标识，在客户端SDK初始化时使用。然后下载最新Library的SDK压缩包，其中包括了Android SDK和AndroidDemo。Android版SDK以Module形式提供， 你的APP只需要添加少量代码和配置，即可完成接入TcInterface统计服务。

>统计服务Android

SDK所有的接口都封装在TcStatInterface抽象类的静态方法中，主要功能接口请参考第3节API说明。应用在启动时，需要调用initialize方法来初始化统计服务，之后便可按照统计的业务需求，调用统计数据上报接口上报统计打点。
SDK提供了接口给开发者来设置向统计统计服务器上报统计数据的策略，开发者可以在任意时候调用修改策略。客户端SDK上报的数据包括默认事件统计、应用全局(AppAction)统计（用于统计app的唤醒、打开关闭频率、使用时长等）、页面访问统计(Page)和自定义事件统计(Event)。
统计SDK提供app的崩溃日志收集功能（统计SDK2.0 将会新增）。功能开启后，对于app在使用过程中的崩溃，SDK将自动采集崩溃日志，并上传到统计后台；统计后台会根据app版本，对崩溃进行聚合、展示。开发者可以根据app实际情况情况，将该崩溃标记成已处理或者忽略状态。

SDK使用配置
----


本节主要介绍使用好房统计SDK前的准备工作，开发者也可以参照SDK中的demo来配置。

2.1.  配置AndroidManifest.xml文件
SDK支持的最低安卓版本为2.2。
       
         <uses-sdk  android:minSdkVersion="8"/>
统计服务需要的权限列表
权限 
 
   
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  
 对应的5种权限，用途如下：
 
网络访问权限，需要联网以便于向小米统计服务端上报统计数据。
查看网络状态，用于根据不同的网络环境，选择相应的上报策略。同时也需要上报当前的网络环境用于后台统计展示。
读取手机状态和身份，用于获取设备的唯一标识，为当前设备生成一个唯一设备ID。
查看Wifi状态权限，用于获取mac地址，生成设备唯一标示。
获取用户地理位置信息，用于获取精准确的地理位置，变于统计用户分布情况。

2.2. 初始化统计服务
应用启动时，需要调用统计SDK中的初始化方法，传入appID（目前已定义好相关APPID，参见下表）等身份验证参数和应用对应的渠道号。在调用其他统计服务相关API前，必须调用本方法初始化统计服务，
建议您在程序的Application的onCreate中初始化Tamic统计服务。传入即可。



APP常规数据统计
--

本节主要介绍如何设置数据上报策略，如何记录页面访问行为和用户自定义事件。本节也介绍了通过集成测试模块验证SDK是否集成成功的方法。


3.1. 数据上报策略

统计服务SDK会先把数据记录缓存在本地，然后根据开发者设定的数据上报策略，触发上报流程。SDK中提供了一系列数据上报策略供开发者选择，这些策略包括：
   
   >UPLOAD_POLICY_REALTIME 实时上报。每当有一条新的记录，就会激发一次上报。
   
   >UPLOAD_POLICY_WIFI_ONLY 只在WIFI下上报。当设备处于WIFI连接时实时上报，否则不上报记录。
   
   > UPLOAD_POLICY_BATCH 批量上报。当记录在本地累积超过一个固定值时（100条），会触发一次上报。
   
   > UPLOAD_POLICY_WHILE_INITIALIZE
      启动时候上报。每次应用启动（调用initialize方法）时候，会将上一次应用使用产生的数据记录打包上报。
    UPLOAD_POLICY_INTERVAL
        指定时间间隔上报。开发者可以指定从1分钟-60天之间的任意时间间隔上报数据记录。需要注意，由于SDK并没有使用安卓的实时唤醒机制，采用Handler,因此采用此策略上报，SDK做不到严格的遵守开发者设定的间隔，而会根据应用数据采集的频率和设备休眠策略，会有一定的偏差。
        
> UPLOAD_POLICY_DEVELOPMENT
调试模式。使用此策略，只有开发者手动调用一个接口才会触发上报，否则在任何情况下都不上报。SDK中提供了一个reportData()方法用于手动触发。这种策略主要用于开发者调试时候可手动控制上报时机，便于做数据对比。
 

统计服务的本地缓存最长会永久保存，只要在数据有效期内上报流程被触发，就会将本地之前没上报成功的所有数据打包ZIP格式上报。如果数据上报成功，则会把已经成功的数据从本地删除。
默认的数据上报策略是UPLOAD_POLICY_INTERVAL，默认的上报时间间隔是三分钟（非wifi是30分钟） 现在也只有默认策略有效。



设置上报策略的代码示例如下：
        
        // 设置策略模式  第一个是策略模式   ，第二是是时间（单位/分）
       TcStatInterface.setUploadPolicy(TcStatInterface.UploadPolicy.UPLOAD_POLICY_INTERVA, 3);
    
    
       
    

API说明
--
 
4.1. API细节
     请具体看demo 注释

5. 集成步骤

5.1 依赖项目

 gradle中配置依赖module, 将项目增加为自己的子模块 

 
    dependencies {
 
       compile project(':StatInterface')
   }

 5.2 配置Settings.gradle
 
      include ':app' ,':StatInterface'
 
 5.3 加入权限
 
  见2.1的说明。
 5.4 初始化 
 
   Application的onCreate()：
 
           // assets
        String fileName = "stat_id.json";

        String url = "http://www.baidu.com";
        // init statSdk
        TcStatInterface.initialize(this, appId, "you app chanel", fileName);
        // set upload url
        TcStatInterface.setUrl(url);
           
  见2.3说明 具体见demo
  
 5.5 其他
 
    如果你还在用Eclispe,直接用源码或者依赖jar
    
    TcStatSdk_2.0.jar
    
 5.6 调用
 
 
 
        findViewById(R.id.id_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                TcStatInterface.onEvent("main", "onlick", "send data");
                                //发送数据
                                TcStatInterface.reportData();

                        }

                });

                findViewById(R.id.id_button2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                // 测试
                                TcStatInterface.onEventParameter("onclick", "open next");

                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                startActivity(intent);

                        }

                });

注意
--

  目前服务端代码需要你自我实现，数据结结构按客户端数据Model实现即可。



> 作者：Tamic : http://www.jianshu.com/p/cd83e81b78aa

> crash日志：NULL

