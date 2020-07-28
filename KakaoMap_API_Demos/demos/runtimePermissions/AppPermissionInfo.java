package net.daum.android.map.openapi.sampleapp.demos.runtimePermissions;


import android.Manifest;

/**
 * Created by kris.shin on 11/17/15.
 *
 */
public class AppPermissionInfo {
    public static final String [] LOCATION_PERMISSIONS = new String [] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static final String [] VOICE_SEARCH_PERMISSIONS = new String [] {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
}
