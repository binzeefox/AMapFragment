# Readme

## 权限
需求以下权限
```
    <!--用于进行网络定位-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!--用于访问GPS定位-->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <!--获取运营商信息，用于支持提供运营商信息相关的接口-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!--用于访问wifi网络信息，wifi信息会用于进行网络定位-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <!--这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <!--用于访问网络，网络定位需要上网-->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!--用于读取手机当前的状态-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <!--写入扩展存储，向扩展卡写入数据，用于写入缓存定位数据-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!--用于申请调用A-GPS模块-->
    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"/>
    <!--用于申请获取蓝牙信息进行室内定位-->
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

## 设置
在AndroidManifest中的Application内添加以下内容

```
        <meta-data android:name="com.amap.api.v2.apikey"
            android:value="你的高德ApiKey">
        </meta-data>
```

## 使用
实例化MapFragment后，内部封装了常用的方法，并且对空指针进行了优化。

==注意，方法```getMap()```,```getLocationClient()```可能会造成空指针，请在确定Fragment加载完毕或调用```setOnMapViewReadyListener(OnMapViewReadyListener listener)```添加回调后，在回调内调用==