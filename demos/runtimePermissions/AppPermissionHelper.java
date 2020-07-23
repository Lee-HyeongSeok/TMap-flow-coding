package net.daum.android.map.openapi.sampleapp.demos.runtimePermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

//import net.daum.android.map.MapProcessMode;
//import net.daum.mf.map.common.Log;


/**
 * Created by kris.shin on 11/13/15.
 *
 */
public class AppPermissionHelper {

    public static final String TAG = "AppPermissionHelper";
    public static final int REQUEST_CODE = 0xbeaf;
    private static final boolean useHandlerActivity = true;

    private AppPermissionCallbackHandler callbackHandler = null;

    private AppPermissionHelper() {

    }

    private static class LazyInstanceHolder {
        private static final AppPermissionHelper INSTANCE;
        static {
            INSTANCE = new AppPermissionHelper();
        }
    }

    public static AppPermissionHelper getAppPermissionHelper() {
        return LazyInstanceHolder.INSTANCE;
    }

    private void startAppPermissionHandlerActivity(Context ctx, String[] permissions) {
        //Log.d(TAG, "startAppPermissionHandlerActivity");
        Class cls = AppPermissionHandlerActivity.class;

        Intent intent = new Intent(ctx, cls);
        intent.putExtra(AppPermissionHandlerActivity.PERMISSION_STRINGS, permissions);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);

        try {
            //Log.d(TAG, "startAppPermissionHandlerActivity startActivity");
            ctx.startActivity(intent);
        } catch (Exception e) {
            //Log.d(TAG, "Unable to launch AppPermissionHandlerActivity", e);
        }
    }

    public void checkAndRequestPermisson(Activity activity, String permission, AppPermissionCallbackHandler handler) {
        checkAndRequestPermissons(activity, new String [] {permission}, handler);
    }

    public void checkAndRequestPermissons(Activity activity, String [] permissions, AppPermissionCallbackHandler handler) {
        if (activity==null) return;
        if (permissions==null) return;

        if (checkForPermissions(activity, permissions)) {
            if (handler!=null) {
                handler.onPermissionGranted();
            }
        } else {
            requestPermissions(activity, permissions, handler);
        }
    }

    public boolean checkForPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkForPermissions(Context context, String[] permissions) {
        if (context==null) return false;
        if (permissions==null) return false;
        if (permissions.length<=0) return false;

        boolean hasPermissions = false;
        for(String permission : permissions) {
            hasPermissions = checkForPermission(context, permission) == true;
            if (hasPermissions==false) {
                return false;
            }
        }

        return hasPermissions;
    }

    public void requestPermission(Activity activity, String permission, AppPermissionCallbackHandler handler) {
        requestPermissions(activity, new String [] {permission}, handler);
    }

    public void requestPermissions(Activity activity, String [] permissions, AppPermissionCallbackHandler handler) {

        callbackHandler = handler;

        if(useHandlerActivity) {
            startAppPermissionHandlerActivity(activity, permissions);
        } else {

            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
        }
    }

    public void onPermissionRequest(int requestCode,  int[] grantResults) {
        if (callbackHandler==null) {
            return;
        }

        boolean permissionStatus = false;
        for (int i = 0, size = grantResults.length; i < size; i++) {
            permissionStatus = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            if(permissionStatus==false) break;
        }

        final boolean isPermissionGranted = permissionStatus;
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (isPermissionGranted) {
                    callbackHandler.onPermissionGranted();
                } else {
                    callbackHandler.onPermissionDenied();
                }
                callbackHandler = null;
            }
        });

    }
}
