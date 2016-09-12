package me.neo.react;

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
    public StatusBarModule(ReactApplicationContext reactContext) {
        super(reactContext);
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
        if (getCurrentActivity()!=null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= 21) {
                        Window window = getCurrentActivity().getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(color);

                    }

                }
            });
        }

    }


    @ReactMethod
    public void hideStatusBar(){
        if (getCurrentActivity()!=null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        View decorView = getCurrentActivity().getWindow().getDecorView();
                        // Hide the status bar.
                        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
                    }
                    catch (Exception ex){ex.printStackTrace();}

                }
            });
        }
    }

    @ReactMethod
    public void showStatusBar(){
        if (getCurrentActivity()!=null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT < 16)
                        return;
                    try{
                        View decorView = getCurrentActivity().getWindow().getDecorView();
                        // Hide the status bar.
                        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                        decorView.setSystemUiVisibility(uiOptions);
                    }
                    catch (Exception ex){ex.printStackTrace();}

                }
            });
        }
    }

    @ReactMethod
    public void fullScreen(){
        if (getCurrentActivity()!=null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        View decorView = getCurrentActivity().getWindow().getDecorView();
                        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
                        } else if (Build.VERSION.SDK_INT >= 19) {
                            //for new api versions.
                            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                            decorView.setSystemUiVisibility(uiOptions);
                        }
                    }
                    catch (Exception ex){ex.printStackTrace();}


                }
            });
        }
    }
}
