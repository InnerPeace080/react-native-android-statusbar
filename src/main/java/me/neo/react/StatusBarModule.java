package me.neo.react;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by Nishanth Shankar on 9/24/15.
 */
public class StatusBarModule extends ReactContextBaseJavaModule {
    Activity mActivity = null;
    public StatusBarModule(ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        mActivity = activity;
    }

    @Override
    public String getName() {
        return "StatusBarAndroid";
    }

    @ReactMethod
    public void setHexColor(String hex){
        int color = Color.parseColor(hex);
        setStatusColor(color);
    }

    @ReactMethod
    public void setRGB(int r, int g, int b){
        int color = Color.rgb(r, g, b);
        setStatusColor(color);
    }

    @ReactMethod
    public void setARGB(int a,int r, int g, int b){
        int color = Color.argb(a, r, g, b);
        setStatusColor(color);
    }

    void setStatusColor(final int color){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = mActivity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(color);

                }

            }
        });

    }


    @ReactMethod
    public void hideStatusBar(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View decorView = mActivity.getWindow().getDecorView();
                // Hide the status bar.
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN ;
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
    }

    @ReactMethod
    public void showStatusBar(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT < 16)
                    return;
                View decorView = mActivity.getWindow().getDecorView();
                // Hide the status bar.
                int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                decorView.setSystemUiVisibility(uiOptions);
            }
        });
    }

    @ReactMethod
    public void fullScreen(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View decorView = mActivity.getWindow().getDecorView();
                if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                } else if(Build.VERSION.SDK_INT >= 19) {
                    //for new api versions.
                    int uiOptions =View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    decorView.setSystemUiVisibility(uiOptions);
                }
            }
        });
    }
}
