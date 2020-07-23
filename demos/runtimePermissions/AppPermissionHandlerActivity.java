package net.daum.android.map.openapi.sampleapp.demos.runtimePermissions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by kris.shin on 11/17/15.
 *
 */
public class AppPermissionHandlerActivity extends Activity {
    public static final String PERMISSION_STRINGS = "permissions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String[] permissions = intent.getStringArrayExtra(PERMISSION_STRINGS);
        ActivityCompat.requestPermissions(this, permissions, AppPermissionHelper.REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AppPermissionHelper.getAppPermissionHelper().onPermissionRequest(requestCode, grantResults);
        finish();
    }
}
