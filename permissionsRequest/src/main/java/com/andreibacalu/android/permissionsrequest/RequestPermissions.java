package com.andreibacalu.android.permissionsrequest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public class RequestPermissions {

    public static void requestPermissions(PermissionRequester permissionRequester, Activity activity, String[] permissions, int[] requestCodes) {
        Assert.assertNotNull(permissions);
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    permissionRequester.onPermissionNeedsExplanation(requestCodes[i], permissions[i]);
                } else {
                    permissionRequester.onPermissionNotGranted(requestCodes[i], permissions[i]);
                }
            } else {
                permissionRequester.onPermissionGranted(requestCodes[i]);
            }
        }
    }

    public static void requestPermissions(PermissionRequester permissionRequester, Fragment fragment, String[] permissions, int[] requestCodes) {
        Assert.assertNotNull(permissions);
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(fragment.getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (fragment.shouldShowRequestPermissionRationale(permissions[i])) {
                    permissionRequester.onPermissionNeedsExplanation(requestCodes[i], permissions[i]);
                } else {
                    permissionRequester.onPermissionNotGranted(requestCodes[i], permissions[i]);
                }
            } else {
                permissionRequester.onPermissionGranted(requestCodes[i]);
            }
        }
    }

    public static void requestBulkPermissions(PermissionRequester permissionRequester, Activity activity, String[] permissions, int requestCode) {
        List<String> permissionsNeedExplanation = new ArrayList<>();
        List<String> permissionsNotGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    permissionsNeedExplanation.add(permissions[i]);
                } else {
                    permissionsNotGranted.add(permissions[i]);
                }
            }
        }
        callPermissionRequester(permissionRequester, permissionsNotGranted, permissionsNeedExplanation, requestCode);
    }

    public static void requestBulkPermissions(PermissionRequester permissionRequester, Fragment fragment, String[] permissions, int requestCode) {
        List<String> permissionsNeedExplanation = new ArrayList<>();
        List<String> permissionsNotGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(fragment.getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (fragment.shouldShowRequestPermissionRationale(permissions[i])) {
                    permissionsNeedExplanation.add(permissions[i]);
                } else {
                    permissionsNotGranted.add(permissions[i]);
                }
            }
        }
        callPermissionRequester(permissionRequester, permissionsNotGranted, permissionsNeedExplanation, requestCode);
    }

    private static void callPermissionRequester(PermissionRequester permissionRequester, List<String> permissionsNotGranted, List<String>
            permissionsNeedExplanation, int requestCode) {
        if (permissionsNeedExplanation.size() > 0 || permissionsNotGranted.size() > 0) {
            if (permissionsNeedExplanation.size() > 0) {
                permissionRequester.onPermissionNeedsExplanation(requestCode, permissionsNeedExplanation.toArray(new String[permissionsNeedExplanation.size()]));
            }
            if (permissionsNotGranted.size() > 0) {
                permissionRequester.onPermissionNotGranted(requestCode, permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
            }
        } else {
            permissionRequester.onPermissionGranted(requestCode);
        }
    }
}
