package com.kelevnor.splash.UTILITY;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import com.kelevnor.splash.R;

public class Permission_Request_Helper {

    /**
     * Author Marios Sifalakis
     * Class to check for Storage Permission and Internet Accessibility
     * Contains all methods for the Request Storage Permission Flow
     *
     * Sets the result in Activity through interface OnAsyncResult
     */
    OnAsyncResult onAsync;
    Activity act;

    //Constructor
    public Permission_Request_Helper(Activity act, OnAsyncResult onAsync){
        this.onAsync = onAsync;
        this.act = act;
    }


    public void onRequestPermissionAction(int requestCode, String permissions[], int[] grantResults){

        if(requestCode == Config.RW_STORAGE_PERMISSION){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(UtilityHelper.isNetworkAvailable(act)){
                    onAsync.onInternetForPermissionSuccess(Config.RESULT_EXT_STORAGE_SUCCESS,"");
                }
                else{
                    UtilityHelper.displayDialogOneButton(act, act.getResources().getString(R.string.error_label), act.getResources().getString(R.string.error_internet), act.getResources().getString(R.string.bottombtn_dismiss));
                    onAsync.onInternetForPermissionFail(Config.RESULT_EXT_STORAGE_SUCCESS_INTERNET_FAIL,"");
                }
            }
            else
            {
                if(UtilityHelper.isNetworkAvailable(act)){
                    UtilityHelper.displayDialogOneButton(act,act.getResources().getString(R.string.permission_required_label), act.getResources().getString(R.string.permission_required_storage),act.getResources().getString(R.string.bottombtn_dismiss));
                    onAsync.onInternetForPermissionFail(Config.RESULT_EXT_STORAGE_FAIL,"");
                }
                else{
                    UtilityHelper.displayDialogOneButton(act, act.getResources().getString(R.string.error_label), act.getResources().getString(R.string.error_internet), act.getResources().getString(R.string.bottombtn_dismiss));
                    onAsync.onInternetForPermissionFail(Config.RESULT_EXT_STORAGE_INTERNET_FAIL,"");
                }
            }
        }
    }

    public void CheckGenericPermissions(){
        if(!checkIfAlreadyhaveExternalStoragePermission(act)){
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissionRationale(act,act.getResources().getString(R.string.permission_required_label), act.getResources().getString(R.string.permission_required_storage_rationale), act.getResources().getString(R.string.bottombtn_dismiss), Manifest.permission.WRITE_EXTERNAL_STORAGE, Config.RW_STORAGE_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Config.RW_STORAGE_PERMISSION);
            }
        }
        else{
            if(UtilityHelper.isNetworkAvailable(act)){
                onAsync.onInternetForPermissionSuccess(Config.RESULT_EXT_STORAGE_SUCCESS,"");
            }
            else{
                onAsync.onInternetForPermissionFail(Config.RESULT_EXT_STORAGE_SUCCESS_INTERNET_FAIL,"");
            }

        }
    }

    //check for storage  permission
    private boolean checkIfAlreadyhaveExternalStoragePermission(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Dialog with the permission Rationale if user denied access to Storage permission
    private void requestPermissionRationale (Context con, String title, String message, String centerBtn, final String permission, final int permissionRequestCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        final AppCompatDialog alert = builder.create();
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(centerBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert.dismiss();
                requestPermission(permission, permissionRequestCode, act);
            }
        });
        builder.show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode, Activity act) {
        ActivityCompat.requestPermissions(act,
                new String[]{permissionName}, permissionRequestCode);
    }

    public interface OnAsyncResult {
        void onInternetForPermissionSuccess(int resultCode, String message);
        void onInternetForPermissionFail(int resultCode, String errorMessage);
    }
}
