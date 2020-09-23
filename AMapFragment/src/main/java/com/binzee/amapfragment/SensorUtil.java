package com.binzee.amapfragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * 传感器工具
 *
 * TODO 待研究
 * @author 狐彻
 * 2020/09/22 14:52
 */
class SensorUtil {
    private Context mCtx;
    private SensorManager mManager;

    SensorUtil(Context context) {
        mCtx = context;
        mManager = context.getSystemService(SensorManager.class);
    }

//    public void startSensorManager(){
//        mManager.
//    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public int getScreenRotationOnPhone() {
        final Display display = ((WindowManager) mCtx
                .getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }
}
