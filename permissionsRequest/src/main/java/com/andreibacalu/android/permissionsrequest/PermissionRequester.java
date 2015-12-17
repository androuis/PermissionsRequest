package com.andreibacalu.android.permissionsrequest;

public interface PermissionRequester {

    void onPermissionGranted(int requestCode);

    void onPermissionNotGranted(int requestCOde, String... permission);

    void onPermissionNeedsExplanation(int requestCode, String... permission);
}
