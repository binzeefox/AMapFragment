package com.binzee.amapfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 地图碎片
 * <p>
 * TODO 点方向
 *
 * @author 狐彻
 * 2020/09/22 12:57
 */
public class MapFragment extends Fragment {
    public static final int MAP_TYPE_NORMAL = AMap.MAP_TYPE_NORMAL;
    public static final int MAP_TYPE_SATELLITE = AMap.MAP_TYPE_SATELLITE;
    public static final int MAP_TYPE_NIGHT = AMap.MAP_TYPE_NIGHT;
    public static final int MAP_TYPE_NAVI = AMap.MAP_TYPE_NAVI;
    public static final int MAP_TYPE_BUS = AMap.MAP_TYPE_BUS;

    private static final String TAG = "MapFragment";
    private MapView mMapView;
    private AMap mAMap;
    private AMapLocationClient mClient;
    private UiSettings mUiSetting;
    private final PreParams _preParams = new PreParams();   //预加载
    private boolean isReady = false;
    private OnMapViewReadyListener onReadyListener;    //地图加载回调

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMapView = new MapView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMapView.setLayoutParams(params);
        mAMap = mMapView.getMap();

        mMapView.onCreate(savedInstanceState);
        isReady = true;
        if (onReadyListener != null) onReadyListener.onReady(this);
        initMap();
        return mMapView;
    }

    /**
     * 返回MapView是否准备就绪
     *
     * @author 狐彻 2020/09/23 8:40
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * 设置地图加载监听
     *
     * @author 狐彻 2020/09/22 16:05
     */
    public void setOnMapViewReadyListener(OnMapViewReadyListener listener) {
        onReadyListener = listener;
    }

    /**
     * 获取Map，可能会空指针
     *
     * @author 狐彻 2020/09/22 15:29
     */
    @Nullable
    public AMap getMap() {
        return mAMap;
    }

    /**
     * 获取LocationClient，可能会空指针
     *
     * @author 狐彻 2020/09/22 15:30
     */
    @Nullable
    public AMapLocationClient getLocationClient() {
        return mClient;
    }

    /**
     * 添加location listener
     *
     * @author 狐彻 2020/09/22 13:12
     */
    public void setLocationListener(AMapLocationListener listener) {
        if (mClient == null) _preParams.listener = listener;
        else mClient.setLocationListener(listener);
    }

    /**
     * 添加Options
     *
     * @author 狐彻 2020/09/22 13:13
     */
    public void setLocationOptions(AMapLocationClientOption options) {
        if (mClient == null) _preParams.option = options;
        else mClient.setLocationOption(options);
    }

    /**
     * 设置地图类型
     *
     * @author 狐彻 2020/09/22 13:39
     */
    public void setMapType(int type) {
        if (mAMap == null) _preParams.type = type;
        else mAMap.setMapType(type);
    }

    /**
     * 显示指南针
     *
     * @author 狐彻 2020/09/22 13:41
     */
    public void showCompass(boolean show) {
        if (mUiSetting == null) _preParams.compass = show;
        else mUiSetting.setCompassEnabled(show);
    }

    /**
     * 显示缩放按钮
     *
     * @author 狐彻 2020/09/22 13:43
     */
    public void showZoomButton(boolean show) {
        if (mUiSetting == null) _preParams.zoomBtn = show;
        else mUiSetting.setZoomControlsEnabled(true);
    }

    /**
     * 显示比例尺
     *
     * @author 狐彻 2020/09/22 13:44
     */
    public void showScale(boolean show) {
        if (mUiSetting == null) _preParams.scale = show;
        else mUiSetting.setScaleControlsEnabled(true);
    }

    /**
     * 设置我的位置样式
     *
     * @author 狐彻 2020/09/22 14:20
     */
    public void setMyLocationStyle(MyLocationStyle style) {
        if (mAMap == null) _preParams.style = style;
        else mAMap.setMyLocationStyle(style);
    }

    /**
     * 显示我的位置
     *
     * @author 狐彻 2020/09/22 14:22
     */
    public void showMyLocation(boolean show) {
        if (mAMap == null) _preParams.myLocation = show;
        else mAMap.setMyLocationEnabled(show);
    }

    /**
     * 移动相机
     *
     * @author 狐彻 2020/09/22 14:26
     */
    public void moveCamera(CameraUpdate update) {
        if (mAMap == null) _preParams.updateList.add(update);
        else mAMap.moveCamera(update);
    }

    /**
     * 动画移动相机
     *
     * @param update   镜头更新参数
     * @param dur      动画时长??
     * @param callback 可取消回调
     * @author 狐彻 2020/09/22 14:26
     */
    public void animateCamera(CameraUpdate update, long dur, AMap.CancelableCallback callback) {
        CameraAnimateParams params = new CameraAnimateParams();
        params.callback = callback;
        params.dur = dur;
        params.update = update;
        if (mAMap == null) _preParams.animatedUpdateList.add(params);
        else mAMap.animateCamera(update, dur, callback);
    }

    public void animateCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        CameraAnimateParams params = new CameraAnimateParams();
        params.update = update;
        params.callback = callback;
        if (mAMap == null) _preParams.animatedUpdateList.add(params);
        else mAMap.animateCamera(update);
    }

    public void animateCamera(CameraUpdate update) {
        CameraAnimateParams params = new CameraAnimateParams();
        params.update = update;
        if (mAMap == null) _preParams.animatedUpdateList.add(params);
        else mAMap.animateCamera(update);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 私有方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 初始化地图
     *
     * @author 狐彻 2020/09/22 13:06
     */
    private void initMap() {
        mClient = new AMapLocationClient
                (Objects.requireNonNull(getContext()).getApplicationContext());
        mUiSetting = mAMap.getUiSettings();
        mAMap.setMapType(MAP_TYPE_NORMAL);

        // 处理预设值参数
        if (_preParams.listener != null)
            mClient.setLocationListener(_preParams.listener);
        if (_preParams.option != null)
            mClient.setLocationOption(_preParams.option);
        if (_preParams.type != null)
            mAMap.setMapType(_preParams.type);
        if (_preParams.compass != null)
            mUiSetting.setCompassEnabled(_preParams.compass);
        if (_preParams.zoomBtn != null)
            mUiSetting.setZoomControlsEnabled(_preParams.zoomBtn);
        if (_preParams.scale != null)
            mUiSetting.setScaleControlsEnabled(_preParams.scale);
        if (_preParams.style != null)
            mAMap.setMyLocationStyle(_preParams.style);
        if (_preParams.myLocation != null)
            mAMap.setMyLocationEnabled(_preParams.myLocation);

        _preParams.updateList.forEach(new Consumer<CameraUpdate>() {
            @Override
            public void accept(CameraUpdate update) {
                mAMap.moveCamera(update);
            }
        });

        _preParams.animatedUpdateList.forEach(new Consumer<CameraAnimateParams>() {
            @Override
            public void accept(CameraAnimateParams params) {
                if (params.update != null && params.dur != null && params.callback != null)
                    mAMap.animateCamera(params.update, params.dur, params.callback);
                else if (params.update != null && params.callback != null)
                    mAMap.animateCamera(params.update, params.callback);
                else if (params.update != null)
                    mAMap.animateCamera(params.update);
            }
        });
    }


    ///////////////////////////////////////////////////////////////////////////
    // 生命周期接管
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部类
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 预加载的设置
     *
     * @author 狐彻 2020/09/22 14:01
     */
    private static class PreParams {
        AMapLocationListener listener;
        AMapLocationClientOption option;
        MyLocationStyle style;
        Integer type = -1;   //地图类型
        Boolean compass, zoomBtn, scale, myLocation;
        List<CameraAnimateParams> animatedUpdateList = new ArrayList<>();
        List<CameraUpdate> updateList = new ArrayList<>();
    }

    /**
     * 镜头动画移动参数
     *
     * @author 狐彻 2020/09/22 14:28
     */
    private static class CameraAnimateParams {
        CameraUpdate update;
        Long dur;
        AMap.CancelableCallback callback;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 地图加载完成回调
     *
     * @author 狐彻 2020/09/22 16:03
     */
    public interface OnMapViewReadyListener {
        void onReady(MapFragment fragment);
    }
}
