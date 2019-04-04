package com.framgia.music_51.screen;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermistoinUtils {
    public static final int REQUEST_RECORD_DOWNLOAD_PERMISSION = 1;
    private Activity mActivity;

    public PermistoinUtils(Activity activity) {
        mActivity = activity;
    }

    private static final String[] RECORD_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public boolean requestPermissionImage() {
        if (isCheckSelfPermission(RECORD_PERMISSION)) {
            requestPermission(RECORD_PERMISSION, REQUEST_RECORD_DOWNLOAD_PERMISSION);
            return true;
        }
        return false;
    }

    private boolean isCheckSelfPermission(String... permissions) {
        if (mActivity != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mActivity,
                        permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(mActivity, permissions,
                requestCode);
    }
}
